package com.devozs.components.harticle.inference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Body of the management → engine {@code POST /engine/infer} call (the LOCAL CPU
 * path). The engine loads {@code modelRef} (HF repo id, or downloads
 * {@code modelKeyPrefix} from {@code storageKind} storage) and generates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EngineInferRequest {
    private String modelRef;
    private String storageKind;       // "local" | "s3"
    private String modelKeyPrefix;    // e.g. models/{sourceSessionId}
    private String prompt;
    private Integer temperature;      // 0..100
    private Integer maxLength;
    private Integer numReturnSequences;
}
