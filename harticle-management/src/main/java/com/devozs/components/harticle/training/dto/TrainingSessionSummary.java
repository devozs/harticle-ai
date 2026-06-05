package com.devozs.components.harticle.training.dto;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.harticle.training.domain.ComputeResourceType;
import com.devozs.components.harticle.training.domain.ModelFetchStatus;
import com.devozs.components.harticle.training.domain.ModelReachability;
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

    /** The reporter this model was trained for (null = general model over all reporters). */
    private UUID reporterId;
    /** Snapshot of that reporter's display name at train time (null for a general model). */
    private String reporterName;

    private String checkpointUri;
    private boolean resumable;
    /** True when this run may be re-run as a fresh attempt (FAILED or STOPPED). */
    private boolean rerunnable;
    private boolean pushToHub;
    private String outputModelRef;
    /** Whether this model's files are reachable for LOCAL CPU inference on the mgmt host. */
    private boolean modelAvailableLocal;
    /** Where the model can be loaded from (null until COMPLETED); ORPHANED = lost with its box. */
    private ModelReachability modelReachability;
    /** Progress of bringing a remote model's files to the mgmt host (fetch-to-local). */
    private ModelFetchStatus modelFetchStatus;

    /** The original run this is an attempt of (null for an original run). */
    private UUID parentSessionId;
    /** 1 for an original run; increments per re-run within the chain. */
    private int attemptNumber;

    private String errorMessage;
    private ErrorType errorType;

    private Long createdAtEpochMs;
    private Long lastAgentSeenAtEpochMs;
    private Long secondsSinceActivity;
}
