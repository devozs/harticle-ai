package com.devozs.components.harticle.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** One extracted field's outcome: the regex tried, whether it matched, and a sample of the value. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldResult {
    private String field;
    private String rule;
    private boolean matched;
    private int length;
    /** Trimmed preview of the extracted value (full value can be long). */
    private String sample;
}
