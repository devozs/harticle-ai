package com.devozs.components.harticle.training.dto;

import com.devozs.components.common.domain.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Terminal failure reported by the agent. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorReport {
    private ErrorType errorType;
    private String message;
}
