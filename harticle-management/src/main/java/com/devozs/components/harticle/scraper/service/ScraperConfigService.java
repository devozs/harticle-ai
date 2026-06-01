package com.devozs.components.harticle.scraper.service;

import com.devozs.components.harticle.scraper.domain.ParserStrategy;
import com.devozs.components.harticle.scraper.domain.RuleType;
import com.devozs.components.harticle.scraper.dto.ScrapeReporterDto;
import com.devozs.components.harticle.scraper.dto.ScrapeSiteDto;
import com.devozs.components.harticle.scraper.entity.ScrapeReporter;
import com.devozs.components.harticle.scraper.entity.ScrapeSite;
import com.devozs.components.harticle.scraper.repository.ScrapeReporterRepository;
import com.devozs.components.harticle.scraper.repository.ScrapeSiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/** CRUD for scrape sites and reporters - the configurable inputs to {@link ScraperService}. */
@Service
@Slf4j
public class ScraperConfigService {

    private final ScrapeSiteRepository siteRepository;
    private final ScrapeReporterRepository reporterRepository;

    public ScraperConfigService(ScrapeSiteRepository siteRepository,
                                ScrapeReporterRepository reporterRepository) {
        this.siteRepository = siteRepository;
        this.reporterRepository = reporterRepository;
    }

    // --- sites ---------------------------------------------------------------

    public List<ScrapeSite> getAllSites() {
        return StreamSupport.stream(siteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public ScrapeSite getSite(UUID id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("scrape site not found: " + id));
    }

    public ScrapeSite createSite(ScrapeSiteDto dto) {
        ScrapeSite site = new ScrapeSite();
        applySite(site, dto);
        if (site.getParserStrategy() == null) {
            site.setParserStrategy(ParserStrategy.GENERIC_REGEX);
        }
        if (site.getRuleType() == null) {
            site.setRuleType(RuleType.REGEX);
        }
        return siteRepository.save(site);
    }

    public ScrapeSite updateSite(UUID id, ScrapeSiteDto dto) {
        ScrapeSite site = getSite(id);
        applySite(site, dto);
        return siteRepository.save(site);
    }

    public void deleteSite(UUID id) {
        siteRepository.deleteById(id);
    }

    private void applySite(ScrapeSite site, ScrapeSiteDto dto) {
        if (dto.getName() != null) site.setName(dto.getName());
        if (dto.getBaseUrl() != null) site.setBaseUrl(dto.getBaseUrl());
        if (dto.getParserStrategy() != null) site.setParserStrategy(dto.getParserStrategy());
        if (dto.getRuleType() != null) site.setRuleType(dto.getRuleType());
        if (dto.getArticleLinkRule() != null) site.setArticleLinkRule(dto.getArticleLinkRule());
        if (dto.getArticleLinkFilter() != null) site.setArticleLinkFilter(dto.getArticleLinkFilter());
        if (dto.getTitleRule() != null) site.setTitleRule(dto.getTitleRule());
        if (dto.getSubtitleRule() != null) site.setSubtitleRule(dto.getSubtitleRule());
        if (dto.getContentRule() != null) site.setContentRule(dto.getContentRule());
        if (dto.getContentSource() != null) site.setContentSource(dto.getContentSource());
        if (dto.getDateRule() != null) site.setDateRule(dto.getDateRule());
        if (dto.getReporterRule() != null) site.setReporterRule(dto.getReporterRule());
        if (dto.getListingStopMarker() != null) site.setListingStopMarker(dto.getListingStopMarker());
        if (dto.getMaxHistoryPages() != null) site.setMaxHistoryPages(dto.getMaxHistoryPages());
        if (dto.getEnabled() != null) site.setEnabled(dto.getEnabled());
    }

    // --- reporters -----------------------------------------------------------

    public List<ScrapeReporter> getReporters(UUID siteId) {
        return siteId == null
                ? StreamSupport.stream(reporterRepository.findAll().spliterator(), false).collect(Collectors.toList())
                : reporterRepository.findBySiteId(siteId);
    }

    public ScrapeReporter getReporter(UUID id) {
        return reporterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("scrape reporter not found: " + id));
    }

    public ScrapeReporter createReporter(ScrapeReporterDto dto) {
        ScrapeSite site = getSite(dto.getSiteId());
        ScrapeReporter reporter = ScrapeReporter.builder()
                .site(site)
                .reporterKey(dto.getReporterKey())
                .displayName(dto.getDisplayName())
                .pathTemplate(dto.getPathTemplate())
                .enabled(dto.getEnabled() == null || dto.getEnabled())
                .build();
        return reporterRepository.save(reporter);
    }

    /**
     * Insert many reporters for a site in one call. {@code siteId} comes from the
     * path; each DTO's own siteId is ignored so the whole batch lands on one site.
     */
    public List<ScrapeReporter> bulkCreateReporters(UUID siteId, List<ScrapeReporterDto> dtos) {
        ScrapeSite site = getSite(siteId);
        List<ScrapeReporter> toSave = dtos.stream()
                .map(dto -> ScrapeReporter.builder()
                        .site(site)
                        .reporterKey(dto.getReporterKey())
                        .displayName(dto.getDisplayName())
                        .pathTemplate(dto.getPathTemplate())
                        .enabled(dto.getEnabled() == null || dto.getEnabled())
                        .build())
                .collect(Collectors.toList());
        return StreamSupport.stream(reporterRepository.saveAll(toSave).spliterator(), false)
                .collect(Collectors.toList());
    }

    public ScrapeReporter updateReporter(UUID id, ScrapeReporterDto dto) {
        ScrapeReporter reporter = getReporter(id);
        if (dto.getSiteId() != null) reporter.setSite(getSite(dto.getSiteId()));
        if (dto.getReporterKey() != null) reporter.setReporterKey(dto.getReporterKey());
        if (dto.getDisplayName() != null) reporter.setDisplayName(dto.getDisplayName());
        if (dto.getPathTemplate() != null) reporter.setPathTemplate(dto.getPathTemplate());
        if (dto.getEnabled() != null) reporter.setEnabled(dto.getEnabled());
        return reporterRepository.save(reporter);
    }

    public void deleteReporter(UUID id) {
        reporterRepository.deleteById(id);
    }
}
