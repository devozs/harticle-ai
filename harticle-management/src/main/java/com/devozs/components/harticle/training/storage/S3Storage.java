package com.devozs.components.harticle.training.storage;

import com.devozs.components.harticle.training.config.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;

/**
 * S3-compatible storage (Nebius and friends). The efficiency win: the agent pulls
 * datasets and pushes/pulls checkpoints directly against the bucket via presigned
 * URLs, so large artifacts never transit the Java app or sit on an expensive PVC.
 */
@Slf4j
public class S3Storage implements StorageService {

    private final S3Client client;
    private final S3Presigner presigner;
    private final String bucket;

    public S3Storage(StorageProperties.S3 cfg) {
        this.bucket = cfg.getBucket();
        StaticCredentialsProvider creds = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(cfg.getAccessKey(), cfg.getSecretKey()));
        S3Configuration s3cfg = S3Configuration.builder()
                .pathStyleAccessEnabled(cfg.isPathStyle())
                .build();
        var clientBuilder = S3Client.builder()
                .region(Region.of(cfg.getRegion()))
                .credentialsProvider(creds)
                .serviceConfiguration(s3cfg);
        var presignerBuilder = S3Presigner.builder()
                .region(Region.of(cfg.getRegion()))
                .credentialsProvider(creds)
                .serviceConfiguration(s3cfg);
        if (cfg.getEndpoint() != null && !cfg.getEndpoint().isBlank()) {
            URI ep = URI.create(cfg.getEndpoint());
            clientBuilder.endpointOverride(ep);
            presignerBuilder.endpointOverride(ep);
        }
        this.client = clientBuilder.build();
        this.presigner = presignerBuilder.build();
    }

    @Override
    public String write(String key, InputStream data, long contentLength) {
        client.putObject(PutObjectRequest.builder().bucket(bucket).key(key).build(),
                RequestBody.fromInputStream(data, contentLength));
        return resolve(key);
    }

    @Override
    public InputStream read(String key) {
        return client.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build());
    }

    @Override
    public boolean exists(String key) {
        try {
            client.headObject(HeadObjectRequest.builder().bucket(bucket).key(key).build());
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        }
    }

    @Override
    public void delete(String key) {
        client.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(key).build());
    }

    @Override
    public String presignGet(String key, Duration ttl) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .getObjectRequest(GetObjectRequest.builder().bucket(bucket).key(key).build())
                .build();
        return presigner.presignGetObject(presignRequest).url().toString();
    }

    @Override
    public String resolve(String key) {
        return "s3://" + bucket + "/" + key;
    }

    @Override
    public String kind() {
        return "s3";
    }
}
