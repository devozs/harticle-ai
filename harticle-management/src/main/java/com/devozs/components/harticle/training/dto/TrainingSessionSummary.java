package com.devozs.components.harticle.training.dto;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.harticle.training.domain.ComputeResourceType;
import com.devozs.components.harticle.training.domain.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Live snapshot of a training session for the FE monitor, the DB-backed analogue
 * of {@code ScrapeProgress}. {@code secondsSinceActivity} is the "stuck vs
 * working" signal: while RUNNING it should stay small.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSessionSummary {
    private UUID id;
    private String name;
    private String baseModel;
    private TrainingStatus status;
    private ComputeResourceType requiredType;
    private boolean stubMode;

    private int progressPercent;
    private Double currentEpoch;
    private Double totalEpochs;
    private Long currentStep;
    private Long totalSteps;
    private Double lastLoss;

    private UUID assignedResourceId;
    private String assignedResourceName;

    private String checkpointUri;
    private boolean resumable;
    private boolean pushToHub;
    private String outputModelRef;

    private String errorMessage;
    private ErrorType errorType;

    private Long createdAtEpochMs;
    private Long lastAgentSeenAtEpochMs;
    private Long secondsSinceActivity;
}
