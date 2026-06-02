package com.devozs.components.harticle.training.dto;

import com.devozs.components.harticle.training.domain.TrainingBackend;
import com.devozs.components.harticle.training.domain.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * The work unit returned by {@code /claim}: everything the agent needs to run (or
 * resume) one training session. Dataset/checkpoint are referenced by URI +
 * download URL so the agent pulls them directly from storage when possible.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentJobDto {
    private UUID sessionId;
    private TrainingStatus status;        // ASSIGNED (fresh) or carries RESUMING intent
    private TrainingBackend backend;      // CUDA | HPU | STUB
    private String baseModel;
    private String hyperparams;           // JSON
    private String datasetSpec;           // JSON

    // dataset access
    private String datasetUri;            // canonical storage URI
    private String datasetDownloadUrl;    // presigned / direct URL the agent can GET

    // resume
    private boolean resume;
    private String checkpointUri;         // where to resume from (null on fresh run)

    // storage hints so the agent configures the right client for uploads
    private String storageKind;           // "local" | "s3"
    private String checkpointKeyPrefix;   // e.g. checkpoints/{sessionId}
    private String modelKeyPrefix;        // e.g. models/{sessionId}

    // output
    private boolean pushToHub;
}
