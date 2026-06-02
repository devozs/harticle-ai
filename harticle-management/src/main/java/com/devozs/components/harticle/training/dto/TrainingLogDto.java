package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** A persisted log line as served to the FE monitor (incremental tail by seq). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingLogDto {
    private long seq;
    private String level;
    private String message;
    private Long loggedAtEpochMs;
}
