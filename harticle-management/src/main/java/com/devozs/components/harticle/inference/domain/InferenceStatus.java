package com.devozs.components.harticle.inference.domain;

/**
 * Lifecycle of one inference test run. A simpler state machine than training (no
 * stop/resume): a run is queued ({@link #PENDING}), executes (LOCAL synchronously
 * via the engine, or on a GPU/HPU agent that {@link #ASSIGNED} then runs it), and
 * terminates {@link #COMPLETED} or {@link #FAILED}.
 */
public enum InferenceStatus {
    PENDING,
    ASSIGNED,
    RUNNING,
    COMPLETED,
    FAILED
}
