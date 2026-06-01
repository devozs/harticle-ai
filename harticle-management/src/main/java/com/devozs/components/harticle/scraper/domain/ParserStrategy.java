package com.devozs.components.harticle.scraper.domain;

/**
 * Selects how a site's pages are parsed.
 *
 * <p>ONE, WALLA and SPORT5 reproduce the per-site branches of the legacy
 * fetch_data.py exactly (link/title/subtitle/content/date/reporter extraction).
 * GENERIC_REGEX drives extraction purely from the rule columns on the site,
 * so new sites can be added through the API without code changes.
 */
public enum ParserStrategy {
    ONE,
    WALLA,
    SPORT5,
    GENERIC_REGEX
}
