-- Reporter-article extraction (scraper) schema.
-- Ports the former Python harticle-engine/harticle/fetch_data.py into generic,
-- DB-driven configuration so sites/reporters/extraction-rules are data, not code.

create table if not exists scrape_site
(
    id uuid not null
        constraint scrape_site_pkey
        primary key,
    created_at        timestamp,
    updated_at        timestamp,
    version           bigint,
    name              varchar(64)  not null,
    base_url          varchar(255) not null,
    -- Extraction strategy. The three legacy sites keep their faithful hard-coded
    -- parsing (ONE/WALLA/SPORT5). New sites added via the API use GENERIC_REGEX,
    -- which applies the rule columns below.
    parser_strategy   varchar(32)  not null default 'GENERIC_REGEX',
    rule_type         varchar(16)  not null default 'REGEX',
    -- Rules used by GENERIC_REGEX. Each is a regex applied to fetched HTML.
    article_link_rule   varchar(512),
    article_link_filter varchar(128),
    title_rule          varchar(512),
    subtitle_rule       varchar(512),
    content_rule        varchar(512),
    date_rule           varchar(512),
    reporter_rule       varchar(512),
    enabled           boolean not null default true,
    constraint scrape_site_name_uk unique (name)
);

create table if not exists scrape_reporter
(
    id uuid not null
        constraint scrape_reporter_pkey
        primary key,
    created_at    timestamp,
    updated_at    timestamp,
    version       bigint,
    site_id       uuid         not null
        constraint scrape_reporter_site_fk references scrape_site (id),
    reporter_key  varchar(64)  not null,
    display_name  varchar(128) not null,
    -- Author/listing path with a single '{}' placeholder for the page number,
    -- e.g. '/Author/מוטי_פשכצקי?Page={}'.
    path_template varchar(512) not null,
    enabled       boolean not null default true,
    constraint scrape_reporter_key_uk unique (site_id, reporter_key)
);

create index if not exists scrape_reporter_site_idx on scrape_reporter (site_id);

create table if not exists scraped_article
(
    id uuid not null
        constraint scraped_article_pkey
        primary key,
    created_at     timestamp,
    updated_at     timestamp,
    version        bigint,
    reporter_id    uuid         not null
        constraint scraped_article_reporter_fk references scrape_reporter (id),
    site_id        uuid         not null
        constraint scraped_article_site_fk references scrape_site (id),
    source_url     varchar(1024) not null,
    title          varchar(512),
    sub_title      varchar(1024),
    content        text,
    published_date varchar(64),
    reporter_name  varchar(128),
    -- LLM fine-tune framing carried over from the legacy CSV (prompt/completion).
    prompt         text,
    completion     text,
    scraped_at     timestamp,
    -- Article identity for re-run override: the same URL may appear under
    -- different site/reporter pairs as distinct rows; re-scraping a given
    -- site+reporter overwrites its own rows in place.
    constraint scraped_article_identity_uk unique (site_id, reporter_id, source_url)
);

create index if not exists scraped_article_reporter_idx on scraped_article (reporter_id);

-- ---------------------------------------------------------------------------
-- Seed: the three sites and reporters that fetch_data.py scraped, with the
-- regex rules it used (kept for GENERIC_REGEX visibility/editing).
-- ---------------------------------------------------------------------------

insert into scrape_site (id, name, base_url, parser_strategy, rule_type,
                         article_link_rule, article_link_filter,
                         title_rule, subtitle_rule, content_rule, date_rule, reporter_rule, enabled)
values
 ('11111111-0000-0000-0000-000000000001', 'sport5', 'https://www.sport5.co.il', 'SPORT5', 'REGEX',
  '(?<=<h2><a href=")(.*)(?=">)', 'articles',
  '(?s)(?<=<h1 class="article-main">)(.+?)(?=</h1>)',
  '(?s)(?<=<h2 class="article-sub-main">)(.+?)(?=</h2>)',
  '([^<P>"]+)</P>',
  '(?<=&nbsp;&nbsp;)(.*)(?= -)',
  '(?<=ContentPlaceHolder1_ancWriter">)(.*)(?=</a>)', true),
 ('11111111-0000-0000-0000-000000000002', 'walla', 'https://sports.walla.co.il', 'WALLA', 'REGEX',
  '(https[^"]+)', 'item',
  '(?<=<h1 class="title   article_speakable">)(.*)(?=</h1)',
  '(?<=<p class="subtitle   article_speakable">)(.*)(?=</p)',
  '([^<p>"]+)</p>',
  '(?<=<div class="date">)(.*)(?=</div><time)',
  '(?<="itemAuthor":)(.*)(?=,"itemAu)', true),
 ('11111111-0000-0000-0000-000000000003', 'one', 'https://www.one.co.il', 'ONE', 'REGEX',
  '(/Article[^"]+)', 'Article',
  '(?<=article-main-title">)(.*)(?=</h1)',
  '(?<=article-sub-title">)(.*)(?=</h2)',
  '([^<p>"]+)</p>',
  '(?<=datePublished">)(.*)(?= \d)',
  '(?<= />\n)(.*)(?=</a>)', true);

insert into scrape_reporter (id, site_id, reporter_key, display_name, path_template, enabled)
values
 -- sport5
 ('22222222-0000-0000-5555-000000000001', '11111111-0000-0000-0000-000000000001', 'desk_sport5',     'מערכת אתר ערוץ הספורט', '/Author/מערכת_אתר_ערוץ_הספורט?Page={}', true),
 ('22222222-0000-0000-5555-000000000002', '11111111-0000-0000-0000-000000000001', 'moti_psheca',     'מוטי פשכצקי',          '/Author/מוטי_פשכצקי?Page={}', true),
 ('22222222-0000-0000-5555-000000000003', '11111111-0000-0000-0000-000000000001', 'tomer_levi',      'תומר לוי',             '/Author/תומר_לוי?Page={}', true),
 ('22222222-0000-0000-5555-000000000004', '11111111-0000-0000-0000-000000000001', 'igal_goldshtein', 'יגאל גולדשטיין',       '/Author/יגאל_גולדשטיין?Page={}', true),
 ('22222222-0000-0000-5555-000000000005', '11111111-0000-0000-0000-000000000001', 'or_riter',        'אור ריטר',             '/Author/אור_ריטר?Page={}', true),
 ('22222222-0000-0000-5555-000000000006', '11111111-0000-0000-0000-000000000001', 'hadar_yacobi',    'הדר יעקבי',            '/Author/הדר_יעקבי?Page={}', true),
 ('22222222-0000-0000-5555-000000000007', '11111111-0000-0000-0000-000000000001', 'yaniv_franco',    'יניב פרנקו',           '/Author/יניב_פרנקו?Page={}', true),
 ('22222222-0000-0000-5555-000000000008', '11111111-0000-0000-0000-000000000001', 'tamir_alyacni',   'תמיר אלחיאני',         '/Author/תמיר_אלחיאני?Page={}', true),
 ('22222222-0000-0000-5555-000000000009', '11111111-0000-0000-0000-000000000001', 'haim_zlachai',    'חיים זלקאי',           '/Author/חיים_זלקאי?Page={}', true),
 ('22222222-0000-0000-5555-000000000010', '11111111-0000-0000-0000-000000000001', 'oded_kremer',     'עודד קרמר',            '/Author/עודד_קרמר?Page={}', true),
 ('22222222-0000-0000-5555-000000000011', '11111111-0000-0000-0000-000000000001', 'yoav_modae',      'יואב מודעי',           '/Author/יואב_מודעי?Page={}', true),
 -- walla
 ('22222222-0000-0000-7777-000000000001', '11111111-0000-0000-0000-000000000002', 'yaniv_tuchman',   'יניב טוכמן',           '/writer/54?page={}', true),
 ('22222222-0000-0000-7777-000000000002', '11111111-0000-0000-0000-000000000002', 'ofir_sahar',      'אופיר סהר',            '/writer/11?page={}', true),
 ('22222222-0000-0000-7777-000000000003', '11111111-0000-0000-0000-000000000002', 'ron_amikam',      'רון עמיקם',            '/writer/2089?page={}', true),
 ('22222222-0000-0000-7777-000000000004', '11111111-0000-0000-0000-000000000002', 'shlomo_wies',     'שלמה וייס',            '/writer/12?page={}', true),
 ('22222222-0000-0000-7777-000000000005', '11111111-0000-0000-0000-000000000002', 'paz_hasdai',      'פז חסדאי',             '/writer/14?page={}', true),
 ('22222222-0000-0000-7777-000000000006', '11111111-0000-0000-0000-000000000002', 'oren_josipovich', 'אורן יוסיפוביץ',       '/writer/17?page={}', true),
 -- one
 ('22222222-0000-0000-1111-000000000001', '11111111-0000-0000-0000-000000000003', 'desk_one',        'מערכת ONE',            '/cat/Articles/MoreArticles.aspx?author=מערכת+ONE&page={}', true),
 ('22222222-0000-0000-1111-000000000002', '11111111-0000-0000-0000-000000000003', 'raz_amir',        'רז אמיר',              '/cat/Articles/MoreArticles.aspx?author=רז+אמיר&page={}', true),
 ('22222222-0000-0000-1111-000000000003', '11111111-0000-0000-0000-000000000003', 'guy_ben_ziv',     'גיא בן זיו',           '/cat/Articles/MoreArticles.aspx?author=גיא+בן+זיו&page={}', true),
 ('22222222-0000-0000-1111-000000000004', '11111111-0000-0000-0000-000000000003', 'asi_maman',       'אסי ממן',              '/cat/Articles/MoreArticles.aspx?author=אסי+ממן&page={}', true),
 ('22222222-0000-0000-1111-000000000005', '11111111-0000-0000-0000-000000000003', 'itzik_kalfi',     'איציק כלפי',           '/cat/Articles/MoreArticles.aspx?author=איציק+כלפי&page={}', true);
