package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Agent records the latest checkpoint location (drives RESUME). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckpointReport {
    private String checkpointUri;
}
