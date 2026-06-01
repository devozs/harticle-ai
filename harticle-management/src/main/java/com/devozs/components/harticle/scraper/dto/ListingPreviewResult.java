package com.devozs.components.harticle.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/** Result of a dry-run listing-page extraction: how many article links the link rule found. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListingPreviewResult {
    private String url;
    private String siteName;
    private boolean success;
    private String verdict;
    private int htmlLength;
    private boolean fetchOk;
    private int linkCount;
    private String linkRule;
    /** First handful of resolved article URLs, for eyeballing. */
    private List<String> sampleLinks;
}
