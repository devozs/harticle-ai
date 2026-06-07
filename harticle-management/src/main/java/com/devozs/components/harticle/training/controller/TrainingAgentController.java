package com.devozs.components.harticle.training.controller;

import com.devozs.components.harticle.inference.dto.InferenceResultReport;
import com.devozs.components.harticle.inference.entity.InferenceRun;
import com.devozs.components.harticle.inference.repository.InferenceRunRepository;
import com.devozs.components.harticle.inference.service.InferenceService;
import com.devozs.components.harticle.training.dto.AgentEnrollRequest;
import com.devozs.components.harticle.training.dto.AgentEnrollResponse;
import com.devozs.components.harticle.training.dto.AgentJobDto;
import com.devozs.components.harticle.training.dto.CheckpointReport;
import com.devozs.components.harticle.training.dto.CompleteRequest;
import com.devozs.components.harticle.training.dto.ErrorReport;
import com.devozs.components.harticle.training.dto.HeartbeatRequest;
import com.devozs.components.harticle.training.dto.HeartbeatResponse;
import com.devozs.components.harticle.training.dto.LogLine;
import com.devozs.components.harticle.training.dto.ModelUploadResult;
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
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
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

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Agent-facing polling protocol. Every call (except {@link #enroll}) authenticates
 * via the {@code X-Agent-Token} header, resolved to a {@link ComputeResource}. A
 * custom header (not {@code Authorization}) is used on purpose: the OAuth2
 * resource-server filter would otherwise intercept {@code Authorization: Bearer}
 * and reject our opaque (non-JWT) token before the controller runs. All
 * connections are OUTBOUND from the agent, so this works behind NAT / a corporate
 * proxy without management ever dialing the box.
 *
 * <p>These endpoints are {@code permitAll} at the Spring Security level (like the
 * scraper); the agent token is enforced here in {@link #resolve}.
 */
@RestController
@RequestMapping(TrainingAgentURLS.URL)
@Slf4j
public class TrainingAgentController {

    private final ComputeResourceService resourceService;
    private final TrainingAgentService agentService;
    private final TrainingSessionService sessionService;
    private final StorageService storageService;
    private final InferenceService inferenceService;
    private final InferenceRunRepository inferenceRunRepository;

    public TrainingAgentController(ComputeResourceService resourceService,
                                   TrainingAgentService agentService,
                                   TrainingSessionService sessionService,
                                   StorageService storageService,
                                   InferenceService inferenceService,
                                   InferenceRunRepository inferenceRunRepository) {
        this.resourceService = resourceService;
        this.agentService = agentService;
        this.sessionService = sessionService;
        this.storageService = storageService;
        this.inferenceService = inferenceService;
        this.inferenceRunRepository = inferenceRunRepository;
    }

    // --- enrollment (code-authenticated, not token) --------------------------

    @PostMapping(TrainingAgentURLS.ENROLL)
    @ResponseBody
    public AgentEnrollResponse enroll(@RequestBody AgentEnrollRequest request) {
        // An invalid / already-used / revoked code surfaces as NoSuchElementException
        // (and a blank code as IllegalArgumentException). Map both to 401 so the agent
        // sees a clear "bad enrollment code" instead of an opaque 500 — the usual cause
        // is a code that was rotated (re-issuing in the UI revokes the previous one).
        try {
            return resourceService.enroll(request);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            throw new ResponseStatusBadCredentials(e.getMessage());
        }
    }

    // --- liveness + claim ----------------------------------------------------

    @PostMapping(TrainingAgentURLS.HEARTBEAT)
    @ResponseBody
    public HeartbeatResponse heartbeat(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                       @RequestBody HeartbeatRequest request) {
        ComputeResource resource = resolve(agentToken);
        resourceService.heartbeat(resource, request.getStatus());
        HeartbeatResponse.HeartbeatResponseBuilder ack = HeartbeatResponse.builder()
                .ack(true)
                .assignedSessionId(resource.getCurrentSessionId())
                .reverifyRequested(resource.isReverifyRequested());
        // Pending model push (fetch-to-local): tell the agent which session's model
        // to upload and where it lives on its box (the session's file:// output ref).
        UUID uploadId = resource.getModelUploadSessionId();
        if (uploadId != null && sessionService.exists(uploadId)) {
            ack.modelUploadSessionId(uploadId)
               .modelUploadSourceUri(sessionService.get(uploadId).getOutputModelRef());
        }
        return ack.build();
    }

    /**
     * Record the agent's readiness preflight verdict (accelerator identification +
     * a tiny real LLM workload). A passing check makes the box claimable.
     */
    @PostMapping(TrainingAgentURLS.PREFLIGHT)
    public ResponseEntity<Void> preflight(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                          @RequestBody PreflightReport report) {
        ComputeResource resource = resolve(agentToken);
        resourceService.recordPreflight(resource, report.isOk(), report.getDetail(), report.getCapabilities());
        return ResponseEntity.noContent().build();
    }

    /** Claim the next matching job; 204 when nothing is available. */
    @PostMapping(TrainingAgentURLS.CLAIM)
    public ResponseEntity<AgentJobDto> claim(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken) {
        ComputeResource resource = resolve(agentToken);
        return agentService.claim(resource)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // --- session reports -----------------------------------------------------

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.PROGRESS)
    @ResponseBody
    public ProgressAck progress(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                @PathVariable UUID id,
                                @RequestBody ProgressReport report) {
        ComputeResource resource = resolve(agentToken);
        // A deleted session is the cooperative kill signal: the row is gone, so we
        // tell the agent to stop (instead of 500ing on the missing session, which it
        // would swallow as non-fatal and keep training). The resource was already
        // freed at delete time; nothing more to clean up here.
        if (!sessionService.exists(id)) {
            return ProgressAck.builder().stopRequested(true).build();
        }
        TrainingSession session = requireOwned(resource, id);
        return agentService.recordProgress(session, report);
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.LOG)
    public ResponseEntity<Void> log(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                    @PathVariable UUID id,
                                    @RequestBody LogLine line) {
        ComputeResource resource = resolve(agentToken);
        requireOwned(resource, id);
        sessionService.appendLog(id, line.getLevel(), line.getMessage());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.CHECKPOINT)
    public ResponseEntity<Void> checkpoint(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                           @PathVariable UUID id,
                                           @RequestBody CheckpointReport report) {
        ComputeResource resource = resolve(agentToken);
        TrainingSession session = requireOwned(resource, id);
        agentService.recordCheckpoint(session, report.getCheckpointUri());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.COMPLETE)
    public ResponseEntity<Void> complete(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                         @PathVariable UUID id,
                                         @RequestBody CompleteRequest request) {
        ComputeResource resource = resolve(agentToken);
        TrainingSession session = requireOwned(resource, id);
        agentService.complete(session, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.STOPPED)
    public ResponseEntity<Void> stopped(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                        @PathVariable UUID id) {
        ComputeResource resource = resolve(agentToken);
        // The agent may report stopped right after the session was deleted (the
        // delete-as-kill path). Nothing to record — the row and its resource are
        // already gone; ack cleanly instead of 500ing on a missing session.
        if (!sessionService.exists(id)) {
            return ResponseEntity.noContent().build();
        }
        TrainingSession session = requireOwned(resource, id);
        agentService.stopped(session);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.ERROR)
    public ResponseEntity<Void> error(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                      @PathVariable UUID id,
                                      @RequestBody ErrorReport report) {
        ComputeResource resource = resolve(agentToken);
        // Likewise tolerate a deleted session (the agent's error report races the
        // delete): the row is gone, so there's nothing to mark failed.
        if (!sessionService.exists(id)) {
            return ResponseEntity.noContent().build();
        }
        TrainingSession session = requireOwned(resource, id);
        agentService.error(session, report);
        return ResponseEntity.noContent().build();
    }

    /**
     * Result of an inference job (GPU/HPU path). The agent loaded the trained model,
     * generated against the prompt, and POSTs the samples here (or an error). Owned
     * by the resource the run was assigned to.
     */
    @PostMapping(TrainingAgentURLS.INFERENCE + TrainingAgentURLS.ID + TrainingAgentURLS.RESULT)
    public ResponseEntity<Void> inferenceResult(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                                @PathVariable UUID id,
                                                @RequestBody InferenceResultReport report) {
        ComputeResource resource = resolve(agentToken);
        // The run may have been deleted while generation was in flight (delete-as-
        // kill). Drop the late result instead of 401ing on the missing run.
        if (!inferenceRunRepository.existsById(id)) {
            return ResponseEntity.noContent().build();
        }
        InferenceRun run = requireOwnedInference(resource, id);
        inferenceService.recordResult(run, report);
        return ResponseEntity.noContent().build();
    }

    /**
     * Stream the exported JSONL dataset. Fallback for when the agent can't reach
     * object storage directly (e.g. local-fs with no shared mount): authenticated
     * by the agent token header instead of a presigned URL.
     */
    @GetMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.DATASET)
    public ResponseEntity<InputStreamResource> dataset(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                                       @PathVariable UUID id) {
        ComputeResource resource = resolve(agentToken);
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

    /**
     * Manifest of model files management already holds for this session ({@code
     * relPath -> byteSize}). The agent reads this before a (re)fetch and re-sends only
     * the files that are missing or a different size, so an interrupted push resumes.
     */
    @GetMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.MODEL_MANIFEST)
    @ResponseBody
    public java.util.Map<String, Long> modelManifest(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                                      @PathVariable UUID id) {
        ComputeResource resource = resolve(agentToken);
        requireOwned(resource, id);
        return sessionService.modelManifest(id);
    }

    /**
     * Receive one file of a model the agent is pushing up (fetch-to-local). The
     * agent streams each file with its path relative to the model dir in the
     * {@code X-Rel-Path} header; we write it under {@code models/{sessionId}/{rel}}.
     * Outbound-only safe: the agent initiates, management never dials the box.
     */
    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.MODEL_FILE)
    public ResponseEntity<Void> modelFile(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                          @RequestHeader(TrainingAgentURLS.REL_PATH_HEADER) String relPath,
                                          @RequestHeader(value = TrainingAgentURLS.FILES_TOTAL_HEADER, required = false) Integer filesTotal,
                                          @RequestHeader(value = TrainingAgentURLS.BYTES_TOTAL_HEADER, required = false) Long bytesTotal,
                                          @PathVariable UUID id,
                                          HttpServletRequest request) throws java.io.IOException {
        ComputeResource resource = resolve(agentToken);
        requireOwned(resource, id);
        sessionService.receiveModelFile(id, relPath, request.getInputStream(), request.getContentLengthLong(),
                filesTotal, bytesTotal);
        return ResponseEntity.noContent().build();
    }

    /**
     * The agent has finished pushing all model files (or hit an error). Flips the
     * session's fetch status and clears the upload request on the resource.
     */
    @PostMapping(TrainingAgentURLS.SESSIONS + TrainingAgentURLS.ID + TrainingAgentURLS.MODEL_UPLOAD_COMPLETE)
    public ResponseEntity<Void> modelUploadComplete(@RequestHeader(TrainingAgentURLS.TOKEN_HEADER) String agentToken,
                                                    @PathVariable UUID id,
                                                    @RequestBody ModelUploadResult result) {
        ComputeResource resource = resolve(agentToken);
        requireOwned(resource, id);
        sessionService.completeModelUpload(id, resource, result.isOk(), result.getMessage());
        return ResponseEntity.noContent().build();
    }

    // --- auth helpers --------------------------------------------------------

    private ComputeResource resolve(String agentToken) {
        // Tolerate an optional "Bearer " prefix in case a client still sends one.
        String token = stripBearer(agentToken);
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

    /** Ensure the resolved resource owns the inference run it's reporting on. */
    private InferenceRun requireOwnedInference(ComputeResource resource, UUID runId) {
        InferenceRun run = inferenceRunRepository.findById(runId)
                .orElseThrow(() -> new ResponseStatusBadCredentials("inference run not found: " + runId));
        if (!resource.getId().equals(run.getAssignedResourceId())) {
            throw new ResponseStatusBadCredentials("inference run " + runId + " is not assigned to this agent");
        }
        return run;
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
