package com.devozs.components.harticle.training.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Storage backend config. {@code type=local} for dev / small datasets on a PVC;
 * {@code type=s3} for Nebius (or any S3-compatible) when datasets/checkpoints get
 * large and a PVC would be expensive.
 */
@Data
@ConfigurationProperties(prefix = "harticle.storage")
public class StorageProperties {

    /** {@code local} | {@code s3}. */
    private String type = "local";

    private final Local local = new Local();
    private final S3 s3 = new S3();

    @Data
    public static class Local {
        /** Filesystem root for artifacts (a host dir in dev, a PVC mount in k8s). */
        private String root = "/data/harticle-training";
        /**
         * Optional base URL management advertises so a non-co-located agent can GET
         * artifacts over HTTPS (the local-fs analogue of a presigned URL). Falls back
         * to relative agent endpoints when unset.
         */
        private String publicBaseUrl = "";
    }

    @Data
    public static class S3 {
        private String endpoint = "";
        private String bucket = "";
        private String region = "us-east-1";
        private String accessKey = "";
        private String secretKey = "";
        /** Nebius and most S3-compatibles need path-style addressing. */
        private boolean pathStyle = true;
    }
}
