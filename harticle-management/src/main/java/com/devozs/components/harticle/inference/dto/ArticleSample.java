package com.devozs.components.harticle.inference.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * One generated article sample, parsed by the engine into the title / sub-title /
 * paragraph shape (the inverse of the {@code title. subTitle. content} training
 * framing). {@code title} and {@code subTitle} may be empty when a sample didn't
 * split cleanly — the whole text then lands in {@code paragraph}.
 *
 * <p>{@link JsonIgnoreProperties} keeps deserialization lenient so the engine can
 * add fields later without breaking older management builds.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleSample {
    private String title;
    private String subTitle;
    private String paragraph;
}
