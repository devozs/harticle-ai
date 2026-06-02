package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** A single log line streamed by the agent. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogLine {
    private String level;
    private String message;
}
