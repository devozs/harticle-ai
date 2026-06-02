package com.devozs.components.harticle.training.service;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.harticle.training.config.TrainingProperties;
import com.devozs.components.harticle.training.domain.TrainingStatus;
import com.devozs.components.harticle.training.entity.ComputeResource;
import com.devozs.components.harticle.training.entity.TrainingSession;
import com.devozs.components.harticle.training.repository.ComputeResourceRepository;
import com.devozs.components.harticle.training.repository.TrainingSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Periodic sweeper. Two jobs:
 * 1) RUNNING sessions whose agent has gone silent past the stall timeout are
 *    failed and their resource freed (the box may have crashed mid-run). The
 *    checkpoint, if any, is kept so the admin can resume.
 * 2) Resources whose heartbeat has lapsed are shown OFFLINE.
 *
 * <p>This is the persisted counterpart to the scraper's {@code secondsSinceActivity}
 * stall signal, but here it must act across the network since the worker is remote.
 */
@Service
@Slf4j
public class TrainingReaperService {

    private final TrainingSessionRepository sessionRepository;
    private final ComputeResourceRepository resourceRepository;
    private final ComputeResourceService resourceService;
    private final TrainingSessionService sessionService;
    private final TrainingProperties properties;

    public TrainingReaperService(TrainingSessionRepository sessionRepository,
                                 ComputeResourceRepository resourceRepository,
                                 ComputeResourceService resourceService,
                                 TrainingSessionService sessionService,
                                 TrainingProperties properties) {
        this.sessionRepository = sessionRepository;
        this.resourceRepository = resourceRepository;
        this.resourceService = resourceService;
        this.sessionService = sessionService;
        this.properties = properties;
    }

    @Scheduled(fixedDelayString = "${harticle.training.reaper-interval-ms:30000}")
    public void reap() {
        reapStalledSessions();
        resourceService.markStaleOffline();
    }

    private void reapStalledSessions() {
        Date cutoff = new Date(System.currentTimeMillis() - properties.getStallTimeoutSeconds() * 1000);
        for (TrainingSession session : sessionRepository.findStalledRunning(cutoff)) {
            log.warn("reaping stalled session {} (last seen {})", session.getId(), session.getLastAgentSeenAt());
            session.setStatus(TrainingStatus.FAILED);
            session.setErrorType(ErrorType.COMMUNICATION);
            session.setErrorMessage("agent went silent; presumed dead after "
                    + properties.getStallTimeoutSeconds() + "s");
            sessionRepository.save(session);
            sessionService.appendLog(session.getId(), "ERROR",
                    "reaped: no agent activity for " + properties.getStallTimeoutSeconds() + "s");
            if (session.getAssignedResourceId() != null) {
                resourceRepository.findById(session.getAssignedResourceId())
                        .ifPresent(this::freeIfHoldingSession);
            }
        }
    }

    private void freeIfHoldingSession(ComputeResource resource) {
        resourceService.markIdle(resource);
    }
}
