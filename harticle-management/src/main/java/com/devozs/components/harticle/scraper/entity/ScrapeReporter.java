package com.devozs.components.harticle.scraper.entity;

import com.devozs.components.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * A reporter (author) on a {@link ScrapeSite}. The {@code pathTemplate} carries
 * a single {@code {}} placeholder for the page number, e.g.
 * {@code /Author/מוטי_פשכצקי?Page={}}.
 */
@Entity
@Table(name = "scrape_reporter")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ScrapeReporter extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "site_id", nullable = false)
    private ScrapeSite site;

    @Column(name = "reporter_key")
    private String reporterKey;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "path_template")
    private String pathTemplate;

    private boolean enabled = true;
}
