package com.devozs.components.harticle.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Live snapshot of the current/last scrape run, polled by the FE so the user
 * can see whether the backend is actually progressing or wedged.
 *
 * <p>The key "stuck vs working" signal is {@code secondsSinceActivity}: while
 * {@code running} is true, it should stay small. If it climbs (e.g. fetches
 * hanging behind the corporate proxy), the run is stalled.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapeProgress {
    private boolean running;
    private boolean cancelRequested;
    private String phase;            // e.g. "listing", "article", "idle", "done", "stopping", "cancelled"
    private String currentSite;
    private String currentReporter;
    private String currentUrl;
    private int currentPage;

    private int reportersProcessed;
    private int pagesFetched;
    private int articlesSaved;
    private int articlesUpdated;
    private int articlesSkipped;
    private int errors;

    private Long startedAtEpochMs;
    private Long lastActivityEpochMs;
    private Long finishedAtEpochMs;
    private Long secondsSinceActivity;   // derived at read time
    private String lastMessage;
}
