package com.devozs.components.harticle.scraper.repository;

import com.devozs.components.harticle.scraper.entity.ScrapeReporter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ScrapeReporterRepository extends CrudRepository<ScrapeReporter, UUID> {
    List<ScrapeReporter> findBySiteId(UUID siteId);
    List<ScrapeReporter> findByEnabledTrue();
    List<ScrapeReporter> findBySiteIdAndEnabledTrue(UUID siteId);
}
