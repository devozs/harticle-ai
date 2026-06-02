package com.devozs.components.harticle.training.service;

import com.devozs.components.harticle.training.config.TrainingProperties;
import com.devozs.components.harticle.training.domain.ComputeResourceReadiness;
import com.devozs.components.harticle.training.domain.ComputeResourceStatus;
import com.devozs.components.harticle.training.dto.AgentEnrollRequest;
import com.devozs.components.harticle.training.dto.AgentEnrollResponse;
import com.devozs.components.harticle.training.dto.ComputeResourceDto;
import com.devozs.components.harticle.training.dto.EnrollmentCodeResponse;
import com.devozs.components.harticle.training.entity.ComputeResource;
import com.devozs.components.harticle.training.repository.ComputeResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Manages compute-resource registration and the admin-gated enrollment handshake.
 *
 * <p>Flow: admin {@link #create}s a resource (OFFLINE, not enrolled) and gets a
 * one-time enrollment code via {@link #issueEnrollmentCode}; the agent redeems it
 * with {@link #enroll}, receiving its bearer token (returned once). Thereafter the
 * agent authenticates by token (resolved in {@link #requireByToken}) and pings
 * {@link #heartbeat}.
 */
@Service
@Slf4j
public class ComputeResourceService {

    private final ComputeResourceRepository repository;
    private final AgentTokenService tokenService;
    private final TrainingProperties properties;

    public ComputeResourceService(ComputeResourceRepository repository,
                                  AgentTokenService tokenService,
                                  TrainingProperties properties) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.properties = properties;
    }

    // --- admin CRUD ----------------------------------------------------------

    public List<ComputeResource> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }

    public ComputeResource get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("compute resource not found: " + id));
    }

    public ComputeResource create(ComputeResourceDto dto) {
        ComputeResource resource = ComputeResource.builder()
                .name(dto.getName())
                .type(dto.getType())
                .status(ComputeResourceStatus.OFFLINE)
                .enrolled(false)
                .enabled(dto.getEnabled() == null || dto.getEnabled())
                .build();
        return repository.save(resource);
    }

    public ComputeResource update(UUID id, ComputeResourceDto dto) {
        ComputeResource resource = get(id);
        if (dto.getName() != null) resource.setName(dto.getName());
        if (dto.getType() != null) resource.setType(dto.getType());
        if (dto.getEnabled() != null) resource.setEnabled(dto.getEnabled());
        return repository.save(resource);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    /**
     * (Re)issue a one-time enrollment code for a resource, resetting enrollment.
     * The plaintext code is returned once; only its hash is stored.
     */
    public EnrollmentCodeResponse issueEnrollmentCode(UUID id) {
        ComputeResource resource = get(id);
        String code = tokenService.generateEnrollmentCode();
        resource.setEnrollmentCodeHash(tokenService.hash(code));
        resource.setEnrolled(false);
        resource.setAgentTokenHash(null);
        resource.setStatus(ComputeResourceStatus.OFFLINE);
        repository.save(resource);
        return EnrollmentCodeResponse.builder().resourceId(id).enrollmentCode(code).build();
    }

    // --- agent handshake -----------------------------------------------------

    /** Redeem an enrollment code for a per-agent bearer token (returned once). */
    @Transactional
    public AgentEnrollResponse enroll(AgentEnrollRequest request) {
        if (request.getCode() == null || request.getCode().isBlank()) {
            throw new IllegalArgumentException("enrollment code required");
        }
        String codeHash = tokenService.hash(request.getCode());
        // The code hash is cleared on redemption, so a match implies a live, unused code.
        ComputeResource resource = getAll().stream()
                .filter(r -> codeHash.equals(r.getEnrollmentCodeHash()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("invalid or already-used enrollment code"));

        String token = tokenService.generateAgentToken();
        resource.setAgentTokenHash(tokenService.hash(token));
        resource.setEnrollmentCodeHash(null); // single use
        resource.setEnrolled(true);
        resource.setCapabilities(request.getCapabilities());
        resource.setStatus(ComputeResourceStatus.IDLE);
        // A freshly (re-)enrolled box must pass preflight again before claiming.
        resource.setReadiness(ComputeResourceReadiness.UNVERIFIED);
        resource.setReadinessDetail(null);
        resource.setReverifyRequested(false);
        resource.setLastHeartbeat(new Date());
        repository.save(resource);

        return AgentEnrollResponse.builder()
                .resourceId(resource.getId())
                .name(resource.getName())
                .type(resource.getType())
                .token(token)
                .build();
    }

    /** Resolve the authenticated agent from its bearer token, or throw 401-worthy. */
    public ComputeResource requireByToken(String token) {
        if (token == null || token.isBlank()) {
            throw new SecurityException("missing agent token");
        }
        return repository.findByAgentTokenHash(tokenService.hash(token))
                .orElseThrow(() -> new SecurityException("invalid agent token"));
    }

    /** Update liveness + status from a heartbeat. Won't overwrite BUSY bookkeeping. */
    public ComputeResource heartbeat(ComputeResource resource, ComputeResourceStatus reported) {
        resource.setLastHeartbeat(new Date());
        if (reported != null) {
            resource.setStatus(reported);
        } else if (resource.getStatus() == ComputeResourceStatus.OFFLINE) {
            resource.setStatus(ComputeResourceStatus.IDLE);
        }
        return repository.save(resource);
    }

    /**
     * Record a readiness preflight verdict from the agent. A passing check makes
     * the box claimable (READY); a failing one blocks it (FAILED) with the reason.
     * Either way the re-verify request is cleared.
     */
    public ComputeResource recordPreflight(ComputeResource resource, boolean ok, String detail, String capabilities) {
        resource.setReadiness(ok ? ComputeResourceReadiness.READY : ComputeResourceReadiness.FAILED);
        resource.setReadinessDetail(detail);
        resource.setReadinessCheckedAt(new Date());
        resource.setReverifyRequested(false);
        if (capabilities != null && !capabilities.isBlank()) {
            resource.setCapabilities(capabilities);
        }
        resource.setLastHeartbeat(new Date());
        return repository.save(resource);
    }

    /**
     * Admin asks a box to re-run its preflight. Rejected while BUSY (a running job
     * must not be disturbed). The agent picks this up via its heartbeat ack.
     */
    public ComputeResource requestReverify(UUID id) {
        ComputeResource resource = get(id);
        if (resource.getStatus() == ComputeResourceStatus.BUSY) {
            throw new IllegalStateException("cannot re-verify a BUSY resource");
        }
        resource.setReverifyRequested(true);
        resource.setReadiness(ComputeResourceReadiness.VERIFYING);
        resource.setReadinessDetail("re-verify requested by admin");
        return repository.save(resource);
    }

    /** Agent signals it has started the preflight; clears the pending request flag. */
    public ComputeResource markVerifying(ComputeResource resource) {
        resource.setReadiness(ComputeResourceReadiness.VERIFYING);
        resource.setReverifyRequested(false);
        resource.setLastHeartbeat(new Date());
        return repository.save(resource);
    }

    /** Mark a resource free again (its session ended). */
    public void markIdle(ComputeResource resource) {
        resource.setCurrentSessionId(null);
        resource.setStatus(ComputeResourceStatus.IDLE);
        resource.setLastHeartbeat(new Date());
        repository.save(resource);
    }

    /** Sweep resources whose heartbeat has gone stale to OFFLINE. */
    public void markStaleOffline() {
        long cutoffMs = System.currentTimeMillis() - properties.getHeartbeatTimeoutSeconds() * 1000;
        for (ComputeResource r : getAll()) {
            if (r.getStatus() != ComputeResourceStatus.OFFLINE
                    && (r.getLastHeartbeat() == null || r.getLastHeartbeat().getTime() < cutoffMs)) {
                r.setStatus(ComputeResourceStatus.OFFLINE);
                repository.save(r);
            }
        }
    }

    public ComputeResource save(ComputeResource resource) {
        return repository.save(resource);
    }
}
