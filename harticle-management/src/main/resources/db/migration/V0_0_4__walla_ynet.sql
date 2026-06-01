-- Walla + Ynet: bring walla's rules in line with its current markup, and add
-- ynet as a new site. Verified against live pages (walla writer/item, ynet
-- topic/article) — see the harticle-scraping plan for the per-field checks.

-- ---------------------------------------------------------------------------
-- (a) Walla: its markup changed. The reporter's own articles now live inside
-- <section class="writer-articles"> (handled by the WALLA listing branch), the
-- title/subtitle classes carry three spaces, the body is in
-- <p class="article_speakable"> tags (REGEX, not JSON-LD), and the date/author
-- moved into the new writer header.
-- ---------------------------------------------------------------------------
update scrape_site set
    article_link_rule  = '(https[^"]+)',
    article_link_filter = 'item',
    title_rule    = '(?s)(?<=<h1 class="title   article_speakable">)(.+?)(?=</h1>)',
    subtitle_rule = '(?s)(?<=<h2 class="subtitle   article_speakable">)(.+?)(?=</h2>)',
    content_source = 'REGEX',
    content_rule  = '<p class="article_speakable">(.*?)</p>',
    date_rule     = '(?s)<p dir="ltr" class="date[^"]*"[^>]*>(.+?)</p>',
    reporter_rule = '(?s)<div class="writer-name-item"><p>(.+?)</p>'
where name = 'walla';

-- ---------------------------------------------------------------------------
-- (b) Ynet: GENERIC_REGEX listing + JSON_LD article body. The listing link rule
-- captures only the reporter's feed cards (lookahead on the trailing
-- ><div class="i…), excluding the target="_top" nav links. Title/subtitle/date/
-- reporter are pulled from the article's JSON-LD block.
-- ---------------------------------------------------------------------------
insert into scrape_site (id, name, base_url, parser_strategy, rule_type,
                         article_link_rule, article_link_filter,
                         title_rule, subtitle_rule, content_source, content_rule,
                         date_rule, reporter_rule, enabled)
values
 ('11111111-0000-0000-0000-000000000004', 'ynet', 'https://www.ynet.co.il', 'GENERIC_REGEX', 'REGEX',
  'href="(https://www\.ynet\.co\.il/[^"]*article/[A-Za-z0-9]+)"(?=><div class="i)', 'article',
  '"headline"\s*:\s*"((?:\\.|[^"\\])*)"',
  '"description"\s*:\s*"((?:\\.|[^"\\])*)"',
  'JSON_LD',
  '"articleBody":"((?:\\.|[^"\\])*)"',
  '"datePublished"\s*:\s*"([^"]+)"',
  '"author"\s*:\s*(?:\[\s*)?\{[^}]*?"name"\s*:\s*"([^"]+)"', true);

-- ---------------------------------------------------------------------------
-- (c) Ynet reporters. Path template /topics/{reporter_id}/{page}. The id segment
-- can be the literal Hebrew or its URL-encoded form (both fetch the same page).
-- Add more reporters as additional rows with incrementing ...9999-...NNN ids.
-- ---------------------------------------------------------------------------
insert into scrape_reporter (id, site_id, reporter_key, display_name, path_template, enabled)
values
 ('22222222-0000-0000-9999-000000000001', '11111111-0000-0000-0000-000000000004', 'nadav_tzantziper', 'נדב צנציפר',
  '/topics/%D7%A0%D7%93%D7%91_%D7%A6%D7%A0%D7%A6%D7%99%D7%A4%D7%A8/{}', true);
