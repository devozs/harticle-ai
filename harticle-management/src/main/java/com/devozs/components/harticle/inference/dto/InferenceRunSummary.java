package com.devozs.components.harticle.inference.dto;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.harticle.inference.domain.InferenceStatus;
import com.devozs.components.harticle.training.domain.ComputeResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/** FE view of an inference run (list + single-run polling). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InferenceRunSummary {
    private UUID id;
    private UUID sourceSessionId;
    private String modelRef;
    private String baseModel;
    private String prompt;

    private InferenceStatus status;
    private boolean local;
    private ComputeResourceType requiredType;
    private UUID assignedResourceId;
    private String assignedResourceName;

    /** Decoded generated samples (null until COMPLETED). */
    private List<String> outputs;

    private String errorMessage;
    private ErrorType errorType;

    private Long createdAtEpochMs;
    private Long durationMs;
}
