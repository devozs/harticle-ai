package com.devozs.components.harticle.training.controller;

import com.devozs.components.harticle.training.dto.AgentEnrollRequest;
import com.devozs.components.harticle.training.dto.AgentEnrollResponse;
import com.devozs.components.harticle.training.dto.AgentJobDto;
import com.devozs.components.harticle.training.dto.CheckpointReport;
import com.devozs.components.harticle.training.dto.CompleteRequest;
import com.devozs.components.harticle.training.dto.ErrorReport;
import com.devozs.components.harticle.training.dto.HeartbeatRequest;
import com.devozs.components.harticle.training.dto.HeartbeatResponse;
import com.devozs.components.harticle.training.dto.LogLine;
import com.devozs.components.harticle.training.dto.PreflightReport;
import com.devozs.components.harticle.training.dto.ProgressAck;
import com.devozs.components.harticle.training.dto.ProgressReport;
import com.devozs.components.harticle.training.entity.ComputeResource;
import com.devozs.components.harticle.training.entity.TrainingSession;
import com.devozs.components.harticle.training.service.ComputeResourceService;
import com.devozs.components.harticle.training.service.DatasetExportService;
import com.devozs.components.harticle.training.service.TrainingAgentService;
import com.devozs.components.harticle.training.service.TrainingSessionService;
import com.devozs.components.harticle.training.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Agent-facing polling protocol. Every call (except {@link #enroll}) authenticates
 * via the {@code Authorization: Bearer <token>} header, resolved to a
 * {@link ComputeResource}. All connections are OUTBOUND from the agent, so this
 * works behind NAT / a corporate proxy without management ever dialing the box.
 *
 * <p>These endpoints are {@code permitAll} at the Spring Security level (like the
 * scraper); the bearer token is enforced here in {@link #resolve}.
 */
@RestController
@RequestMapping(TrainingAgentURLS.URL)
@Slf4j
public class TrainingAgentController {

    private final ComputeResourceService resourceService;
    private final TrainingAgentService agentService;
    private final TrainingSessionService sessionService;
    private final StorageService storageService;

    public TrainingAgentController(ComputeResourceService resourceService,
                                   TrainingAgentService agentService,
                                   TrainingSessionService sessionService,
                                   StorageService storageService) {
        this.resourceService = resourceService;
        this.agentService = agentService;
        this.sessionService = sessionService;
        this.storageService = storageService;
    }

    // --- enrollment (code-authenticated, not token) --------------------------

    @PostMapping(TrainingAgentURLS.ENROLL)
    @ResponseBody
    public AgentEnrollResponse enroll(@RequestBody AgentEnrollRequest request) {
        return resourceService.enroll(request);
    }

    // --- liveness + claim ----------------------------------------------------

    @PostMapping(TrainingAgentURLS.HEARTBEAT)
    @ResponseBody
    public HeartbeatResponse heartbeat(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                       @RequestBody HeartbeatRequest request) {
        ComputeResource resource = resolve(auth);
        resourceService.heartbeat(resource, request.getStatus());
        return HeartbeatResponse.builder()
                .ack(true)
                .assignedSessionId(resource.getCurrentSessionId())
                .reverifyRequested(resource.isReverifyRequested())
                .build();
    }

    /**
     * Record the agent's readiness preflight verdict (accelerator identification +
     * a tiny real LLM workload). A passing check makes the box claimable.
     */
    @PostMapping(TrainingAgentURLS.PREFLIGHT)
    public ResponseEntity<Void> preflight(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                          @RequestBody PreflightReport report) {
        ComputeResource resource = resolve(auth);
        resourceService.recordPreflight(resource, report.isOk(), report.getDetail(), report.getCapabilities());
        return ResponseEntity.noContent().build();
    }

    /** Claim the next matching job; 204 when nothing is available. */
    @PostMapping(TrainingAgentURLS.CLAIM)
    public ResponseEntity<AgentJobDto> claim(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        ComputeResource resource = resolve(auth);
        return agentService.claim(resource)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // --- session reports -----------------------------------------------------

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.PROGRESS)
    @ResponseBody
    public ProgressAck progress(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                @PathVariable UUID id,
                                @RequestBody ProgressReport report) {
        ComputeResource resource = resolve(auth);
        TrainingSession session = requireOwned(resource, id);
        return agentService.recordProgress(session, report);
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.LOG)
    public ResponseEntity<Void> log(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                    @PathVariable UUID id,
                                    @RequestBody LogLine line) {
        ComputeResource resource = resolve(auth);
        requireOwned(resource, id);
        sessionService.appendLog(id, line.getLevel(), line.getMessage());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.CHECKPOINT)
    public ResponseEntity<Void> checkpoint(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                           @PathVariable UUID id,
                                           @RequestBody CheckpointReport report) {
        ComputeResource resource = resolve(auth);
        TrainingSession session = requireOwned(resource, id);
        agentService.recordCheckpoint(session, report.getCheckpointUri());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.COMPLETE)
    public ResponseEntity<Void> complete(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                         @PathVariable UUID id,
                                         @RequestBody CompleteRequest request) {
        ComputeResource resource = resolve(auth);
        TrainingSession session = requireOwned(resource, id);
        agentService.complete(session, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.STOPPED)
    public ResponseEntity<Void> stopped(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                        @PathVariable UUID id) {
        ComputeResource resource = resolve(auth);
        TrainingSession session = requireOwned(resource, id);
        agentService.stopped(session);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.ERROR)
    public ResponseEntity<Void> error(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                      @PathVariable UUID id,
                                      @RequestBody ErrorReport report) {
        ComputeResource resource = resolve(auth);
        TrainingSession session = requireOwned(resource, id);
        agentService.error(session, report);
        return ResponseEntity.noContent().build();
    }

    /**
     * Stream the exported JSONL dataset. Fallback for when the agent can't reach
     * object storage directly (e.g. local-fs with no shared mount): authenticated
     * by bearer token instead of a presigned URL.
     */
    @GetMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.DATASET)
    public ResponseEntity<InputStreamResource> dataset(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
                                                       @PathVariable UUID id) {
        ComputeResource resource = resolve(auth);
        requireOwned(resource, id);
        String key = DatasetExportService.datasetKey(id);
        if (!storageService.exists(key)) {
            return ResponseEntity.notFound().build();
        }
        InputStreamResource body = new InputStreamResource(storageService.read(key));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(body);
    }

    // --- auth helpers --------------------------------------------------------

    private ComputeResource resolve(String authorizationHeader) {
        String token = stripBearer(authorizationHeader);
        try {
            return resourceService.requireByToken(token);
        } catch (SecurityException e) {
            throw new ResponseStatusBadCredentials(e.getMessage());
        }
    }

    /** Ensure the resolved resource actually owns the session it's reporting on. */
    private TrainingSession requireOwned(ComputeResource resource, UUID sessionId) {
        TrainingSession session = sessionService.get(sessionId);
        if (!resource.getId().equals(session.getAssignedResourceId())) {
            throw new ResponseStatusBadCredentials("session " + sessionId + " is not assigned to this agent");
        }
        return session;
    }

    private static String stripBearer(String header) {
        if (header == null) {
            return null;
        }
        String h = header.trim();
        return h.regionMatches(true, 0, "Bearer ", 0, 7) ? h.substring(7).trim() : h;
    }

    /** 401 for bad/missing agent credentials. */
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.UNAUTHORIZED)
    static class ResponseStatusBadCredentials extends RuntimeException {
        ResponseStatusBadCredentials(String message) {
            super(message);
        }
    }
}
