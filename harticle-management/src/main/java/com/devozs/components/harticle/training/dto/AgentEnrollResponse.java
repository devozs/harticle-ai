package com.devozs.components.harticle.training.dto;

import com.devozs.components.harticle.training.domain.ComputeResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/** Management → agent: the per-agent bearer token (returned once) plus identity. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentEnrollResponse {
    private UUID resourceId;
    private String name;
    private ComputeResourceType type;
    private String token;
}
