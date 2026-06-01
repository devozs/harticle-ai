package com.devozs.components.harticle.scraper.dto;

import com.devozs.components.harticle.scraper.domain.ContentSource;
import com.devozs.components.harticle.scraper.domain.ParserStrategy;
import com.devozs.components.harticle.scraper.domain.RuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Create/update payload for a scrape site. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapeSiteDto {
    private String name;
    private String baseUrl;
    private ParserStrategy parserStrategy;
    private RuleType ruleType;
    private String articleLinkRule;
    private String articleLinkFilter;
    private String titleRule;
    private String subtitleRule;
    private String contentRule;
    private ContentSource contentSource;
    private String dateRule;
    private String reporterRule;
    private String listingStopMarker;
    private Integer maxHistoryPages;
    private Boolean enabled;
}
