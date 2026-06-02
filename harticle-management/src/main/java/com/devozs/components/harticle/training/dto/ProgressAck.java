package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response to a progress tick. {@code stopRequested} is the cooperative-cancel
 * signal: when true, the agent checkpoints and stops at the next step boundary
 * (the cross-network analogue of {@code ScrapeProgressTracker.isCancelRequested()}).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressAck {
    private boolean stopRequested;
}
