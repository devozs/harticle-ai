package com.devozs.components.harticle.training.dto;

import com.devozs.components.harticle.training.domain.ComputeResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Admin payload to register/update a compute resource. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComputeResourceDto {
    private String name;
    private ComputeResourceType type;
    private Boolean enabled;
}
