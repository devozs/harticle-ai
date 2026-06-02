package com.devozs.components.harticle.training.domain;

/**
 * Whether a registered box has passed its readiness preflight and may run jobs.
 *
 * <p>UNVERIFIED: enrolled but never checked. VERIFYING: a preflight is in flight
 * (on agent start or admin re-verify). READY: identified its accelerator
 * (nvidia-smi/hl-smi) AND ran a tiny real LLM workload successfully — only then
 * is it claimable. FAILED: the preflight errored; {@code readinessDetail} carries
 * why.
 */
public enum ComputeResourceReadiness {
    UNVERIFIED,
    VERIFYING,
    READY,
    FAILED
}
