package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Agent → management progress tick, emitted from the Trainer callback per step. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressReport {
    private Double epoch;
    private Double totalEpochs;
    private Long step;
    private Long totalSteps;
    private Double loss;
    private Integer percent;
}
