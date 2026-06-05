package com.devozs.components.harticle.inference.dto;

import com.devozs.components.harticle.training.domain.ModelReachability;
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
    /** Where the model can be loaded from; ORPHANED models are hidden/blocked. */
    private ModelReachability reachability;
    /** The reporter this model was trained for (null = general model over all reporters). */
    private UUID reporterId;
    /** That reporter's display name (null for a general model); drives the inference reporter picker. */
    private String reporterName;
}
