package com.devozs.components.harticle.scraper.service;

import com.devozs.components.harticle.scraper.domain.ParserStrategy;
import com.devozs.components.harticle.scraper.dto.ArticlePreviewResult;
import com.devozs.components.harticle.scraper.dto.FieldResult;
import com.devozs.components.harticle.scraper.dto.ListingPreviewResult;
import com.devozs.components.harticle.scraper.dto.PreviewRequest;
import com.devozs.components.harticle.scraper.config.ScraperHttpConfig;
import com.devozs.components.harticle.scraper.entity.ScrapeSite;
import com.devozs.components.harticle.scraper.repository.ScrapeSiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Dry-run extraction for testing rules (especially regex) against live pages
 * WITHOUT persisting. Loads a site's saved rules, applies any inline overrides
 * from the request, fetches the URL, runs {@link ExtractionRuleEngine}, and
 * reports a clear per-field + overall verdict.
 */
@Service
@Slf4j
public class ScrapePreviewService {

    private static final int SAMPLE_LEN = 160;
    private static final int MAX_SAMPLE_LINKS = 10;

    private final ScrapeSiteRepository siteRepository;
    private final ExtractionRuleEngine ruleEngine;
    private final RestTemplate restTemplate;

    public ScrapePreviewService(ScrapeSiteRepository siteRepository,
                                ExtractionRuleEngine ruleEngine,
                                @Qualifier(ScraperHttpConfig.SCRAPER_REST_TEMPLATE) RestTemplate restTemplate) {
        this.siteRepository = siteRepository;
        this.ruleEngine = ruleEngine;
        this.restTemplate = restTemplate;
    }

    /** Fetch an article URL and report what each extraction rule captured. */
    public ArticlePreviewResult previewArticle(PreviewRequest req) {
        ScrapeSite site = effectiveSite(req);
        String html = fetch(req.getUrl());
        boolean fetchOk = html != null;
        html = html == null ? "" : html;

        List<FieldResult> fields = new ArrayList<>();
        String title = ruleEngine.extractTitle(site, html);
        fields.add(field("title", site.getTitleRule(), title));
        String subtitle = ruleEngine.extractSubtitle(site, html);
        fields.add(field("subTitle", site.getSubtitleRule(), subtitle));
        String content = ruleEngine.extractContent(site, html);
        fields.add(field("content", site.getContentRule(), content));
        fields.add(field("publishedDate", site.getDateRule(), ruleEngine.extractDate(site, html)));
        fields.add(field("reporterName", site.getReporterRule(), ruleEngine.extractReporter(site, html)));

        // Success mirrors the real scraper's persistence gate: title present AND content present.
        boolean titleOk = title != null && !title.isBlank();
        boolean contentOk = content != null && !content.isBlank();
        boolean success = fetchOk && titleOk && contentOk;

        String verdict;
        if (!fetchOk) {
            verdict = "FETCH FAILED - could not retrieve the page (network/proxy/404).";
        } else if (!titleOk) {
            verdict = "FAIL - title rule matched nothing; the real scraper would skip this article.";
        } else if (!contentOk) {
            verdict = "PARTIAL - title matched but content is empty; row would persist with empty body.";
        } else {
            verdict = "OK - title and content extracted; this article would be scraped successfully.";
        }

        return ArticlePreviewResult.builder()
                .url(req.getUrl())
                .siteName(site.getName())
                .success(success)
                .verdict(verdict)
                .htmlLength(html.length())
                .fetchOk(fetchOk)
                .fields(fields)
                .build();
    }

    /** Fetch a listing/author page and report how many article links the link rule finds. */
    public ListingPreviewResult previewListing(PreviewRequest req) {
        ScrapeSite site = effectiveSite(req);
        String html = fetch(req.getUrl());
        boolean fetchOk = html != null;
        html = html == null ? "" : html;

        List<String> links = ruleEngine.extractArticleLinks(site, html);
        List<String> sample = new ArrayList<>();
        for (String link : links) {
            if (sample.size() >= MAX_SAMPLE_LINKS) {
                break;
            }
            sample.add(site.getBaseUrl() + link);
        }

        boolean success = fetchOk && !links.isEmpty();
        String verdict;
        if (!fetchOk) {
            verdict = "FETCH FAILED - could not retrieve the listing page.";
        } else if (links.isEmpty()) {
            verdict = "FAIL - link rule found 0 article links; the crawl would stop here.";
        } else {
            verdict = "OK - found " + links.size() + " article link(s) on this page.";
        }

        return ListingPreviewResult.builder()
                .url(req.getUrl())
                .siteName(site.getName())
                .success(success)
                .verdict(verdict)
                .htmlLength(html.length())
                .fetchOk(fetchOk)
                .linkCount(links.size())
                .linkRule(site.getArticleLinkRule())
                .sampleLinks(sample)
                .build();
    }

    // --- helpers -------------------------------------------------------------

    /** Build an in-memory site = saved rules (if siteId given) with inline overrides applied. Never saved. */
    private ScrapeSite effectiveSite(PreviewRequest req) {
        ScrapeSite site = new ScrapeSite();
        if (req.getSiteId() != null) {
            ScrapeSite saved = siteRepository.findById(req.getSiteId())
                    .orElseThrow(() -> new NoSuchElementException("scrape site not found: " + req.getSiteId()));
            site.setName(saved.getName());
            site.setBaseUrl(saved.getBaseUrl());
            site.setParserStrategy(saved.getParserStrategy());
            site.setRuleType(saved.getRuleType());
            site.setArticleLinkRule(saved.getArticleLinkRule());
            site.setArticleLinkFilter(saved.getArticleLinkFilter());
            site.setTitleRule(saved.getTitleRule());
            site.setSubtitleRule(saved.getSubtitleRule());
            site.setContentRule(saved.getContentRule());
            site.setContentSource(saved.getContentSource());
            site.setDateRule(saved.getDateRule());
            site.setReporterRule(saved.getReporterRule());
            site.setListingStopMarker(saved.getListingStopMarker());
        } else {
            site.setName("(inline)");
            site.setParserStrategy(ParserStrategy.GENERIC_REGEX);
        }

        // Inline overrides win, so you can iterate on a single regex live.
        if (req.getBaseUrl() != null) site.setBaseUrl(req.getBaseUrl());
        if (req.getParserStrategy() != null) site.setParserStrategy(req.getParserStrategy());
        if (req.getArticleLinkRule() != null) site.setArticleLinkRule(req.getArticleLinkRule());
        if (req.getArticleLinkFilter() != null) site.setArticleLinkFilter(req.getArticleLinkFilter());
        if (req.getTitleRule() != null) site.setTitleRule(req.getTitleRule());
        if (req.getSubtitleRule() != null) site.setSubtitleRule(req.getSubtitleRule());
        if (req.getContentRule() != null) site.setContentRule(req.getContentRule());
        if (req.getContentSource() != null) site.setContentSource(req.getContentSource());
        if (req.getDateRule() != null) site.setDateRule(req.getDateRule());
        if (req.getReporterRule() != null) site.setReporterRule(req.getReporterRule());
        if (req.getListingStopMarker() != null) site.setListingStopMarker(req.getListingStopMarker());

        if (site.getBaseUrl() == null) {
            site.setBaseUrl("");
        }
        return site;
    }

    private FieldResult field(String name, String rule, String value) {
        boolean matched = value != null && !value.isBlank();
        String sample = value == null ? null
                : (value.length() > SAMPLE_LEN ? value.substring(0, SAMPLE_LEN) + "..." : value);
        return FieldResult.builder()
                .field(name)
                .rule(rule)
                .matched(matched)
                .length(value == null ? 0 : value.length())
                .sample(sample)
                .build();
    }

    private String fetch(String url) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            log.warn("preview fetch failed for {}: {}", url, e.getMessage());
            return null;
        }
    }
}
