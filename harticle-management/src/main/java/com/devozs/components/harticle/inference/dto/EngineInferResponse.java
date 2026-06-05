package com.devozs.components.harticle.inference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/** Body of the engine's {@code POST /engine/infer} response. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EngineInferResponse {
    private List<String> outputs;
    private String device;     // "cpu" | "cuda" | ...
    private String model;
    private String error;      // non-null on failure
}
