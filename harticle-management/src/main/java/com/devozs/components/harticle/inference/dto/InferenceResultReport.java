package com.devozs.components.harticle.inference.dto;

import com.devozs.components.common.domain.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * What an agent (or the LOCAL engine path) reports back after running an inference
 * job. Either {@code outputs} (success) or {@code errorType}/{@code message}
 * (failure) is populated.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InferenceResultReport {
    private List<ArticleSample> outputs;
    private ErrorType errorType;
    private String message;
}
