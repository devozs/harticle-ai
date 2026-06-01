package com.devozs.components.harticle.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/** Result of a scrape run, aggregated across reporters. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapeRunSummary {
    @Builder.Default
    private int reportersProcessed = 0;
    @Builder.Default
    private int pagesFetched = 0;
    @Builder.Default
    private int articlesSaved = 0;
    @Builder.Default
    private int articlesUpdated = 0;
    @Builder.Default
    private int articlesSkipped = 0;
    @Builder.Default
    private int errors = 0;
    @Builder.Default
    private List<String> messages = new ArrayList<>();
}
