package com.devozs.components.harticle.scraper.domain;

/**
 * How a site's extraction rules are interpreted. Today only REGEX is ported
 * from the legacy Python scraper; CSS (jsoup selectors) is reserved for later.
 */
public enum RuleType {
    REGEX,
    CSS
}
