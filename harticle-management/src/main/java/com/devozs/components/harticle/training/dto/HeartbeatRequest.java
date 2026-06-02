package com.devozs.components.harticle.training.dto;

import com.devozs.components.harticle.training.domain.ComputeResourceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Agent liveness ping; carries the agent's self-reported status. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartbeatRequest {
    private ComputeResourceStatus status;
}
