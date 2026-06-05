package com.devozs.components.harticle.training.domain;

/**
 * Tracks bringing a remotely-trained model's files down to the management host
 * for LOCAL (CPU) inference.
 *
 * <ul>
 *   <li>{@code NONE} — not requested (or the model is already reachable: an S3 ref,
 *       a hub id, or a local-fs run that wrote to this host).</li>
 *   <li>{@code REQUESTED} — admin asked; waiting for the owning agent to see the
 *       flag on its next heartbeat and start pushing.</li>
 *   <li>{@code UPLOADING} — the agent has begun streaming files up.</li>
 *   <li>{@code AVAILABLE} — all files written under {@code models/{id}/}; the model
 *       can now run on LOCAL.</li>
 *   <li>{@code FAILED} — the push errored; the admin can retry.</li>
 * </ul>
 */
public enum ModelFetchStatus {
    NONE,
    REQUESTED,
    UPLOADING,
    AVAILABLE,
    FAILED,
}
