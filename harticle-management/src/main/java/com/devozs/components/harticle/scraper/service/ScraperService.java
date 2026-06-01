package com.devozs.components.harticle.scraper.service;

import com.devozs.components.harticle.scraper.dto.ScrapeRunSummary;
import com.devozs.components.harticle.scraper.entity.ScrapeReporter;
import com.devozs.components.harticle.scraper.entity.ScrapeSite;
import com.devozs.components.harticle.scraper.entity.ScrapedArticle;
import com.devozs.components.harticle.scraper.config.ScraperHttpConfig;
import com.devozs.components.harticle.scraper.repository.ScrapeReporterRepository;
import com.devozs.components.harticle.scraper.repository.ScrapedArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Drives reporter-article extraction. This is the Java port of the page-loop in
 * the legacy {@code harticle-engine/harticle/fetch_data.py}: for each enabled
 * reporter, page through their listing, fetch each article, extract structured
 * fields via {@link ExtractionRuleEngine}, skip URLs already stored, and persist
 * a {@link ScrapedArticle}. Results land in Postgres instead of CSV.
 */
@Service
@Slf4j
public class ScraperService {

    private final ScrapeReporterRepository reporterRepository;
    private final ScrapedArticleRepository articleRepository;
    private final ExtractionRuleEngine ruleEngine;
    private final RestTemplate restTemplate;
    private final ScrapeProgressTracker progress;

    /** Max listing pages to walk per reporter (legacy PAGES constant was 2000). */
    @Value("${harticle.scraper.max-pages:2000}")
    private int maxPages;

    /**
     * Give up on a reporter after this many consecutive listing-page failures.
     * Stops a dead/blocked site from grinding through all max-pages on timeouts.
     */
    @Value("${harticle.scraper.max-consecutive-failures:5}")
    private int maxConsecutiveFailures;

    public ScraperService(ScrapeReporterRepository reporterRepository,
                          ScrapedArticleRepository articleRepository,
                          ExtractionRuleEngine ruleEngine,
                          @Qualifier(ScraperHttpConfig.SCRAPER_REST_TEMPLATE) RestTemplate restTemplate,
                          ScrapeProgressTracker progress) {
        this.reporterRepository = reporterRepository;
        this.articleRepository = articleRepository;
        this.ruleEngine = ruleEngine;
        this.restTemplate = restTemplate;
        this.progress = progress;
    }

    /** Scrape every enabled reporter across all sites, asynchronously. */
    @Async
    public void scrapeAllEnabledAsync(Integer pagesOverride, boolean force) {
        ScrapeRunSummary summary = scrapeAllEnabled(pagesOverride, force);
        log.info("scrape-all finished: {}", summary);
    }

    public ScrapeRunSummary scrapeAllEnabled(Integer pagesOverride, boolean force) {
        ScrapeRunSummary summary = new ScrapeRunSummary();
        progress.start("all enabled reporters" + (force ? " (force)" : ""));
        try {
            for (ScrapeReporter reporter : reporterRepository.findByEnabledTrue()) {
                if (progress.isCancelRequested()) {
                    break;
                }
                scrapeReporter(reporter, summary, pagesOverride, force);
            }
        } finally {
            progress.finish();
        }
        return summary;
    }

    /** Scrape a single reporter by id, asynchronously. */
    @Async
    public void scrapeReporterAsync(java.util.UUID reporterId, Integer pagesOverride, boolean force) {
        reporterRepository.findById(reporterId).ifPresentOrElse(
                reporter -> {
                    ScrapeRunSummary summary = scrapeReporterSync(reporter, pagesOverride, force);
                    log.info("scrape reporter {} finished: {}", reporter.getDisplayName(), summary);
                },
                () -> log.warn("scrape requested for unknown reporter {}", reporterId));
    }

    public ScrapeRunSummary scrapeReporterSync(ScrapeReporter reporter, Integer pagesOverride, boolean force) {
        ScrapeRunSummary summary = new ScrapeRunSummary();
        progress.start("reporter " + reporter.getDisplayName() + (force ? " (force)" : ""));
        try {
            scrapeReporter(reporter, summary, pagesOverride, force);
        } finally {
            progress.finish();
        }
        return summary;
    }

    /** Scrape every enabled reporter of a single site, asynchronously. */
    @Async
    public void scrapeSiteAsync(java.util.UUID siteId, Integer pagesOverride, boolean force) {
        ScrapeRunSummary summary = scrapeSiteSync(siteId, pagesOverride, force);
        log.info("scrape site {} finished: {}", siteId, summary);
    }

    /** Scrape every enabled reporter of a single site and return the run summary. */
    public ScrapeRunSummary scrapeSiteSync(java.util.UUID siteId, Integer pagesOverride, boolean force) {
        ScrapeRunSummary summary = new ScrapeRunSummary();
        progress.start("site " + siteId + (force ? " (force)" : ""));
        try {
            for (ScrapeReporter reporter : reporterRepository.findBySiteIdAndEnabledTrue(siteId)) {
                if (progress.isCancelRequested()) {
                    break;
                }
                scrapeReporter(reporter, summary, pagesOverride, force);
            }
        } finally {
            progress.finish();
        }
        return summary;
    }

    // --- delete (scoped) -----------------------------------------------------
    // Removing scraped articles is independent of any scrape run; it just clears
    // stored rows in the chosen scope so a subsequent (non-force) run re-fetches.

    /** Delete every scraped article. Returns the number of rows removed. */
    @Transactional
    public long deleteAllArticles() {
        long count = articleRepository.count();
        articleRepository.deleteAll();
        log.info("deleted all {} scraped articles", count);
        return count;
    }

    /** Delete all scraped articles for one site. Returns the number of rows removed. */
    @Transactional
    public long deleteArticlesBySite(UUID siteId) {
        long count = articleRepository.deleteBySiteId(siteId);
        log.info("deleted {} scraped articles for site {}", count, siteId);
        return count;
    }

    /** Delete all scraped articles for one reporter. Returns the number of rows removed. */
    @Transactional
    public long deleteArticlesByReporter(UUID reporterId) {
        long count = articleRepository.deleteByReporterId(reporterId);
        log.info("deleted {} scraped articles for reporter {}", count, reporterId);
        return count;
    }

    /**
     * Resolve the per-reporter page cap: the smallest of the per-run override,
     * the site's admin ceiling, and the global default. Nulls are ignored.
     */
    private int resolvePageCap(ScrapeSite site, Integer pagesOverride) {
        int cap = maxPages;
        if (site.getMaxHistoryPages() != null && site.getMaxHistoryPages() > 0) {
            cap = Math.min(cap, site.getMaxHistoryPages());
        }
        if (pagesOverride != null && pagesOverride > 0) {
            cap = Math.min(cap, pagesOverride);
        }
        return cap;
    }

    private void scrapeReporter(ScrapeReporter reporter, ScrapeRunSummary summary,
                                Integer pagesOverride, boolean force) {
        ScrapeSite site = reporter.getSite();
        if (site == null || !site.isEnabled() || !reporter.isEnabled()) {
            return;
        }
        summary.setReportersProcessed(summary.getReportersProcessed() + 1);
        progress.beginReporter(site.getName(), reporter.getDisplayName());
        log.info("processing reporter {} ({})", reporter.getReporterKey(), site.getName());

        // Within a run, avoid re-fetching the same URL twice; across runs the
        // existsBySourceUrl check below skips anything already handled.
        Set<String> seenThisRun = new HashSet<>();
        int consecutiveFailures = 0;
        int pageCap = resolvePageCap(site, pagesOverride);

        for (int page = 1; page <= pageCap; page++) {
            if (progress.isCancelRequested()) {
                log.info("stop requested, aborting reporter {} at page {}", reporter.getReporterKey(), page);
                break;
            }

            String listingUrl = site.getBaseUrl() + format(reporter.getPathTemplate(), page);
            progress.beginListing(listingUrl, page);
            log.debug("fetching listing {}", listingUrl);

            String listingHtml;
            try {
                listingHtml = restTemplate.getForObject(listingUrl, String.class);
            } catch (Exception e) {
                log.warn("listing fetch failed, skipping {}: {}", listingUrl, e.getMessage());
                summary.setErrors(summary.getErrors() + 1);
                progress.error("listing fetch failed: " + e.getMessage());
                // Bail out of a dead/blocked reporter instead of timing out every
                // page up to max-pages.
                if (++consecutiveFailures >= maxConsecutiveFailures) {
                    log.warn("aborting reporter {} after {} consecutive listing failures",
                            reporter.getReporterKey(), consecutiveFailures);
                    progress.error("aborted: " + consecutiveFailures + " consecutive listing failures");
                    break;
                }
                continue;
            }
            consecutiveFailures = 0;
            summary.setPagesFetched(summary.getPagesFetched() + 1);
            progress.pageFetched();

            List<String> links = ruleEngine.extractArticleLinks(site, listingHtml == null ? "" : listingHtml);

            // Full-archive crawl: stop only when a listing page yields no articles.
            // (We no longer break on "all already stored" - that would abort an
            // update crawl on page 1, since every link is an existing row.)
            if (links.isEmpty()) {
                log.info("no more articles for {}, stopping at page {}", reporter.getReporterKey(), page);
                break;
            }

            for (String link : links) {
                if (progress.isCancelRequested()) {
                    break;
                }
                String articleUrl = site.getBaseUrl() + link;
                // Always skip in-run duplicates. Across runs: normally skip any
                // URL already persisted; with force, re-fetch and overwrite — but
                // ONLY this run's scope, since persist() upserts scoped to
                // (site, reporter, url), never touching other reporters' rows.
                boolean inRunDup = !seenThisRun.add(articleUrl);
                if (inRunDup || (!force && articleRepository.existsBySourceUrl(articleUrl))) {
                    summary.setArticlesSkipped(summary.getArticlesSkipped() + 1);
                    progress.skipped();
                    continue;
                }

                try {
                    progress.beginArticle(articleUrl);
                    String articleHtml = restTemplate.getForObject(articleUrl, String.class);
                    if (articleHtml == null) {
                        continue;
                    }
                    String title = ruleEngine.extractTitle(site, articleHtml);
                    if (title == null) {
                        log.debug("skipping {} - no title", articleUrl);
                        continue;
                    }
                    persist(site, reporter, articleUrl, title, articleHtml, summary, force);
                } catch (Exception e) {
                    log.warn("article fetch/parse failed {}: {}", articleUrl, e.getMessage());
                    summary.setErrors(summary.getErrors() + 1);
                    progress.error("article failed: " + e.getMessage());
                }
            }
        }
    }

    private void persist(ScrapeSite site, ScrapeReporter reporter, String articleUrl,
                         String title, String articleHtml, ScrapeRunSummary summary, boolean force) {
        String subtitle = ruleEngine.extractSubtitle(site, articleHtml);
        String content = ruleEngine.extractContent(site, articleHtml);
        String reporterName = ruleEngine.extractReporter(site, articleHtml);
        String date = ruleEngine.extractDate(site, articleHtml);

        // Normally insert-only (callers skip existing URLs). With force, overwrite
        // the existing row IN SCOPE — matched by (site, reporter, url) — so a
        // forced run only ever touches its own scope's rows, keeping id/created_at.
        ScrapedArticle article = null;
        boolean updated = false;
        if (force) {
            article = articleRepository
                    .findBySiteIdAndReporterIdAndSourceUrl(site.getId(), reporter.getId(), articleUrl)
                    .orElse(null);
            updated = article != null;
        }
        if (article == null) {
            article = new ScrapedArticle();
            article.setSite(site);
            article.setReporter(reporter);
            article.setSourceUrl(articleUrl);
        }
        article.setTitle(title);
        article.setSubTitle(subtitle);
        article.setContent(content);
        article.setReporterName(reporterName);
        article.setPublishedDate(date);
        article.setScrapedAt(new Date());
        articleRepository.save(article);

        if (updated) {
            summary.setArticlesUpdated(summary.getArticlesUpdated() + 1);
            progress.updated();
        } else {
            summary.setArticlesSaved(summary.getArticlesSaved() + 1);
            progress.saved();
        }
    }

    /** Replace the single '{}' placeholder with the page number (Python str.format equivalent). */
    private String format(String template, int page) {
        return template.replace("{}", Integer.toString(page));
    }
}
