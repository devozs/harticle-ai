package com.devozs.components.harticle.inference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Admin request to create + submit an inference test. {@code target} is either the
 * literal {@code "LOCAL"} (run on the deployment CPU via the engine) or a compute
 * resource id (run on that GPU/HPU agent). Generation knobs default server-side.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InferenceRunDto {
    /** The COMPLETED training session whose model to test. */
    private UUID sourceSessionId;
    /** {@code "LOCAL"} or a compute resource UUID (as string). */
    private String target;
    private String prompt;

    /**
     * Single "absurdity" dial (0..100): the simple end-user-facing knob. When set,
     * the server derives {@code temperature} and {@code maxLength} from it. Explicit
     * {@code temperature}/{@code maxLength} still win (advanced override).
     */
    private Integer absurdity;

    private Integer temperature;          // 0..100 (matches the engine's /100 convention)
    private Integer maxLength;
    private Integer numReturnSequences;
}
