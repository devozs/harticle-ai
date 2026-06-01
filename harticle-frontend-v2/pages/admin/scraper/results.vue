<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { ScrapedArticle } from '~/types/scraper'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useScraperStore()
const { sites, reporters, articles, runSummary, running, progress } = storeToRefs(store)

const runSiteId = ref('')
const runReporterId = ref('')
const articleFilterReporter = ref('')
const selected = ref<ScrapedArticle | undefined>()
const note = ref('')

// Per-run page cap (blank = site/global max). One input per run scope.
const pagesAll = ref<number | undefined>()
const pagesSite = ref<number | undefined>()
const pagesReporter = ref<number | undefined>()

// Per-run force re-scrape. Scoped to its own run only (all / site / reporter).
const forceAll = ref(false)
const forceSite = ref(false)
const forceReporter = ref(false)

// Review-pane dock side, remembered across visits.
const dock = useLocalStorage<'side' | 'bottom'>('scraper.reviewDock', 'side')

onMounted(async () => {
  await Promise.all([store.fetchSites(), store.fetchReporters(), store.fetchArticles()])
  // Pick up a run already in flight (started before reload / from another tab).
  await store.fetchStatus()
  if (progress.value?.running) pollRun()
})

onUnmounted(() => store.stopStatusPolling())

// Reporter dropdowns scoped to the chosen site where relevant.
const reportersForRun = computed(() =>
  runSiteId.value ? reporters.value.filter(r => r.site?.id === runSiteId.value) : reporters.value,
)

// Poll live status; when the run finishes, refresh the articles table.
function pollRun() {
  store.startStatusPolling(() => store.fetchArticles(articleFilterReporter.value || undefined))
}

async function runAll() {
  note.value = ''
  await store.runAllAsync(pagesAll.value, forceAll.value)
  note.value = 'Async scrape started for all enabled reporters. Watch live status below.'
  pollRun()
}

async function runSite() {
  if (!runSiteId.value) return
  pollRun()
  await store.runSiteSync(runSiteId.value, pagesSite.value, forceSite.value)
  await store.fetchArticles(articleFilterReporter.value || undefined)
}

async function runReporter() {
  if (!runReporterId.value) return
  pollRun()
  await store.runReporterSync(runReporterId.value, pagesReporter.value, forceReporter.value)
  await store.fetchArticles(articleFilterReporter.value || undefined)
}

async function refreshArticles() {
  await store.fetchArticles(articleFilterReporter.value || undefined)
}
</script>

<template>
  <div>
    <h1 class="text-xl font-bold text-gray-900">Run & Results</h1>
    <p class="mt-1 text-sm text-gray-500">Trigger scrapes at any level and review the articles that land in the DB.</p>

    <!-- run controls -->
    <div class="mt-4 grid grid-cols-1 gap-4 md:grid-cols-3">
      <div class="rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
        <h3 class="text-sm font-bold text-gray-800">All reporters</h3>
        <p class="mt-1 text-xs text-gray-500">Fire-and-forget async run.</p>
        <input
          v-model.number="pagesAll"
          type="number"
          min="1"
          placeholder="Pages (blank = max)"
          class="mt-2 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
        <label class="mt-2 flex items-center gap-2 text-xs text-gray-600">
          <input v-model="forceAll" type="checkbox" class="rounded border-gray-300 text-cyan-600">
          Force re-scrape (overwrite all reporters' existing articles)
        </label>
        <button
          type="button"
          class="mt-3 rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
          :disabled="running"
          @click="runAll"
        >
          Run all (async)
        </button>
      </div>

      <div class="rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
        <h3 class="text-sm font-bold text-gray-800">By site</h3>
        <select
          v-model="runSiteId"
          class="mt-2 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
          <option value="">Select site…</option>
          <option v-for="site in sites" :key="site.id" :value="site.id">{{ site.name }}</option>
        </select>
        <input
          v-model.number="pagesSite"
          type="number"
          min="1"
          placeholder="Pages (blank = max)"
          class="mt-2 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
        <label class="mt-2 flex items-center gap-2 text-xs text-gray-600">
          <input v-model="forceSite" type="checkbox" class="rounded border-gray-300 text-cyan-600">
          Force re-scrape (overwrite this site's existing articles)
        </label>
        <button
          type="button"
          class="mt-3 rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
          :disabled="running || !runSiteId"
          @click="runSite"
        >
          {{ running ? 'Running…' : 'Run site (sync)' }}
        </button>
      </div>

      <div class="rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
        <h3 class="text-sm font-bold text-gray-800">By reporter</h3>
        <select
          v-model="runReporterId"
          class="mt-2 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
          <option value="">Select reporter…</option>
          <option v-for="r in reportersForRun" :key="r.id" :value="r.id">
            {{ r.displayName }} ({{ r.site?.name }})
          </option>
        </select>
        <input
          v-model.number="pagesReporter"
          type="number"
          min="1"
          placeholder="Pages (blank = max)"
          class="mt-2 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
        <label class="mt-2 flex items-center gap-2 text-xs text-gray-600">
          <input v-model="forceReporter" type="checkbox" class="rounded border-gray-300 text-cyan-600">
          Force re-scrape (overwrite this reporter's existing articles)
        </label>
        <button
          type="button"
          class="mt-3 rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
          :disabled="running || !runReporterId"
          @click="runReporter"
        >
          {{ running ? 'Running…' : 'Run reporter (sync)' }}
        </button>
      </div>
    </div>

    <p v-if="note" class="mt-3 text-sm text-cyan-700">{{ note }}</p>

    <!-- live, while running (or just finished) -->
    <ScraperLiveProgressCard v-if="progress" :progress="progress" class="mt-4" @stop="store.stopRun()" />

    <ScraperRunSummaryCard v-if="runSummary" :summary="runSummary" class="mt-4" />

    <!-- results -->
    <div class="mt-6 flex items-center gap-2">
      <h2 class="text-sm font-bold text-gray-800">Scraped articles</h2>
      <select
        v-model="articleFilterReporter"
        class="rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        @change="refreshArticles"
      >
        <option value="">All reporters</option>
        <option v-for="r in reporters" :key="r.id" :value="r.id">{{ r.displayName }}</option>
      </select>
      <button
        type="button"
        class="rounded-lg border border-gray-300 px-3 py-1.5 text-sm text-gray-600 hover:bg-gray-100"
        @click="refreshArticles"
      >
        Refresh
      </button>

      <!-- review pane dock toggle (remembered) -->
      <div class="ml-auto flex items-center gap-1 text-xs">
        <span class="text-gray-400">Review pane:</span>
        <button
          type="button"
          class="rounded-lg border px-2 py-1"
          :class="dock === 'side' ? 'border-cyan-600 bg-cyan-50 text-cyan-700' : 'border-gray-300 text-gray-600'"
          @click="dock = 'side'"
        >
          Side
        </button>
        <button
          type="button"
          class="rounded-lg border px-2 py-1"
          :class="dock === 'bottom' ? 'border-cyan-600 bg-cyan-50 text-cyan-700' : 'border-gray-300 text-gray-600'"
          @click="dock = 'bottom'"
        >
          Bottom
        </button>
      </div>
    </div>

    <div class="mt-3 rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
      <ScraperArticleTable :articles="articles" :selected-id="selected?.id" @open="selected = $event" />
    </div>

    <!-- Stays mounted; clicking another row just reassigns `selected`, so the
         pane updates live without needing to close it first. -->
    <ScraperArticleDetailDrawer
      v-if="selected"
      :article="selected"
      :dock="dock"
      @close="selected = undefined"
    />
  </div>
</template>
