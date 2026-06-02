package com.devozs.components.harticle.training.domain;

/**
 * Which executor the Python agent should run for a claimed job. Derived from the
 * assigned resource's {@link ComputeResourceType}, unless the session is flagged
 * stub (local dev without a real accelerator).
 */
public enum TrainingBackend {
    CUDA,
    HPU,
    STUB
}
