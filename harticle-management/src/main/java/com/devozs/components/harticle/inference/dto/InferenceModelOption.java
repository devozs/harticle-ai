package com.devozs.components.harticle.inference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * A trained model the admin can pick to test: one COMPLETED training session that
 * produced an {@code outputModelRef}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InferenceModelOption {
    private UUID sessionId;
    private String name;
    private String baseModel;
    private String outputModelRef;
    /** Whether this model is reachable for LOCAL CPU inference on the mgmt host. */
    private boolean availableLocal;
}
