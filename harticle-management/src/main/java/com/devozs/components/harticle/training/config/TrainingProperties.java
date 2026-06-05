package com.devozs.components.harticle.training.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** Tunables for the training module. */
@Data
@ConfigurationProperties(prefix = "harticle.training")
public class TrainingProperties {

    /**
     * A RUNNING session whose agent has not been heard from for this many seconds
     * is reaped (marked FAILED, its resource freed). Must exceed the agent's
     * progress-report interval with margin.
     */
    private long stallTimeoutSeconds = 300;

    /**
     * A resource whose last heartbeat is older than this is shown OFFLINE. Should
     * exceed the agent heartbeat interval.
     */
    private long heartbeatTimeoutSeconds = 60;

    /** TTL for presigned dataset/checkpoint download URLs handed to the agent. */
    private long presignTtlMinutes = 720;

    /**
     * A GPU/HPU inference run left PENDING for this many seconds (no live agent of its
     * type ever claimed it — e.g. its box was removed) is failed so the FE stops polling.
     */
    private long inferenceClaimTimeoutSeconds = 120;

    /** How often the reaper sweeps for stalled sessions / offline resources (ms). */
    private long reaperIntervalMs = 30000;
}
