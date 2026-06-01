package com.devozs.components.harticle.scraper.dto;

import com.devozs.components.harticle.scraper.domain.ContentSource;
import com.devozs.components.harticle.scraper.domain.ParserStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Dry-run preview input. Provide a {@code siteId} to load that site's saved
 * rules, then optionally override any rule field inline to iterate on regex
 * WITHOUT persisting anything to the DB. {@code baseUrl}/{@code parserStrategy}
 * may also be supplied inline for a site that doesn't exist yet.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreviewRequest {

    /** Existing site to load rules from. Optional if all rules are supplied inline. */
    private UUID siteId;

    /** Article URL (for article preview) or listing URL (for listing preview). */
    private String url;

    // --- inline overrides (null = fall back to the loaded site) --------------
    private String baseUrl;
    private ParserStrategy parserStrategy;
    private String articleLinkRule;
    private String articleLinkFilter;
    private String titleRule;
    private String subtitleRule;
    private String contentRule;
    private ContentSource contentSource;
    private String dateRule;
    private String reporterRule;
    private String listingStopMarker;
}
