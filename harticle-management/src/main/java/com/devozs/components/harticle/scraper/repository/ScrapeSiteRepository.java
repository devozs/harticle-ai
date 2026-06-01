package com.devozs.components.harticle.scraper.repository;

import com.devozs.components.harticle.scraper.entity.ScrapeSite;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScrapeSiteRepository extends CrudRepository<ScrapeSite, UUID> {
    Optional<ScrapeSite> findByName(String name);
}
