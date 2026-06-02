package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Agent → management: redeem a one-time enrollment code for a bearer token. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentEnrollRequest {
    private String code;
    /** JSON string of detected specs (gpu/hpu count, VRAM, driver + agent versions, hostname). */
    private String capabilities;
}
