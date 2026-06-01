package com.devozs.components.harticle.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Result of a dry-run article extraction. {@code success} is the clear verdict:
 * an article is considered successfully scraped when title and content both
 * matched (the same fields the real scraper requires to persist a row).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePreviewResult {
    private String url;
    private String siteName;
    private boolean success;
    private String verdict;
    private int htmlLength;
    private boolean fetchOk;
    private List<FieldResult> fields;
}
