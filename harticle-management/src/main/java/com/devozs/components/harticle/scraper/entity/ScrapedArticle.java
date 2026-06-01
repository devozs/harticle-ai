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

import java.util.Date;

/**
 * One scraped article, structured for later LLM/fine-tune use. The
 * {@code prompt}/{@code completion} pair mirrors the legacy CSV columns
 * (title-or-subtitle as prompt, body as completion).
 */
@Entity
@Table(name = "scraped_article")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ScrapedArticle extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false)
    private ScrapeReporter reporter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "site_id", nullable = false)
    private ScrapeSite site;

    @Column(name = "source_url", length = 1024)
    private String sourceUrl;

    @Column(length = 512)
    private String title;

    @Column(name = "sub_title", length = 1024)
    private String subTitle;

    @Column(columnDefinition = "text")
    private String content;

    @Column(name = "published_date")
    private String publishedDate;

    @Column(name = "reporter_name")
    private String reporterName;

    @Column(columnDefinition = "text")
    private String prompt;

    @Column(columnDefinition = "text")
    private String completion;

    @Column(name = "scraped_at")
    private Date scrapedAt;
}
