package com.devozs.components.harticle.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/** Create/update payload for a reporter on a site. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapeReporterDto {
    private UUID siteId;
    private String reporterKey;
    private String displayName;
    private String pathTemplate;
    private Boolean enabled;
}
