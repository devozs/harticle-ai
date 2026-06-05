package com.devozs.components.harticle.training.domain;

/**
 * Where a COMPLETED training session's model can actually be loaded from, derived
 * live from its {@code outputModelRef} plus the state of the box that trained it.
 * Used to gate the inference picker and reject unrunnable models up front.
 *
 * <ul>
 *   <li>{@code PORTABLE} — the ref is an S3 URI or a HF hub id; the engine can resolve
 *       it from anywhere, so it runs on LOCAL CPU or any live GPU/HPU box.</li>
 *   <li>{@code LOCAL_AVAILABLE} — a {@code file://} model whose files are present in
 *       management storage (a local-fs run, or one fetched-to-local); runs on LOCAL.</li>
 *   <li>{@code REMOTE_ONLY} — a {@code file://} model not yet on this host, but the box
 *       that trained it is still registered and live; fetchable, and GPU-runnable on
 *       that same box.</li>
 *   <li>{@code REMOTE_OFFLINE} — a {@code file://} model not on this host whose training
 *       box is still registered but not live (heartbeat lapsed). The files are NOT lost:
 *       once the box is back online it flips to {@code REMOTE_ONLY} and can be fetched /
 *       run. Distinct from ORPHANED so the UI doesn't wrongly say "box removed".</li>
 *   <li>{@code ORPHANED} — a {@code file://} model not on this host whose training box
 *       record is gone (admin deleted it / never recorded). The files are lost with the
 *       box; the model cannot be run or fetched and should be re-trained.</li>
 * </ul>
 */
public enum ModelReachability {
    PORTABLE,
    LOCAL_AVAILABLE,
    REMOTE_ONLY,
    REMOTE_OFFLINE,
    ORPHANED,
}
