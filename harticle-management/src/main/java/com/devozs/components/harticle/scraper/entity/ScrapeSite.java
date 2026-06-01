package com.devozs.components.harticle.scraper.entity;

import com.devozs.components.common.entity.BaseEntity;
import com.devozs.components.harticle.scraper.domain.ContentSource;
import com.devozs.components.harticle.scraper.domain.ParserStrategy;
import com.devozs.components.harticle.scraper.domain.RuleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * A scrapable news site plus the extraction rules used to mine its pages.
 * Ported from the {@code site_configuration} list in the legacy fetch_data.py.
 */
@Entity
@Table(name = "scrape_site")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ScrapeSite extends BaseEntity {

    private String name;

    @Column(name = "base_url")
    private String baseUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "parser_strategy")
    private ParserStrategy parserStrategy = ParserStrategy.GENERIC_REGEX;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type")
    private RuleType ruleType = RuleType.REGEX;

    /** Regex capturing article hrefs from a reporter listing page. */
    @Column(name = "article_link_rule")
    private String articleLinkRule;

    /** Substring a captured href must contain to be kept (e.g. "articles", "item", "Article"). */
    @Column(name = "article_link_filter")
    private String articleLinkFilter;

    @Column(name = "title_rule")
    private String titleRule;

    @Column(name = "subtitle_rule")
    private String subtitleRule;

    @Column(name = "content_rule")
    private String contentRule;

    /** Where the article body is read from (raw-HTML regex vs JSON-LD articleBody). */
    @Enumerated(EnumType.STRING)
    @Column(name = "content_source")
    private ContentSource contentSource = ContentSource.REGEX;

    @Column(name = "date_rule")
    private String dateRule;

    @Column(name = "reporter_rule")
    private String reporterRule;

    /**
     * Optional marker that ends the reporter's article list on a listing page
     * (e.g. {@code class="paging"} on sport5). When set and found, the listing
     * HTML is truncated here before the link rule runs, so trailing
     * "recommended" sections are ignored. Empty = scan the whole page.
     */
    @Column(name = "listing_stop_marker")
    private String listingStopMarker;

    /** Admin-set ceiling on how many listing pages to crawl per reporter (null = global default). */
    @Column(name = "max_history_pages")
    private Integer maxHistoryPages;

    private boolean enabled = true;
}
