package com.devozs.components.harticle.scraper.repository;

import com.devozs.components.harticle.scraper.entity.ScrapedArticle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScrapedArticleRepository extends CrudRepository<ScrapedArticle, UUID> {
    /** Global de-dup: an article URL is scraped at most once, regardless of reporter/section. */
    boolean existsBySourceUrl(String sourceUrl);
    /** Scoped lookup for force re-scrape: overwrite this exact pair's row in place. */
    Optional<ScrapedArticle> findBySiteIdAndReporterIdAndSourceUrl(UUID siteId, UUID reporterId, String sourceUrl);
    List<ScrapedArticle> findByReporterId(UUID reporterId);
}
