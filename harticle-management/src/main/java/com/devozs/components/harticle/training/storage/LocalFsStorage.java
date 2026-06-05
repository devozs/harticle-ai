package com.devozs.components.harticle.training.storage;

import com.devozs.components.harticle.training.config.StorageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

/**
 * Filesystem-backed storage under a configured root (a host dir in dev, a k8s PVC
 * in prod). When management and the agent share the mount, the agent reads/writes
 * the resolved {@code file://} path directly — no bytes through the Java app.
 */
@Slf4j
public class LocalFsStorage implements StorageService {

    private final Path root;
    private final String publicBaseUrl;

    public LocalFsStorage(StorageProperties.Local cfg) {
        this.root = Paths.get(cfg.getRoot()).toAbsolutePath().normalize();
        this.publicBaseUrl = cfg.getPublicBaseUrl();
        try {
            Files.createDirectories(this.root);
        } catch (IOException e) {
            throw new UncheckedIOException("could not create storage root " + this.root, e);
        }
    }

    private Path pathFor(String key) {
        Path p = root.resolve(key).normalize();
        if (!p.startsWith(root)) {
            throw new IllegalArgumentException("key escapes storage root: " + key);
        }
        return p;
    }

    @Override
    public String write(String key, InputStream data, long contentLength) {
        Path target = pathFor(key);
        try {
            Files.createDirectories(target.getParent());
            Files.copy(data, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to write " + key, e);
        }
        return resolve(key);
    }

    @Override
    public InputStream read(String key) {
        try {
            return Files.newInputStream(pathFor(key));
        } catch (IOException e) {
            throw new UncheckedIOException("failed to read " + key, e);
        }
    }

    @Override
    public boolean exists(String key) {
        return Files.exists(pathFor(key));
    }

    @Override
    public void delete(String key) {
        try {
            Files.deleteIfExists(pathFor(key));
        } catch (IOException e) {
            throw new UncheckedIOException("failed to delete " + key, e);
        }
    }

    @Override
    public void deletePrefix(String keyPrefix) {
        Path dir = pathFor(keyPrefix);
        if (!Files.exists(dir)) {
            return;
        }
        try (var paths = Files.walk(dir)) {
            // Deepest-first so directories are emptied before they're removed.
            paths.sorted(java.util.Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.deleteIfExists(p);
                } catch (IOException e) {
                    throw new UncheckedIOException("failed to delete " + p, e);
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException("failed to delete prefix " + keyPrefix, e);
        }
    }

    @Override
    public String presignGet(String key, Duration ttl) {
        // No real presigning on a filesystem. If a public base URL is configured
        // (management reachable over HTTPS), advertise that; otherwise the agent
        // uses its authenticated /training/agent dataset endpoint.
        if (publicBaseUrl != null && !publicBaseUrl.isBlank()) {
            return publicBaseUrl.replaceAll("/$", "") + "/" + key;
        }
        return resolve(key);
    }

    @Override
    public String resolve(String key) {
        return pathFor(key).toUri().toString();
    }

    @Override
    public String kind() {
        return "local";
    }
}
