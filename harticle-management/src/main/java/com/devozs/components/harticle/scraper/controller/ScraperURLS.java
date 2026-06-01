package com.devozs.components.harticle.scraper.controller;

public final class ScraperURLS {
    private ScraperURLS() {}

    public static final String URL = "scraper";

    public static final String SITES = "/sites";
    public static final String REPORTERS = "/reporters";
    public static final String ARTICLES = "/articles";
    public static final String RUN = "/run";
    public static final String RUN_SYNC = "/run-sync";
    public static final String RUN_STATUS = "/run/status";
    public static final String RUN_STOP = "/run/stop";
    public static final String PREVIEW_ARTICLE = "/preview/article";
    public static final String PREVIEW_LISTING = "/preview/listing";

    public static final String BULK = "/bulk";
    public static final String SITE = "/site";

    public static final String ID = "/{id}";
    public static final String REPORTER_ID = "/{reporterId}";
    public static final String SITE_ID = "/{siteId}";

    /** Scoped article deletion: /articles (all), /articles/site/{siteId}, /articles/reporter/{reporterId}. */
    public static final String REPORTER = "/reporter";

    public static final String URL_PATTERN = "/" + URL + "/**";
}
