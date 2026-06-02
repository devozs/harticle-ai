package com.devozs.components.harticle.training.service;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.harticle.training.config.TrainingProperties;
import com.devozs.components.harticle.training.domain.ComputeResourceReadiness;
import com.devozs.components.harticle.training.domain.ComputeResourceStatus;
import com.devozs.components.harticle.training.domain.ComputeResourceType;
import com.devozs.components.harticle.training.domain.TrainingBackend;
import com.devozs.components.harticle.training.domain.TrainingStatus;
import com.devozs.components.harticle.training.dto.AgentJobDto;
import com.devozs.components.harticle.training.dto.CompleteRequest;
import com.devozs.components.harticle.training.dto.ErrorReport;
import com.devozs.components.harticle.training.dto.ProgressAck;
import com.devozs.components.harticle.training.dto.ProgressReport;
import com.devozs.components.harticle.training.entity.ComputeResource;
import com.devozs.components.harticle.training.entity.TrainingSession;
import com.devozs.components.harticle.training.repository.ComputeResourceRepository;
import com.devozs.components.harticle.training.repository.TrainingSessionRepository;
import com.devozs.components.harticle.training.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Server side of the agent polling protocol. Owns the transactional state
 * transitions an agent drives: claim a job, report progress (and learn of a
 * cooperative stop), checkpoint, and terminate (complete / stopped / error).
 */
@Service
@Slf4j
public class TrainingAgentService {

    private final TrainingSessionRepository sessionRepository;
    private final ComputeResourceRepository resourceRepository;
    private final ComputeResourceService resourceService;
    private final TrainingSessionService sessionService;
    private final StorageService storageService;
    private final TrainingProperties properties;

    public TrainingAgentService(TrainingSessionRepository sessionRepository,
                                ComputeResourceRepository resourceRepository,
                                ComputeResourceService resourceService,
                                TrainingSessionService sessionService,
                                StorageService storageService,
                                TrainingProperties properties) {
        this.sessionRepository = sessionRepository;
        this.resourceRepository = resourceRepository;
        this.resourceService = resourceService;
        this.sessionService = sessionService;
        this.storageService = storageService;
        this.properties = properties;
    }

    /**
     * Atomically claim the next matching session for this resource. The repository
     * uses {@code FOR UPDATE SKIP LOCKED} so concurrent agents never grab the same
     * row. Returns empty if nothing is claimable.
     */
    @Transactional
    public Optional<AgentJobDto> claim(ComputeResource resource) {
        // Only an enabled box that has passed its readiness preflight may claim.
        if (!resource.isEnabled() || resource.getReadiness() != ComputeResourceReadiness.READY) {
            return Optional.empty();
        }
        ComputeResourceType type = resource.getType();
        Optional<TrainingSession> picked = sessionRepository.lockNextClaimable(type.name());
        if (picked.isEmpty()) {
            return Optional.empty();
        }
        TrainingSession session = picked.get();
        boolean resume = session.getStatus() == TrainingStatus.RESUMING;

        session.setStatus(TrainingStatus.ASSIGNED);
        session.setAssignedResourceId(resource.getId());
        session.setLastAgentSeenAt(new Date());
        sessionRepository.save(session);

        resource.setStatus(ComputeResourceStatus.BUSY);
        resource.setCurrentSessionId(session.getId());
        resource.setLastHeartbeat(new Date());
        resourceRepository.save(resource);

        sessionService.appendLog(session.getId(), "INFO",
                (resume ? "resumed" : "claimed") + " by " + resource.getName());
        return Optional.of(buildJob(session, resource, resume));
    }

    private AgentJobDto buildJob(TrainingSession session, ComputeResource resource, boolean resume) {
        TrainingBackend backend = session.isStubMode()
                ? TrainingBackend.STUB
                : switch (resource.getType()) {
                    case CUDA -> TrainingBackend.CUDA;
                    case HPU -> TrainingBackend.HPU;
                };

        String datasetUrl = null;
        if (session.getDatasetUri() != null) {
            datasetUrl = storageService.presignGet(
                    DatasetExportService.datasetKey(session.getId()),
                    Duration.ofMinutes(properties.getPresignTtlMinutes()));
        }

        return AgentJobDto.builder()
                .sessionId(session.getId())
                .status(session.getStatus())
                .backend(backend)
                .baseModel(session.getBaseModel())
                .hyperparams(session.getHyperparams())
                .datasetSpec(session.getDatasetSpec())
                .datasetUri(session.getDatasetUri())
                .datasetDownloadUrl(datasetUrl)
                .resume(resume)
                .checkpointUri(resume ? session.getCheckpointUri() : null)
                .storageKind(storageService.kind())
                .checkpointKeyPrefix("checkpoints/" + session.getId())
                .modelKeyPrefix("models/" + session.getId())
                .pushToHub(session.isPushToHub())
                .build();
    }

    /**
     * Record a progress tick and return whether a stop has been requested. This is
     * the cooperative-cancel channel: the agent learns of admin STOP here and acts
     * at the next step boundary.
     */
    @Transactional
    public ProgressAck recordProgress(TrainingSession session, ProgressReport report) {
        // First progress after ASSIGNED means training actually started.
        if (session.getStatus() == TrainingStatus.ASSIGNED) {
            session.setStatus(TrainingStatus.RUNNING);
        }
        if (report.getEpoch() != null) session.setCurrentEpoch(report.getEpoch());
        if (report.getTotalEpochs() != null) session.setTotalEpochs(report.getTotalEpochs());
        if (report.getStep() != null) session.setCurrentStep(report.getStep());
        if (report.getTotalSteps() != null) session.setTotalSteps(report.getTotalSteps());
        if (report.getLoss() != null) session.setLastLoss(report.getLoss());
        if (report.getPercent() != null) session.setProgressPercent(report.getPercent());
        session.setLastAgentSeenAt(new Date());
        sessionRepository.save(session);

        boolean stop = session.getStatus() == TrainingStatus.STOP_REQUESTED;
        return ProgressAck.builder().stopRequested(stop).build();
    }

    @Transactional
    public void recordCheckpoint(TrainingSession session, String checkpointUri) {
        session.setCheckpointUri(checkpointUri);
        session.setLastAgentSeenAt(new Date());
        sessionRepository.save(session);
        sessionService.appendLog(session.getId(), "INFO", "checkpoint saved: " + checkpointUri);
    }

    @Transactional
    public void complete(TrainingSession session, CompleteRequest request) {
        session.setStatus(TrainingStatus.COMPLETED);
        session.setProgressPercent(100);
        session.setOutputModelRef(request.getOutputModelRef());
        if (request.getFinalCheckpointUri() != null) {
            session.setCheckpointUri(request.getFinalCheckpointUri());
        }
        session.setLastAgentSeenAt(new Date());
        sessionRepository.save(session);
        freeResource(session);
        sessionService.appendLog(session.getId(), "INFO", "completed; model=" + request.getOutputModelRef());
    }

    @Transactional
    public void stopped(TrainingSession session) {
        session.setStatus(TrainingStatus.STOPPED);
        session.setLastAgentSeenAt(new Date());
        sessionRepository.save(session);
        freeResource(session);
        sessionService.appendLog(session.getId(), "INFO", "stopped at agent (checkpoint kept for resume)");
    }

    @Transactional
    public void error(TrainingSession session, ErrorReport report) {
        session.setStatus(TrainingStatus.FAILED);
        session.setErrorType(report.getErrorType() == null ? ErrorType.INTERNAL : report.getErrorType());
        session.setErrorMessage(report.getMessage());
        session.setLastAgentSeenAt(new Date());
        sessionRepository.save(session);
        freeResource(session);
        sessionService.appendLog(session.getId(), "ERROR", "failed: " + report.getMessage());
    }

    private void freeResource(TrainingSession session) {
        if (session.getAssignedResourceId() == null) {
            return;
        }
        resourceRepository.findById(session.getAssignedResourceId())
                .ifPresent(resourceService::markIdle);
    }
}
