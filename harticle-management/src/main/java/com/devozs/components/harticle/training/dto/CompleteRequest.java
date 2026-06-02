package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Terminal success: the final model reference (storage URI and/or HF Hub repo). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteRequest {
    private String outputModelRef;
    private String finalCheckpointUri;
}
