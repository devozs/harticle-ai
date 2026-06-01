// Mirrors the scraper API in harticle-management (camelCase JSON keys).

export type ParserStrategy = 'ONE' | 'WALLA' | 'SPORT5' | 'GENERIC_REGEX'
export type RuleType = 'REGEX' | 'CSS'
export type ContentSource = 'REGEX' | 'JSON_LD'

// Common BaseEntity fields present on every persisted entity.
interface BaseEntity {
  id: string
  version?: number
  createdAt?: string
  updatedAt?: string
}

export interface ScrapeSite extends BaseEntity {
  name: string
  baseUrl: string
  parserStrategy: ParserStrategy
  ruleType: RuleType
  articleLinkRule?: string
  articleLinkFilter?: string
  titleRule?: string
  subtitleRule?: string
  contentRule?: string
  contentSource?: ContentSource
  dateRule?: string
  reporterRule?: string
  listingStopMarker?: string
  maxHistoryPages?: number
  enabled: boolean
}

export interface ScrapeReporter extends BaseEntity {
  site?: ScrapeSite
  reporterKey: string
  displayName: string
  pathTemplate: string
  enabled: boolean
}

export interface ScrapedArticle extends BaseEntity {
  site?: ScrapeSite
  reporter?: ScrapeReporter
  sourceUrl: string
  title?: string
  subTitle?: string
  content?: string
  publishedDate?: string
  reporterName?: string
  scrapedAt?: string
}

// --- request payloads ------------------------------------------------------

export interface ScrapeSiteDto {
  name?: string
  baseUrl?: string
  parserStrategy?: ParserStrategy
  ruleType?: RuleType
  articleLinkRule?: string
  articleLinkFilter?: string
  titleRule?: string
  subtitleRule?: string
  contentRule?: string
  contentSource?: ContentSource
  dateRule?: string
  reporterRule?: string
  listingStopMarker?: string
  maxHistoryPages?: number
  enabled?: boolean
}

export interface ScrapeReporterDto {
  siteId?: string
  reporterKey?: string
  displayName?: string
  pathTemplate?: string
  enabled?: boolean
}

export interface PreviewRequest {
  siteId?: string
  url: string
  // inline rule overrides (null/omitted = fall back to the loaded site)
  baseUrl?: string
  parserStrategy?: ParserStrategy
  articleLinkRule?: string
  articleLinkFilter?: string
  titleRule?: string
  subtitleRule?: string
  contentRule?: string
  contentSource?: ContentSource
  dateRule?: string
  reporterRule?: string
  listingStopMarker?: string
}

// --- responses -------------------------------------------------------------

export interface FieldResult {
  field: string
  rule?: string
  matched: boolean
  length: number
  sample?: string
}

export interface ArticlePreviewResult {
  url: string
  siteName: string
  success: boolean
  verdict: string
  htmlLength: number
  fetchOk: boolean
  fields: FieldResult[]
}

export interface ListingPreviewResult {
  url: string
  siteName: string
  success: boolean
  verdict: string
  htmlLength: number
  fetchOk: boolean
  linkCount: number
  linkRule?: string
  sampleLinks: string[]
}

export interface ScrapeRunSummary {
  reportersProcessed: number
  pagesFetched: number
  articlesSaved: number
  articlesUpdated: number
  articlesSkipped: number
  errors: number
  messages: string[]
}

export interface ScrapeProgress {
  running: boolean
  cancelRequested: boolean
  phase: string
  currentSite?: string
  currentReporter?: string
  currentUrl?: string
  currentPage: number
  reportersProcessed: number
  pagesFetched: number
  articlesSaved: number
  articlesUpdated: number
  articlesSkipped: number
  errors: number
  startedAtEpochMs?: number
  lastActivityEpochMs?: number
  finishedAtEpochMs?: number
  secondsSinceActivity?: number
  lastMessage?: string
}
