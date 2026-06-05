package com.devozs.components.harticle.training.dto;

import com.devozs.components.harticle.training.domain.ComputeResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Admin payload to create a training session. {@code hyperparams} is free-form
 * (epochs, batchSize, learningRate, ...); only what the chosen backend reads is used.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSessionDto {
    private String name;
    private String baseModel;
    private ComputeResourceType requiredType;
    private Boolean stubMode;
    private Boolean pushToHub;
    /** Fetch the trained model to local on successful completion (default true). */
    private Boolean autoFetchLocal;

    // dataset scope (null/empty reporterIds = all scraped articles)
    private List<String> reporterIds;

    // hyperparameters — sent through to the trainer as-is
    private Integer epochs;
    private Integer batchSize;
    private Double learningRate;
    private Integer warmupSteps;
    private Double weightDecay;
    private Integer saveSteps;
    private Integer contextLength;
}
