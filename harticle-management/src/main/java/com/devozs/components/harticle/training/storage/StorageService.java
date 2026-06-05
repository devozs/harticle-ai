package com.devozs.components.harticle.training.storage;

import java.io.InputStream;
import java.time.Duration;

/**
 * Pluggable object storage for training artifacts, shared between management
 * (writes datasets) and the Python agent (reads datasets, reads/writes
 * checkpoints + final models). Two impls selected by {@code harticle.storage.type}:
 * {@link LocalFsStorage} (disk / k8s PVC) and {@link S3Storage} (Nebius et al.).
 *
 * <p>Keys are logical paths, e.g. {@code datasets/{sessionId}.jsonl},
 * {@code checkpoints/{sessionId}/checkpoint-{step}/...}, {@code models/{sessionId}/}.
 *
 * <p>Efficiency: large artifacts should NOT transit the Java app. With S3,
 * {@link #presignGet(String, Duration)} hands the agent a direct download URL;
 * the agent pushes checkpoints straight to the bucket. With local-fs on a shared
 * PVC the agent reads/writes the mount directly; otherwise it falls back to the
 * management dataset/checkpoint HTTPS endpoints.
 */
public interface StorageService {

    /** Stream {@code data} to {@code key}; returns the canonical URI of the written object. */
    String write(String key, InputStream data, long contentLength);

    InputStream read(String key);

    boolean exists(String key);

    void delete(String key);

    /**
     * Delete every object under {@code keyPrefix} (a logical "directory", e.g.
     * {@code models/{id}/} or {@code checkpoints/{id}/}). On local-fs this removes
     * the directory tree; on S3 it lists and batch-deletes the prefix. Idempotent:
     * a missing prefix is a no-op. Used when a session/run is deleted and all its
     * artifacts must go with it.
     */
    void deletePrefix(String keyPrefix);

    /**
     * A time-boxed URL the agent can GET without management in the path. S3 returns
     * a presigned URL; local-fs returns a management-served HTTPS path (no real
     * presigning — the agent authenticates with its bearer token instead).
     */
    String presignGet(String key, Duration ttl);

    /** Canonical, stable URI for {@code key} (e.g. {@code s3://bucket/key} or {@code file:///root/key}). */
    String resolve(String key);

    /** Backend kind, surfaced to the agent so it picks the right client (boto3 vs fs vs http). */
    String kind();
}
