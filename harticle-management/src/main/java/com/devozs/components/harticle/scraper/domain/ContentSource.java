package com.devozs.components.harticle.scraper.domain;

/**
 * Where to read an article's body from.
 *
 * <p>REGEX applies the site's {@code contentRule} to the raw HTML (the legacy
 * behaviour). JSON_LD extracts the {@code "articleBody"} field from the page's
 * JSON-LD block and unescapes it — used by sites (e.g. sport5) whose visible
 * markup no longer exposes the body in simple paragraph tags.
 */
public enum ContentSource {
    REGEX,
    JSON_LD
}
