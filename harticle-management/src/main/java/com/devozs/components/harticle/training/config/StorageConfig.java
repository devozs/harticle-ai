package com.devozs.components.harticle.training.config;

import com.devozs.components.harticle.training.storage.LocalFsStorage;
import com.devozs.components.harticle.training.storage.S3Storage;
import com.devozs.components.harticle.training.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Picks the {@link StorageService} impl from {@code harticle.storage.type}. */
@Configuration
@EnableConfigurationProperties({StorageProperties.class, TrainingProperties.class})
@Slf4j
public class StorageConfig {

    @Bean
    public StorageService storageService(StorageProperties props) {
        String type = props.getType() == null ? "local" : props.getType().trim().toLowerCase();
        if ("s3".equals(type)) {
            log.info("training storage: S3 bucket={} endpoint={}", props.getS3().getBucket(), props.getS3().getEndpoint());
            return new S3Storage(props.getS3());
        }
        log.info("training storage: local-fs root={}", props.getLocal().getRoot());
        return new LocalFsStorage(props.getLocal());
    }
}
