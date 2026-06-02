package com.devozs.components.harticle.training.service;

import com.devozs.components.harticle.training.domain.TrainingStatus;
import com.devozs.components.harticle.training.dto.TrainingLogDto;
import com.devozs.components.harticle.training.dto.TrainingSessionDto;
import com.devozs.components.harticle.training.dto.TrainingSessionSummary;
import com.devozs.components.harticle.training.entity.ComputeResource;
import com.devozs.components.harticle.training.entity.TrainingLog;
import com.devozs.components.harticle.training.entity.TrainingSession;
import com.devozs.components.harticle.training.repository.ComputeResourceRepository;
import com.devozs.components.harticle.training.repository.TrainingLogRepository;
import com.devozs.components.harticle.training.repository.TrainingSessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    private final DatasetExportService datasetExportService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TrainingSessionService(TrainingSessionRepository sessionRepository,
                                  TrainingLogRepository logRepository,
                                  ComputeResourceRepository resourceRepository,
                                  DatasetExportService datasetExportService) {
        this.sessionRepository = sessionRepository;
        this.logRepository = logRepository;
        this.resourceRepository = resourceRepository;
        this.datasetExportService = datasetExportService;
    }

    public List<TrainingSession> getAll() {
        return sessionRepository.findAllByOrderByCreatedAtDesc();
    }

    public TrainingSession get(UUID id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("training session not found: " + id));
    }

    /**
     * Create a session in PENDING (immediately claimable) and kick off dataset
     * export asynchronously. The agent won't get a usable dataset URI until export
     * finishes, but claim+setup overlaps the export so this is rarely a wait.
     */
    public TrainingSession create(TrainingSessionDto dto) {
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
                .build();
        session = sessionRepository.save(session);
        exportDatasetAsync(session.getId());
        return session;
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

    public void delete(UUID id) {
        logRepository.deleteBySessionId(id);
        sessionRepository.deleteById(id);
    }

    // --- read-back -----------------------------------------------------------

    public TrainingSessionSummary snapshot(UUID id) {
        return toSummary(get(id));
    }

    public List<TrainingSessionSummary> summaries() {
        return getAll().stream().map(this::toSummary).toList();
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
        boolean resumable = (s.getStatus() == TrainingStatus.STOPPED || s.getStatus() == TrainingStatus.FAILED)
                && s.getCheckpointUri() != null && !s.getCheckpointUri().isBlank();
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
                .checkpointUri(s.getCheckpointUri())
                .resumable(resumable)
                .pushToHub(s.isPushToHub())
                .outputModelRef(s.getOutputModelRef())
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
