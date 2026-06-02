package com.devozs.components.harticle.training.domain;

/**
 * Liveness/availability of a registered compute box, as last reported by its
 * agent's heartbeat (or inferred by the reaper when heartbeats stop).
 *
 * <p>OFFLINE: no recent heartbeat. IDLE: alive, free to claim a job. BUSY:
 * alive, executing a training session. ERROR: agent reported a fault.
 */
public enum ComputeResourceStatus {
    OFFLINE,
    IDLE,
    BUSY,
    ERROR
}
