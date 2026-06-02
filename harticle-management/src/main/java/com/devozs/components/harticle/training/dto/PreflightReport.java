package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Agent → management readiness verdict. Sent after the agent identifies its
 * accelerator (nvidia-smi/hl-smi) and runs a tiny real LLM workload. {@code ok}
 * gates whether the resource becomes claimable; {@code detail} is a device
 * summary on success or the error on failure; {@code capabilities} refreshes the
 * stored specs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreflightReport {
    private boolean ok;
    private String detail;
    private String capabilities;
}
