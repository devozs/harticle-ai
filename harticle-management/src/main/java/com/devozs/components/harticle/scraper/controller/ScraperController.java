package com.devozs.components.harticle.scraper.controller;

import com.devozs.components.harticle.scraper.dto.ArticlePreviewResult;
import com.devozs.components.harticle.scraper.dto.ListingPreviewResult;
import com.devozs.components.harticle.scraper.dto.PreviewRequest;
import com.devozs.components.harticle.scraper.dto.ScrapeProgress;
import com.devozs.components.harticle.scraper.dto.ScrapeReporterDto;
import com.devozs.components.harticle.scraper.dto.ScrapeRunSummary;
import com.devozs.components.harticle.scraper.dto.ScrapeSiteDto;
import com.devozs.components.harticle.scraper.entity.ScrapeReporter;
import com.devozs.components.harticle.scraper.entity.ScrapeSite;
import com.devozs.components.harticle.scraper.entity.ScrapedArticle;
import com.devozs.components.harticle.scraper.repository.ScrapedArticleRepository;
import com.devozs.components.harticle.scraper.service.ScrapePreviewService;
import com.devozs.components.harticle.scraper.service.ScrapeProgressTracker;
import com.devozs.components.harticle.scraper.service.ScraperConfigService;
import com.devozs.components.harticle.scraper.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST surface for reporter-article extraction. Configure sites/reporters,
 * trigger scrape runs (async), and read back the scraped articles. This is the
 * API the frontend calls; it replaces the standalone Python fetch_data.py.
 */
@RestController
@RequestMapping(ScraperURLS.URL)
@Slf4j
public class ScraperController {

    private final ScraperConfigService configService;
    private final ScraperService scraperService;
    private final ScrapePreviewService previewService;
    private final ScrapeProgressTracker progressTracker;
    private final ScrapedArticleRepository articleRepository;

    public ScraperController(ScraperConfigService configService,
                             ScraperService scraperService,
                             ScrapePreviewService previewService,
                             ScrapeProgressTracker progressTracker,
                             ScrapedArticleRepository articleRepository) {
        this.configService = configService;
        this.scraperService = scraperService;
        this.previewService = previewService;
        this.progressTracker = progressTracker;
        this.articleRepository = articleRepository;
    }

    // --- sites ---------------------------------------------------------------

    @GetMapping(ScraperURLS.SITES)
    @ResponseBody
    public List<ScrapeSite> getSites() {
        return configService.getAllSites();
    }

    @GetMapping(ScraperURLS.SITES + ScraperURLS.ID)
    @ResponseBody
    public ScrapeSite getSite(@PathVariable UUID id) {
        return configService.getSite(id);
    }

    @PostMapping(ScraperURLS.SITES)
    @ResponseBody
    public ScrapeSite createSite(@RequestBody ScrapeSiteDto dto) {
        return configService.createSite(dto);
    }

    @PutMapping(ScraperURLS.SITES + ScraperURLS.ID)
    @ResponseBody
    public ScrapeSite updateSite(@PathVariable UUID id, @RequestBody ScrapeSiteDto dto) {
        return configService.updateSite(id, dto);
    }

    @DeleteMapping(ScraperURLS.SITES + ScraperURLS.ID)
    public ResponseEntity<Void> deleteSite(@PathVariable UUID id) {
        configService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }

    // --- reporters -----------------------------------------------------------

    @GetMapping(ScraperURLS.REPORTERS)
    @ResponseBody
    public List<ScrapeReporter> getReporters(@RequestParam(required = false) UUID siteId) {
        return configService.getReporters(siteId);
    }

    @PostMapping(ScraperURLS.REPORTERS)
    @ResponseBody
    public ScrapeReporter createReporter(@RequestBody ScrapeReporterDto dto) {
        return configService.createReporter(dto);
    }

    /** Bulk-load a list of reporters onto one site in a single call. */
    @PostMapping(ScraperURLS.REPORTERS + ScraperURLS.BULK + ScraperURLS.SITE_ID)
    @ResponseBody
    public List<ScrapeReporter> bulkCreateReporters(@PathVariable UUID siteId,
                                                    @RequestBody List<ScrapeReporterDto> reporters) {
        return configService.bulkCreateReporters(siteId, reporters);
    }

    @PutMapping(ScraperURLS.REPORTERS + ScraperURLS.ID)
    @ResponseBody
    public ScrapeReporter updateReporter(@PathVariable UUID id, @RequestBody ScrapeReporterDto dto) {
        return configService.updateReporter(id, dto);
    }

    @DeleteMapping(ScraperURLS.REPORTERS + ScraperURLS.ID)
    public ResponseEntity<Void> deleteReporter(@PathVariable UUID id) {
        configService.deleteReporter(id);
        return ResponseEntity.noContent().build();
    }

    // --- run + read ----------------------------------------------------------

    /**
     * Trigger a scrape of all enabled reporters. Runs asynchronously; returns 202.
     * Optional {@code pages} caps listing pages per reporter (omit = site/global max).
     */
    @PostMapping(ScraperURLS.RUN)
    public ResponseEntity<String> runAll(@RequestParam(required = false) Integer pages,
                                         @RequestParam(required = false, defaultValue = "false") boolean force) {
        scraperService.scrapeAllEnabledAsync(pages, force);
        return ResponseEntity.accepted().body("scrape started for all enabled reporters");
    }

    /** Trigger a scrape of a single reporter. Runs asynchronously; returns 202. */
    @PostMapping(ScraperURLS.RUN + ScraperURLS.REPORTER_ID)
    public ResponseEntity<String> runReporter(@PathVariable UUID reporterId,
                                              @RequestParam(required = false) Integer pages,
                                              @RequestParam(required = false, defaultValue = "false") boolean force) {
        scraperService.scrapeReporterAsync(reporterId, pages, force);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("scrape started for reporter " + reporterId);
    }

    /**
     * Scrape a single reporter synchronously and return the run summary. For
     * testing: blocks until done so you immediately see saved/updated/skipped
     * counts. {@code pages} caps listing pages (omit = site/global max).
     * {@code force} re-scrapes already-handled articles in this scope.
     */
    @PostMapping(ScraperURLS.RUN_SYNC + ScraperURLS.REPORTER_ID)
    @ResponseBody
    public ScrapeRunSummary runReporterSync(@PathVariable UUID reporterId,
                                            @RequestParam(required = false) Integer pages,
                                            @RequestParam(required = false, defaultValue = "false") boolean force) {
        ScrapeReporter reporter = configService.getReporter(reporterId);
        return scraperService.scrapeReporterSync(reporter, pages, force);
    }

    /** Trigger a scrape of every enabled reporter on one site. Async; returns 202. */
    @PostMapping(ScraperURLS.RUN + ScraperURLS.SITE + ScraperURLS.SITE_ID)
    public ResponseEntity<String> runSite(@PathVariable UUID siteId,
                                          @RequestParam(required = false) Integer pages,
                                          @RequestParam(required = false, defaultValue = "false") boolean force) {
        scraperService.scrapeSiteAsync(siteId, pages, force);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("scrape started for site " + siteId);
    }

    /** Scrape every enabled reporter on one site synchronously and return the summary. */
    @PostMapping(ScraperURLS.RUN_SYNC + ScraperURLS.SITE + ScraperURLS.SITE_ID)
    @ResponseBody
    public ScrapeRunSummary runSiteSync(@PathVariable UUID siteId,
                                        @RequestParam(required = false) Integer pages,
                                        @RequestParam(required = false, defaultValue = "false") boolean force) {
        return scraperService.scrapeSiteSync(siteId, pages, force);
    }

    /**
     * Live status of the current/last scrape run. The FE polls this so the
     * user can see real progress (pages/articles ticking up) and detect a
     * stalled backend via {@code secondsSinceActivity}.
     */
    @GetMapping(ScraperURLS.RUN_STATUS)
    @ResponseBody
    public ScrapeProgress runStatus() {
        return progressTracker.snapshot();
    }

    /**
     * Request the in-flight run to stop. Cooperative: the running thread checks
     * the flag at reporter/page/article boundaries and bails out promptly.
     * Applies to any run type (all reporters / by site / by reporter), since
     * there is a single active run. Returns the post-request status snapshot.
     */
    @PostMapping(ScraperURLS.RUN_STOP)
    @ResponseBody
    public ScrapeProgress stopRun() {
        boolean requested = progressTracker.requestCancel();
        log.info("stop run requested via API (active run: {})", requested);
        return progressTracker.snapshot();
    }

    // --- preview / dry-run (no persistence) ----------------------------------

    /**
     * Dry-run extraction on one article URL: fetch, apply rules, report each
     * field + an overall verdict. No DB writes. Supply inline rule overrides in
     * the body to iterate on regex without saving the site.
     */
    @PostMapping(ScraperURLS.PREVIEW_ARTICLE)
    @ResponseBody
    public ArticlePreviewResult previewArticle(@RequestBody PreviewRequest request) {
        return previewService.previewArticle(request);
    }

    /** Dry-run extraction on one listing/author URL: report how many article links the rule finds. */
    @PostMapping(ScraperURLS.PREVIEW_LISTING)
    @ResponseBody
    public ListingPreviewResult previewListing(@RequestBody PreviewRequest request) {
        return previewService.previewListing(request);
    }

    @GetMapping(ScraperURLS.ARTICLES)
    @ResponseBody
    public List<ScrapedArticle> getArticles(@RequestParam(required = false) UUID reporterId) {
        if (reporterId != null) {
            return articleRepository.findByReporterId(reporterId);
        }
        return StreamSupport.stream(articleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
