import { defineStore } from 'pinia'
import type {
  ScrapeProgress,
  ScrapeReporter,
  ScrapeReporterDto,
  ScrapeRunSummary,
  ScrapeSite,
  ScrapeSiteDto,
  ScrapedArticle,
} from '~/types/scraper'

let statusInterval: ReturnType<typeof setInterval> | undefined

export const useScraperStore = defineStore('scraper', {
  state: () => ({
    sites: [] as ScrapeSite[],
    reporters: [] as ScrapeReporter[],
    articles: [] as ScrapedArticle[],
    loading: false,
    running: false,
    runSummary: undefined as ScrapeRunSummary | undefined,
    progress: undefined as ScrapeProgress | undefined,
    error: null as unknown,
  }),

  getters: {
    siteById: state => (id?: string) =>
      state.sites.find(site => site.id === id),
    reportersForSite: state => (siteId?: string) =>
      siteId
        ? state.reporters.filter(reporter => reporter.site?.id === siteId)
        : state.reporters,
  },

  actions: {
    // --- sites -------------------------------------------------------------
    async fetchSites() {
      const { listSites } = useScraperApi()
      try {
        this.loading = true
        this.sites = await listSites()
      } catch (error) {
        this.error = error
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async saveSite(dto: ScrapeSiteDto, id?: string) {
      const { saveSite } = useScraperApi()
      const saved = await saveSite(dto, id)
      const index = this.sites.findIndex(site => site.id === saved.id)
      if (index === -1) this.sites.push(saved)
      else this.sites[index] = saved
      return saved
    },

    async deleteSite(id: string) {
      const { deleteSite } = useScraperApi()
      await deleteSite(id)
      this.sites = this.sites.filter(site => site.id !== id)
    },

    // --- reporters ---------------------------------------------------------
    async fetchReporters(siteId?: string) {
      const { listReporters } = useScraperApi()
      try {
        this.loading = true
        this.reporters = await listReporters(siteId)
      } catch (error) {
        this.error = error
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async saveReporter(dto: ScrapeReporterDto, id?: string) {
      const { saveReporter } = useScraperApi()
      const saved = await saveReporter(dto, id)
      const index = this.reporters.findIndex(reporter => reporter.id === saved.id)
      if (index === -1) this.reporters.push(saved)
      else this.reporters[index] = saved
      return saved
    },

    async bulkAddReporters(siteId: string, dtos: ScrapeReporterDto[]) {
      const { bulkAddReporters } = useScraperApi()
      const saved = await bulkAddReporters(siteId, dtos)
      this.reporters.push(...saved)
      return saved
    },

    async deleteReporter(id: string) {
      const { deleteReporter } = useScraperApi()
      await deleteReporter(id)
      this.reporters = this.reporters.filter(reporter => reporter.id !== id)
    },

    // --- run --------------------------------------------------------------
    // `pages` caps listing pages; `force` re-scrapes already-handled articles
    // within the run's scope only.
    async runReporterSync(reporterId: string, pages?: number, force?: boolean) {
      const { runReporterSync } = useScraperApi()
      try {
        this.running = true
        this.runSummary = await runReporterSync(reporterId, pages, force)
        return this.runSummary
      } finally {
        this.running = false
      }
    },

    async runSiteSync(siteId: string, pages?: number, force?: boolean) {
      const { runSiteSync } = useScraperApi()
      try {
        this.running = true
        this.runSummary = await runSiteSync(siteId, pages, force)
        return this.runSummary
      } finally {
        this.running = false
      }
    },

    async runReporterAsync(reporterId: string, pages?: number, force?: boolean) {
      const { runReporter } = useScraperApi()
      return runReporter(reporterId, pages, force)
    },

    async runSiteAsync(siteId: string, pages?: number, force?: boolean) {
      const { runSite } = useScraperApi()
      return runSite(siteId, pages, force)
    },

    async runAllAsync(pages?: number, force?: boolean) {
      const { runAll } = useScraperApi()
      return runAll(pages, force)
    },

    // --- live progress -----------------------------------------------------
    async fetchStatus() {
      const { runStatus } = useScraperApi()
      try {
        this.progress = await runStatus()
      } catch (error) {
        this.error = error
      }
    },

    async stopRun() {
      const { stopRun } = useScraperApi()
      this.progress = await stopRun()
    },

    // Poll run status every 2s. Auto-stops once the run is finished AND the
    // articles have been refreshed, so a completed run leaves fresh results.
    //
    // Guards against the start race: a run POST and the first status GET fire
    // nearly together, so the first snapshot often still shows running=false
    // (backend hasn't flipped the flag yet). We therefore only treat
    // "not running" as finished once we've actually observed running=true, OR
    // after a short startup grace of a few ticks (covers a run that finishes
    // almost instantly).
    startStatusPolling(onFinish?: () => void) {
      this.stopStatusPolling()
      let observedRunning = false
      let ticks = 0
      const startupGraceTicks = 4 // ~8s before we trust an unstarted snapshot
      const tick = async () => {
        ticks += 1
        await this.fetchStatus()
        if (this.progress?.running) {
          observedRunning = true
          return
        }
        // progress is not running here
        if (observedRunning || ticks >= startupGraceTicks) {
          this.stopStatusPolling()
          if (onFinish) onFinish()
        }
      }
      void tick()
      statusInterval = setInterval(() => void tick(), 2000)
    },

    stopStatusPolling() {
      if (statusInterval) {
        clearInterval(statusInterval)
        statusInterval = undefined
      }
    },

    // --- delete scraped articles (scoped) ---------------------------------
    // Returns the number of rows deleted; refreshes the local list afterwards.
    async deleteArticles(scope: 'all' | 'site' | 'reporter', id?: string) {
      const api = useScraperApi()
      const res = await (
        scope === 'site' && id
          ? api.deleteArticlesBySite(id)
          : scope === 'reporter' && id
            ? api.deleteArticlesByReporter(id)
            : api.deleteAllArticles()
      )
      await this.fetchArticles()
      return res.deleted
    },

    // --- results -----------------------------------------------------------
    async fetchArticles(reporterId?: string) {
      const { listArticles } = useScraperApi()
      try {
        this.loading = true
        this.articles = await listArticles(reporterId)
      } catch (error) {
        this.error = error
        console.error(error)
      } finally {
        this.loading = false
      }
    },
  },
})
