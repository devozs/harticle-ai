package com.devozs.components.harticle.scraper.service;

import com.devozs.components.harticle.scraper.domain.ContentSource;
import com.devozs.components.harticle.scraper.domain.ParserStrategy;
import com.devozs.components.harticle.scraper.entity.ScrapeSite;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Applies a {@link ScrapeSite}'s extraction rules to fetched HTML.
 *
 * <p>This is a faithful Java port of the {@code extract_*} and
 * {@code clear_characters} functions from the legacy Python
 * {@code harticle-engine/harticle/fetch_data.py}. Title/subtitle/content/date/
 * reporter extraction is driven uniformly by the regex rule columns on the
 * site. Article-link extraction keeps the per-site quirks (the nested Walla
 * lookup, the Sport5 href clean-up) under {@link ParserStrategy}; new sites use
 * {@link ParserStrategy#GENERIC_REGEX}, driven only by the rule columns.
 */
@Component
public class ExtractionRuleEngine {

    /** Extract article hrefs (relative to the site base) from a reporter listing page. */
    public List<String> extractArticleLinks(ScrapeSite site, String listingHtml) {
        List<String> links = new ArrayList<>();
        ParserStrategy strategy = site.getParserStrategy();

        // Cut off trailing "recommended"/related sections (e.g. sport5's מומלצים
        // block after class="paging") so only the reporter's own links are taken.
        listingHtml = truncateAtStopMarker(site, listingHtml);

        if (strategy == ParserStrategy.ONE) {
            // one.co.il's mobile listing emits absolute hrefs; strip the base so
            // the engine's baseUrl + link reconstructs correctly (as SPORT5/WALLA).
            for (String link : findAll(site.getArticleLinkRule(), listingHtml)) {
                if (!link.contains("MoreArticles")) {
                    links.add(link.replace(site.getBaseUrl(), ""));
                }
            }
            return links;
        }

        if (strategy == ParserStrategy.WALLA) {
            // Narrow to the reporter's own feed ("כל הכתבות של …") so cross-promo
            // links from other Walla domains and nav lists are excluded.
            List<String> blocks =
                    findAll("(?s)(?<=<section class=\"writer-articles\">)(.*?)(?=</section>)", listingHtml);
            if (!blocks.isEmpty()) {
                for (String link : findAll(site.getArticleLinkRule(), blocks.get(0))) {
                    if (link.contains("item") && link.contains("sports")) {
                        links.add(link.replace(site.getBaseUrl(), ""));
                    }
                }
            }
            return links;
        }

        if (strategy == ParserStrategy.SPORT5) {
            for (String link : findAll(site.getArticleLinkRule(), listingHtml)) {
                if (link.contains("articles")) {
                    links.add(link.replace(site.getBaseUrl(), "")
                            .replace("\" target=\"_self", "")
                            .replace("\" target=\"_blank", ""));
                }
            }
            return links;
        }

        // GENERIC_REGEX: capture by rule, keep by optional filter, strip the base url.
        String filter = site.getArticleLinkFilter();
        for (String link : findAll(site.getArticleLinkRule(), listingHtml)) {
            if (filter == null || filter.isBlank() || link.contains(filter)) {
                links.add(link.replace(site.getBaseUrl(), ""));
            }
        }
        return links;
    }

    /** @return cleaned title, or {@code null} when the rule matches nothing (article is skipped). */
    public String extractTitle(ScrapeSite site, String html) {
        String title = findFirst(site.getTitleRule(), html);
        if (title == null) {
            return null;
        }
        return clearCharacters(title, true);
    }

    public String extractSubtitle(ScrapeSite site, String html) {
        String subtitle = findFirst(site.getSubtitleRule(), html);
        return clearCharacters(subtitle == null ? "" : subtitle, true);
    }

    public String extractContent(ScrapeSite site, String html) {
        if (site.getContentSource() == ContentSource.JSON_LD) {
            return extractJsonLdArticleBody(html);
        }
        StringBuilder content = new StringBuilder();
        for (String paragraph : findAll(site.getContentRule(), html)) {
            if (paragraph != null && paragraph.length() > 1) {
                content.append(paragraph);
            }
        }
        return clearCharacters(content.toString(), false);
    }

    /**
     * Pull the body from a JSON-LD {@code "articleBody":"..."} field and
     * JSON-unescape it. Used by sites (sport5) whose visible markup no longer
     * exposes paragraphs as simple tags.
     */
    private String extractJsonLdArticleBody(String html) {
        if (html == null) {
            return "";
        }
        // Locate the key, then scan the JSON string value forward char-by-char.
        // (A regex like "((?:\\.|[^"\\])*)" recurses per character and blows the
        // stack on long bodies — StackOverflowError. Manual scan is O(n), safe.)
        Matcher key = Pattern.compile("\"articleBody\"\\s*:\\s*\"").matcher(html);
        if (!key.find()) {
            return "";
        }
        int i = key.end();
        StringBuilder raw = new StringBuilder();
        while (i < html.length()) {
            char c = html.charAt(i);
            if (c == '\\' && i + 1 < html.length()) {
                raw.append(c).append(html.charAt(i + 1)); // keep escape pair intact
                i += 2;
            } else if (c == '"') {
                break; // unescaped closing quote ends the value
            } else {
                raw.append(c);
                i++;
            }
        }
        return unescapeJson(raw.toString()).strip();
    }

    /** Minimal JSON string unescape: \" \\ \/ \n \r \t \b \f and \\uXXXX. */
    private String unescapeJson(String s) {
        StringBuilder out = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '\\' || i + 1 >= s.length()) {
                out.append(c);
                continue;
            }
            char next = s.charAt(++i);
            switch (next) {
                case 'n' -> out.append('\n');
                case 'r' -> out.append('\r');
                case 't' -> out.append('\t');
                case 'b' -> out.append('\b');
                case 'f' -> out.append('\f');
                case '"' -> out.append('"');
                case '\\' -> out.append('\\');
                case '/' -> out.append('/');
                case 'u' -> {
                    if (i + 4 < s.length()) {
                        try {
                            out.append((char) Integer.parseInt(s.substring(i + 1, i + 5), 16));
                            i += 4;
                        } catch (NumberFormatException e) {
                            out.append(next);
                        }
                    } else {
                        out.append(next);
                    }
                }
                default -> out.append(next);
            }
        }
        return out.toString();
    }

    /** Truncate listing HTML at the site's stop marker (first occurrence), if set and present. */
    private String truncateAtStopMarker(ScrapeSite site, String listingHtml) {
        String marker = site.getListingStopMarker();
        if (listingHtml == null || marker == null || marker.isBlank()) {
            return listingHtml;
        }
        int idx = listingHtml.indexOf(marker);
        return idx >= 0 ? listingHtml.substring(0, idx) : listingHtml;
    }

    public String extractDate(ScrapeSite site, String html) {
        String date = findFirst(site.getDateRule(), html);
        if (date == null) {
            return "";
        }
        // Legacy: keep only the first space-delimited token, drop stray quotes.
        return date.split(" ")[0].replace("\"", "");
    }

    public String extractReporter(ScrapeSite site, String html) {
        String name = findFirst(site.getReporterRule(), html);
        if (name == null) {
            return "";
        }
        return clearCharacters(name.replace("\"", ""), true);
    }

    /** Port of clear_characters: decode the handful of entities the legacy scraper handled. */
    public String clearCharacters(String text, boolean removeNewLine) {
        if (text == null) {
            return "";
        }
        text = text.replace("&ldquo;", "\"").replace("&rdquo;", "\"").replace("&ndash;", "-")
                .replace("&quot;", "\"").replace(";", "").replace("</p>", "")
                .replace("<STRONG>", "").replace("</STRONG>", "").replace("&nbsp", " ")
                .replace("&#x27", "").replace("&#39", "'").replace("&rsquo", "'");
        if (removeNewLine) {
            text = text.replace("\n", "").replace("\r", "").strip();
        }
        return text;
    }

    // --- regex helpers mirroring Python re.findall semantics -----------------

    private String findFirst(String pattern, String text) {
        if (pattern == null || pattern.isBlank() || text == null) {
            return null;
        }
        Matcher m = Pattern.compile(pattern).matcher(text);
        if (m.find()) {
            return m.groupCount() >= 1 ? m.group(1) : m.group();
        }
        return null;
    }

    private List<String> findAll(String pattern, String text) {
        List<String> out = new ArrayList<>();
        if (pattern == null || pattern.isBlank() || text == null) {
            return out;
        }
        Matcher m = Pattern.compile(pattern).matcher(text);
        while (m.find()) {
            out.add(m.groupCount() >= 1 ? m.group(1) : m.group());
        }
        return out;
    }
}
