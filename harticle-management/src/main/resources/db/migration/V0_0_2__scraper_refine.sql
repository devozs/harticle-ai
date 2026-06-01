-- Scraper refinements: per-site content source, listing section cutoff, and
-- per-site page-history ceiling. Plus point sport5 at its current markup.

alter table scrape_site add column if not exists content_source      varchar(16) default 'REGEX';
alter table scrape_site add column if not exists listing_stop_marker  varchar(255);
alter table scrape_site add column if not exists max_history_pages    integer;

-- sport5's article body is no longer in <P> tags; it lives in the page's
-- JSON-LD as "articleBody". Switch its content source and store the matching
-- capture rule (kept meaningful/editable even though JSON_LD mode reads it
-- internally). Also cut the listing at the paging block so the trailing
-- "מומלצים" (recommended) section is ignored.
update scrape_site
set content_source     = 'JSON_LD',
    content_rule       = '"articleBody":"((?:\\.|[^"\\])*)"',
    listing_stop_marker = 'class="paging"'
where name = 'sport5';
