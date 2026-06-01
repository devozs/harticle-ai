import type {
  ArticlePreviewResult,
  ListingPreviewResult,
  PreviewRequest,
  ScrapeProgress,
  ScrapeReporter,
  ScrapeReporterDto,
  ScrapeRunSummary,
  ScrapeSite,
  ScrapeSiteDto,
  ScrapedArticle,
} from '~/types/scraper'

// Typed $fetch wrappers for the scraper REST API (harticle-management).
// Built on useApi().apiBase, which run_dev.sh points at the live mgmt port.
export function useScraperApi() {
  const { apiBase } = useApi()
  const base = () => `${apiBase.value}/scraper`

  // --- sites ---------------------------------------------------------------
  const listSites = () =>
    $fetch<ScrapeSite[]>(`${base()}/sites`)

  const getSite = (id: string) =>
    $fetch<ScrapeSite>(`${base()}/sites/${id}`)

  const saveSite = (dto: ScrapeSiteDto, id?: string) =>
    id
      ? $fetch<ScrapeSite>(`${base()}/sites/${id}`, { method: 'PUT', body: dto })
      : $fetch<ScrapeSite>(`${base()}/sites`, { method: 'POST', body: dto })

  const deleteSite = (id: string) =>
    $fetch(`${base()}/sites/${id}`, { method: 'DELETE' })

  // --- reporters -----------------------------------------------------------
  const listReporters = (siteId?: string) =>
    $fetch<ScrapeReporter[]>(`${base()}/reporters`, {
      query: siteId ? { siteId } : undefined,
    })

  const saveReporter = (dto: ScrapeReporterDto, id?: string) =>
    id
      ? $fetch<ScrapeReporter>(`${base()}/reporters/${id}`, { method: 'PUT', body: dto })
      : $fetch<ScrapeReporter>(`${base()}/reporters`, { method: 'POST', body: dto })

  const bulkAddReporters = (siteId: string, dtos: ScrapeReporterDto[]) =>
    $fetch<ScrapeReporter[]>(`${base()}/reporters/bulk/${siteId}`, {
      method: 'POST',
      body: dtos,
    })

  const deleteReporter = (id: string) =>
    $fetch(`${base()}/reporters/${id}`, { method: 'DELETE' })

  // --- run -----------------------------------------------------------------
  // `pages` caps listing pages per reporter (undefined = site/global max).
  // `force` re-scrapes already-handled articles, but ONLY within the run's scope.
  const runQuery = (pages?: number, force?: boolean) => {
    const q: Record<string, string | number> = {}
    if (pages && pages > 0) q.pages = pages
    if (force) q.force = 'true'
    return Object.keys(q).length ? q : undefined
  }

  const runAll = (pages?: number, force?: boolean) =>
    $fetch<string>(`${base()}/run`, { method: 'POST', query: runQuery(pages, force) })

  const runReporter = (reporterId: string, pages?: number, force?: boolean) =>
    $fetch<string>(`${base()}/run/${reporterId}`, { method: 'POST', query: runQuery(pages, force) })

  const runReporterSync = (reporterId: string, pages?: number, force?: boolean) =>
    $fetch<ScrapeRunSummary>(`${base()}/run-sync/${reporterId}`, { method: 'POST', query: runQuery(pages, force) })

  const runSite = (siteId: string, pages?: number, force?: boolean) =>
    $fetch<string>(`${base()}/run/site/${siteId}`, { method: 'POST', query: runQuery(pages, force) })

  const runSiteSync = (siteId: string, pages?: number, force?: boolean) =>
    $fetch<ScrapeRunSummary>(`${base()}/run-sync/site/${siteId}`, { method: 'POST', query: runQuery(pages, force) })

  const runStatus = () =>
    $fetch<ScrapeProgress>(`${base()}/run/status`)

  const stopRun = () =>
    $fetch<ScrapeProgress>(`${base()}/run/stop`, { method: 'POST' })

  // --- preview (no persist) ------------------------------------------------
  const previewArticle = (req: PreviewRequest) =>
    $fetch<ArticlePreviewResult>(`${base()}/preview/article`, { method: 'POST', body: req })

  const previewListing = (req: PreviewRequest) =>
    $fetch<ListingPreviewResult>(`${base()}/preview/listing`, { method: 'POST', body: req })

  // --- results -------------------------------------------------------------
  const listArticles = (reporterId?: string) =>
    $fetch<ScrapedArticle[]>(`${base()}/articles`, {
      query: reporterId ? { reporterId } : undefined,
    })

  return {
    listSites,
    getSite,
    saveSite,
    deleteSite,
    listReporters,
    saveReporter,
    bulkAddReporters,
    deleteReporter,
    runAll,
    runReporter,
    runReporterSync,
    runSite,
    runSiteSync,
    runStatus,
    stopRun,
    previewArticle,
    previewListing,
    listArticles,
  }
}
