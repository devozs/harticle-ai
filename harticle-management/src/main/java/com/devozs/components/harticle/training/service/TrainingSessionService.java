package com.devozs.components.harticle.training.service;

import com.devozs.components.harticle.training.domain.ModelFetchStatus;
import com.devozs.components.harticle.training.domain.ModelReachability;
import com.devozs.components.harticle.training.domain.TrainingStatus;
import com.devozs.components.harticle.training.dto.TrainingLogDto;
import com.devozs.components.harticle.training.dto.TrainingSessionDto;
import com.devozs.components.harticle.training.dto.TrainingSessionSummary;
import com.devozs.components.harticle.training.domain.ComputeResourceStatus;
import com.devozs.components.harticle.training.entity.ComputeResource;
import com.devozs.components.harticle.training.entity.TrainingLog;
import com.devozs.components.harticle.training.entity.TrainingSession;
import com.devozs.components.harticle.scraper.entity.ScrapeReporter;
import com.devozs.components.harticle.scraper.repository.ScrapeReporterRepository;
import com.devozs.components.harticle.training.repository.ComputeResourceRepository;
import com.devozs.components.harticle.training.repository.TrainingLogRepository;
import com.devozs.components.harticle.training.repository.TrainingSessionRepository;
import com.devozs.components.harticle.training.storage.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Admin-side lifecycle for training sessions: create (→ PENDING, claimable),
 * stop (→ STOP_REQUESTED, cooperative), resume (→ RESUMING), and read-back
 * (list / snapshot / logs). The persisted snapshot is the DB-backed analogue of
 * the scraper's in-memory {@code ScrapeProgressTracker}, so progress survives the
 * agent boundary and is visible across boxes.
 */
@Service
@Slf4j
public class TrainingSessionService {

    private final TrainingSessionRepository sessionRepository;
    private final TrainingLogRepository logRepository;
    private final ComputeResourceRepository resourceRepository;
    private final ComputeResourceService resourceService;
    private final DatasetExportService datasetExportService;
    private final StorageService storageService;
    private final ScrapeReporterRepository reporterRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TrainingSessionService(TrainingSessionRepository sessionRepository,
                                  TrainingLogRepository logRepository,
                                  ComputeResourceRepository resourceRepository,
                                  ComputeResourceService resourceService,
                                  DatasetExportService datasetExportService,
                                  StorageService storageService,
                                  ScrapeReporterRepository reporterRepository) {
        this.sessionRepository = sessionRepository;
        this.logRepository = logRepository;
        this.resourceRepository = resourceRepository;
        this.resourceService = resourceService;
        this.datasetExportService = datasetExportService;
        this.storageService = storageService;
        this.reporterRepository = reporterRepository;
    }

    public List<TrainingSession> getAll() {
        return sessionRepository.findAllByOrderByCreatedAtDesc();
    }

    public TrainingSession get(UUID id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("training session not found: " + id));
    }

    public boolean exists(UUID id) {
        return sessionRepository.existsById(id);
    }

    /**
     * Create a session in PENDING (immediately claimable) and kick off dataset
     * export asynchronously. The agent won't get a usable dataset URI until export
     * finishes, but claim+setup overlaps the export so this is rarely a wait.
     */
    public TrainingSession create(TrainingSessionDto dto) {
        // A model scoped to exactly one reporter is a per-reporter ("dedicated") model;
        // promote that reporter to first-class columns (id + name snapshot) so history,
        // re-train, and the inference picker can rely on it without re-parsing JSON.
        // Empty/multi selection = a general model (both columns null).
        UUID reporterId = singleReporterId(dto.getReporterIds());
        String reporterName = reporterId == null ? null
                : reporterRepository.findById(reporterId).map(ScrapeReporter::getDisplayName).orElse(null);

        TrainingSession session = TrainingSession.builder()
                .name(dto.getName())
                .baseModel(dto.getBaseModel())
                .requiredType(dto.getRequiredType())
                .stubMode(Boolean.TRUE.equals(dto.getStubMode()))
                .pushToHub(Boolean.TRUE.equals(dto.getPushToHub()))
                .status(TrainingStatus.PENDING)
                .progressPercent(0)
                .datasetSpec(buildDatasetSpec(dto))
                .hyperparams(buildHyperparams(dto))
                .reporterId(reporterId)
                .reporterName(reporterName)
                .build();
        session = sessionRepository.save(session);
        exportDatasetAsync(session.getId());
        return session;
    }

    /** The reporter id when a dataset scope names exactly one reporter, else null. */
    private static UUID singleReporterId(List<String> reporterIds) {
        if (reporterIds == null || reporterIds.size() != 1) {
            return null;
        }
        try {
            return UUID.fromString(reporterIds.get(0));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Async
    public void exportDatasetAsync(UUID sessionId) {
        try {
            TrainingSession session = get(sessionId);
            List<UUID> reporterIds = parseReporterIds(session.getDatasetSpec());
            String uri = datasetExportService.export(session, reporterIds);
            session.setDatasetUri(uri);
            sessionRepository.save(session);
        } catch (Exception e) {
            log.error("dataset export failed for session {}", sessionId, e);
            appendLog(sessionId, "ERROR", "dataset export failed: " + e.getMessage());
        }
    }

    /** Cooperative stop: flip to STOP_REQUESTED; the agent acts at the next step boundary. */
    public TrainingSession stop(UUID id) {
        TrainingSession session = get(id);
        if (session.getStatus() == TrainingStatus.RUNNING
                || session.getStatus() == TrainingStatus.ASSIGNED
                || session.getStatus() == TrainingStatus.RESUMING) {
            session.setStatus(TrainingStatus.STOP_REQUESTED);
            sessionRepository.save(session);
            appendLog(id, "INFO", "stop requested by admin");
        }
        return session;
    }

    /** Resume a STOPPED/FAILED session that has a checkpoint to continue from. */
    public TrainingSession resume(UUID id) {
        TrainingSession session = get(id);
        if (session.getStatus() != TrainingStatus.STOPPED && session.getStatus() != TrainingStatus.FAILED) {
            throw new IllegalStateException("only a STOPPED or FAILED session can resume (was " + session.getStatus() + ")");
        }
        if (session.getCheckpointUri() == null || session.getCheckpointUri().isBlank()) {
            throw new IllegalStateException("no checkpoint to resume from");
        }
        session.setStatus(TrainingStatus.RESUMING);
        session.setErrorMessage(null);
        session.setErrorType(null);
        session.setAssignedResourceId(null);
        sessionRepository.save(session);
        appendLog(id, "INFO", "resume requested by admin (from " + session.getCheckpointUri() + ")");
        return session;
    }

    /**
     * Re-run a FAILED or STOPPED session as a NEW PENDING session with the
     * parent's EXACT config — same base model, hyperparams, dataset spec, type,
     * stub/hub flags — and the SAME dataset (reuses {@code datasetUri}, no
     * re-export) so it's a faithful re-execution. The parent row is left
     * untouched, preserving the attempt history; the new session points back at
     * its parent and carries an incremented {@code attemptNumber}.
     *
     * <p>COMPLETED runs are intentionally not re-runnable (re-run is for
     * recovering a failed/stopped attempt, not duplicating a good one).
     */
    public TrainingSession rerun(UUID id) {
        TrainingSession parent = get(id);
        if (parent.getStatus() != TrainingStatus.FAILED && parent.getStatus() != TrainingStatus.STOPPED) {
            throw new IllegalStateException(
                    "only a FAILED or STOPPED session can be re-run (was " + parent.getStatus() + ")");
        }
        // Chain to the ORIGINAL run so all attempts share one parent and the
        // attempt counter reflects total tries, even when re-running a re-run.
        UUID rootId = parent.getParentSessionId() != null ? parent.getParentSessionId() : parent.getId();
        int nextAttempt = sessionRepository.maxAttemptInChain(rootId) + 1;

        TrainingSession copy = TrainingSession.builder()
                .name(parent.getName())
                .baseModel(parent.getBaseModel())
                .requiredType(parent.getRequiredType())
                .stubMode(parent.isStubMode())
                .pushToHub(parent.isPushToHub())
                .status(TrainingStatus.PENDING)
                .progressPercent(0)
                .datasetSpec(parent.getDatasetSpec())
                .hyperparams(parent.getHyperparams())
                .reporterId(parent.getReporterId())
                .reporterName(parent.getReporterName())
                .parentSessionId(rootId)
                .attemptNumber(nextAttempt)
                .build();
        copy = sessionRepository.save(copy);

        // The dataset endpoint serves per-session keys, so we can't just reuse the
        // parent's URI string — the new session needs its OWN key populated. Copy
        // the parent's bytes verbatim (same data, no re-query). If the parent never
        // produced a dataset, export fresh so the re-run isn't stuck on a 404.
        String copiedUri = datasetExportService.copyDataset(parent.getId(), copy.getId());
        if (copiedUri != null) {
            copy.setDatasetUri(copiedUri);
            copy = sessionRepository.save(copy);
            appendLog(copy.getId(), "INFO",
                    "re-run of session " + id + " (attempt #" + nextAttempt + "); reused dataset from parent");
        } else {
            appendLog(copy.getId(), "INFO",
                    "re-run of session " + id + " (attempt #" + nextAttempt + "); parent dataset missing, re-exporting");
            exportDatasetAsync(copy.getId());
        }
        return copy;
    }

    /**
     * Delete a session, freeing any compute it holds and removing all its
     * artifacts (dataset, checkpoints, output model) along with its logs and row.
     *
     * <p>If the session is in-flight, deletion is also the kill switch. We can't
     * reach into a remote, outbound-only agent to kill its process, so the abort is
     * cooperative: the assigned resource is freed immediately (so it isn't left
     * stuck BUSY), and the row is removed. At the agent's next progress tick the
     * report comes back {@code stopRequested} (its session has vanished), so it
     * halts the training loop instead of running to completion — see the GONE
     * handling in {@code TrainingAgentController.progress}.
     *
     * <p>{@code @Transactional} is required: the derived {@code deleteBySessionId}
     * bulk delete needs an active transaction (without one JPA throws
     * TransactionRequiredException on the {@code remove}), and it keeps the
     * log + session deletes atomic.
     */
    @org.springframework.transaction.annotation.Transactional
    public void delete(UUID id) {
        TrainingSession session = sessionRepository.findById(id).orElse(null);
        if (session != null) {
            freeAssignedResource(session);
            deleteArtifacts(session);
        }
        logRepository.deleteBySessionId(id);
        sessionRepository.deleteById(id);
    }

    /** Free the compute resource this session is running on, if it still holds it. */
    private void freeAssignedResource(TrainingSession session) {
        if (session.getAssignedResourceId() == null) {
            return;
        }
        resourceRepository.findById(session.getAssignedResourceId()).ifPresent(resource -> {
            // Only reclaim the box if it's actually pinned to THIS session.
            if (id(session).equals(resource.getCurrentSessionId())) {
                resourceService.markIdle(resource);
            }
        });
    }

    /** Remove the session's dataset, checkpoints, and output model from storage. */
    private void deleteArtifacts(TrainingSession session) {
        UUID id = session.getId();
        try {
            storageService.delete(DatasetExportService.datasetKey(id));
            storageService.deletePrefix("checkpoints/" + id + "/");
            storageService.deletePrefix("models/" + id + "/");
        } catch (Exception e) {
            // Best-effort: a storage hiccup shouldn't block removing the DB row.
            log.warn("could not fully delete artifacts for session {}", id, e);
        }
    }

    private static UUID id(TrainingSession s) {
        return s.getId();
    }

    // --- read-back -----------------------------------------------------------

    public TrainingSessionSummary snapshot(UUID id) {
        return toSummary(get(id));
    }

    public List<TrainingSessionSummary> summaries() {
        return getAll().stream().map(this::toSummary).toList();
    }

    /**
     * Sessions that produced a usable model — COMPLETED with a non-null
     * {@code outputModelRef}. These are the only models the admin may test in the
     * inference screen (a failed/running run has nothing to load).
     */
    public List<TrainingSession> completedModels() {
        return getAll().stream()
                .filter(s -> s.getStatus() == TrainingStatus.COMPLETED)
                .filter(s -> s.getOutputModelRef() != null && !s.getOutputModelRef().isBlank())
                .toList();
    }

    // --- fetch-to-local (bring a remote model's files onto the management host) ---

    /** Storage key prefix the model files live under for a given session. */
    public static String modelKeyPrefix(UUID sessionId) {
        return "models/" + sessionId;
    }

    /**
     * Is this session's model present in management's own storage (so LOCAL CPU
     * inference can load it)? True for a model already written here (local-fs run,
     * or one fetched), or any S3/hub ref the engine can resolve on its own. The
     * presence probe ({@code config.json} under the model prefix) is authoritative
     * and flips to true automatically once an agent finishes pushing.
     */
    public boolean isModelAvailableLocally(TrainingSession s) {
        String ref = s.getOutputModelRef();
        if (ref == null || ref.isBlank()) {
            return false;
        }
        // s3:// and bare hub ids are resolvable from anywhere; only file:// is host-bound.
        if (!ref.startsWith("file://")) {
            return true;
        }
        return storageService.exists(modelKeyPrefix(s.getId()) + "/config.json");
    }

    /**
     * Where this completed session's model can be loaded from — see {@link ModelReachability}.
     * Derived live (no stored column) from the output ref, local presence, and the state
     * of the box that trained it, so it always reflects the current world: when a training
     * box is removed, its un-fetched {@code file://} models flip to ORPHANED automatically.
     */
    public ModelReachability modelReachability(TrainingSession s) {
        String ref = s.getOutputModelRef();
        if (s.getStatus() != TrainingStatus.COMPLETED || ref == null || ref.isBlank()) {
            return ModelReachability.ORPHANED;
        }
        if (!ref.startsWith("file://")) {
            return ModelReachability.PORTABLE;
        }
        if (isModelAvailableLocally(s)) {
            return ModelReachability.LOCAL_AVAILABLE;
        }
        // file:// not on this host: only its own training box still holds the files, and
        // only if that box is still registered and alive.
        UUID resourceId = s.getAssignedResourceId();
        if (resourceId != null) {
            ComputeResource box = resourceRepository.findById(resourceId).orElse(null);
            if (resourceService.isLive(box)) {
                return ModelReachability.REMOTE_ONLY;
            }
        }
        return ModelReachability.ORPHANED;
    }

    /**
     * Admin asks to bring a remotely-trained model's files down to this host. Since
     * the agent is outbound-only, we flag the box that trained it; the agent sees
     * the flag on its next heartbeat and pushes the files up. No-op if already local.
     */
    public TrainingSession requestModelFetch(UUID sessionId) {
        TrainingSession session = get(sessionId);
        if (session.getStatus() != TrainingStatus.COMPLETED
                || session.getOutputModelRef() == null || session.getOutputModelRef().isBlank()) {
            throw new IllegalStateException("only a COMPLETED session with a trained model can be fetched");
        }
        if (isModelAvailableLocally(session)) {
            session.setModelFetchStatus(ModelFetchStatus.AVAILABLE);
            return sessionRepository.save(session);
        }
        UUID resourceId = session.getAssignedResourceId();
        if (resourceId == null) {
            throw new IllegalStateException("session has no training box to fetch the model from");
        }
        ComputeResource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new IllegalStateException("the box that trained this model is no longer registered"));
        resource.setModelUploadSessionId(sessionId);
        resourceRepository.save(resource);

        session.setModelFetchStatus(ModelFetchStatus.REQUESTED);
        appendLog(sessionId, "INFO", "model fetch-to-local requested; awaiting agent push from " + resource.getName());
        return sessionRepository.save(session);
    }

    /** Write one pushed model file under {@code models/{sessionId}/{relPath}}. */
    public void receiveModelFile(UUID sessionId, String relPath, InputStream data, long contentLength) {
        String safeRel = relPath.replace("\\", "/").replaceAll("^/+", "");
        if (safeRel.contains("..")) {
            throw new IllegalArgumentException("illegal model file path: " + relPath);
        }
        TrainingSession session = get(sessionId);
        if (session.getModelFetchStatus() != ModelFetchStatus.UPLOADING) {
            session.setModelFetchStatus(ModelFetchStatus.UPLOADING);
            sessionRepository.save(session);
        }
        storageService.write(modelKeyPrefix(sessionId) + "/" + safeRel, data, contentLength);
    }

    /** The agent finished (or failed) pushing the model; finalize + clear the flag. */
    public void completeModelUpload(UUID sessionId, ComputeResource resource, boolean ok, String message) {
        TrainingSession session = get(sessionId);
        session.setModelFetchStatus(ok ? ModelFetchStatus.AVAILABLE : ModelFetchStatus.FAILED);
        sessionRepository.save(session);
        appendLog(sessionId, ok ? "INFO" : "WARN",
                ok ? "model fetched to local storage" : "model fetch failed: " + message);
        // Clear the pending request so the agent stops re-pushing on each heartbeat.
        if (sessionId.equals(resource.getModelUploadSessionId())) {
            resource.setModelUploadSessionId(null);
            resourceRepository.save(resource);
        }
    }

    public List<TrainingLogDto> logs(UUID sessionId, long afterSeq) {
        return logRepository.findBySessionIdAndSeqGreaterThanOrderBySeqAsc(sessionId, afterSeq).stream()
                .map(l -> TrainingLogDto.builder()
                        .seq(l.getSeq())
                        .level(l.getLevel())
                        .message(l.getMessage())
                        .loggedAtEpochMs(l.getLoggedAt() == null ? null : l.getLoggedAt().getTime())
                        .build())
                .toList();
    }

    public TrainingSessionSummary toSummary(TrainingSession s) {
        String resourceName = null;
        if (s.getAssignedResourceId() != null) {
            resourceName = resourceRepository.findById(s.getAssignedResourceId())
                    .map(ComputeResource::getName).orElse(null);
        }
        Long lastSeen = s.getLastAgentSeenAt() == null ? null : s.getLastAgentSeenAt().getTime();
        Long sinceSeconds = lastSeen == null ? null : (System.currentTimeMillis() - lastSeen) / 1000;
        boolean terminalRecoverable = s.getStatus() == TrainingStatus.STOPPED || s.getStatus() == TrainingStatus.FAILED;
        boolean resumable = terminalRecoverable
                && s.getCheckpointUri() != null && !s.getCheckpointUri().isBlank();
        // A failed/stopped run can always be re-run (fresh attempt); COMPLETED cannot.
        boolean rerunnable = terminalRecoverable;
        return TrainingSessionSummary.builder()
                .id(s.getId())
                .name(s.getName())
                .baseModel(s.getBaseModel())
                .status(s.getStatus())
                .requiredType(s.getRequiredType())
                .stubMode(s.isStubMode())
                .progressPercent(s.getProgressPercent())
                .currentEpoch(s.getCurrentEpoch())
                .totalEpochs(s.getTotalEpochs())
                .currentStep(s.getCurrentStep())
                .totalSteps(s.getTotalSteps())
                .lastLoss(s.getLastLoss())
                .assignedResourceId(s.getAssignedResourceId())
                .assignedResourceName(resourceName)
                .reporterId(s.getReporterId())
                .reporterName(s.getReporterName())
                .checkpointUri(s.getCheckpointUri())
                .resumable(resumable)
                .rerunnable(rerunnable)
                .parentSessionId(s.getParentSessionId())
                .attemptNumber(s.getAttemptNumber())
                .pushToHub(s.isPushToHub())
                .outputModelRef(s.getOutputModelRef())
                .modelAvailableLocal(s.getStatus() == TrainingStatus.COMPLETED && isModelAvailableLocally(s))
                .modelReachability(s.getStatus() == TrainingStatus.COMPLETED ? modelReachability(s) : null)
                .modelFetchStatus(s.getModelFetchStatus())
                .errorMessage(s.getErrorMessage())
                .errorType(s.getErrorType())
                .createdAtEpochMs(s.getCreatedAt() == null ? null : s.getCreatedAt().getTime())
                .lastAgentSeenAtEpochMs(lastSeen)
                .secondsSinceActivity(sinceSeconds)
                .build();
    }

    // --- logging (shared with the agent service) -----------------------------

    public TrainingLog appendLog(UUID sessionId, String level, String message) {
        long seq = logRepository.countBySessionId(sessionId);
        TrainingLog logLine = TrainingLog.builder()
                .sessionId(sessionId)
                .level(level)
                .message(message)
                .loggedAt(new java.util.Date())
                .seq(seq)
                .build();
        return logRepository.save(logLine);
    }

    // --- JSON helpers --------------------------------------------------------

    private String buildDatasetSpec(TrainingSessionDto dto) {
        ObjectNode node = objectMapper.createObjectNode();
        ArrayNode fields = node.putArray("fields");
        fields.add("title").add("subTitle").add("content");
        node.put("format", "jsonl");
        ArrayNode reporters = node.putArray("reporterIds");
        if (dto.getReporterIds() != null) {
            dto.getReporterIds().forEach(reporters::add);
        }
        return node.toString();
    }

    private String buildHyperparams(TrainingSessionDto dto) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("epochs", dto.getEpochs() == null ? 3 : dto.getEpochs());
        node.put("batchSize", dto.getBatchSize() == null ? 4 : dto.getBatchSize());
        node.put("learningRate", dto.getLearningRate() == null ? 5e-5 : dto.getLearningRate());
        node.put("warmupSteps", dto.getWarmupSteps() == null ? 10 : dto.getWarmupSteps());
        node.put("weightDecay", dto.getWeightDecay() == null ? 0.01 : dto.getWeightDecay());
        node.put("saveSteps", dto.getSaveSteps() == null ? 200 : dto.getSaveSteps());
        node.put("contextLength", dto.getContextLength() == null ? 128 : dto.getContextLength());
        return node.toString();
    }

    private List<UUID> parseReporterIds(String datasetSpec) {
        try {
            var node = objectMapper.readTree(datasetSpec);
            var arr = node.get("reporterIds");
            if (arr == null || !arr.isArray()) {
                return List.of();
            }
            return java.util.stream.StreamSupport.stream(arr.spliterator(), false)
                    .map(n -> UUID.fromString(n.asText()))
                    .toList();
        } catch (Exception e) {
            log.warn("could not parse reporterIds from datasetSpec: {}", datasetSpec, e);
            return List.of();
        }
    }
}
