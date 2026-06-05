package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Posted by the agent after it finishes pushing a model up (fetch-to-local).
 * {@code ok=false} carries the failure reason so the admin can retry.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelUploadResult {
    private boolean ok;
    private String message;
}
