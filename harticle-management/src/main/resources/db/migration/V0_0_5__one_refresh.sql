-- one.co.il refresh: its markup and listing format changed. The reporter
-- listing is now the mobile site (sites.one.co.il), articles are absolute
-- /Mobile/Article/… hrefs, and the canonical www.one.co.il/Article/{id}.html
-- page is client-rendered (no server HTML to scrape). We therefore point one at
-- sites.one.co.il and scrape the server-rendered mobile article pages, reading
-- title/subtitle/date/reporter from their JSON-LD and the body from <p> tags.
-- Verified against live pages — see the harticle-scraping plan for per-field checks.

-- ---------------------------------------------------------------------------
-- (a) Repoint the one site at sites.one.co.il and refresh its rules. The link
-- rule captures the absolute mobile article hrefs (the ONE listing branch strips
-- the base url so baseUrl + link reconstructs correctly).
-- ---------------------------------------------------------------------------
update scrape_site set
    base_url           = 'https://sites.one.co.il',
    article_link_rule  = '(?<=href=")(https://sites\.one\.co\.il/Mobile/Article/[^"]+\.html)',
    article_link_filter = 'Article',
    title_rule    = '"headline":\s*"((?:\\.|[^"\\])*)"',
    subtitle_rule = '"description":\s*"((?:\\.|[^"\\])*)"',
    content_source = 'REGEX',
    content_rule  = '(?s)<p>(.+?)</p>',
    date_rule     = '"datePublished":\s*"([^"]+)"',
    reporter_rule = '"author":\s*\{[^}]*?"name":\s*"([^"]+)"'
where name = 'one';

-- ---------------------------------------------------------------------------
-- (b) Repoint the one reporters' path templates at the new mobile listing
-- (relative to the new sites.one.co.il base). The author param is the display
-- name with spaces as '+'; the literal Hebrew form fetches fine.
-- ---------------------------------------------------------------------------
update scrape_reporter
set path_template = '/mobile/articles/morearticles.aspx?author=' || replace(display_name, ' ', '+') || '&page={}'
where site_id = '11111111-0000-0000-0000-000000000003';
