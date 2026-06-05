<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { ScrapedArticle } from '~/types/scraper'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useScraperStore()
const { confirm } = useConfirm()
const { sites, reporters, articles, runSummary, running, progress } = storeToRefs(store)

type Scope = 'all' | 'site' | 'reporter'

// One unified run/manage panel: a scope selector reveals the relevant picker,
// and Pages / Force / Run / Delete all act on that single chosen scope.
const scope = ref<Scope>('all')
const runSiteId = ref('')
const runReporterId = ref('')
const pages = ref<number | undefined>()
const force = ref(false)

const articleFilterReporter = ref('')
const selected = ref<ScrapedArticle | undefined>()
const note = ref('')

// Review-pane dock side, remembered across visits.
const dock = useLocalStorage<'side' | 'bottom'>('scraper.reviewDock', 'side')

onMounted(async () => {
  await Promise.all([store.fetchSites(), store.fetchReporters(), store.fetchArticles()])
  // Pick up a run already in flight (started before reload / from another tab).
  await store.fetchStatus()
  if (progress.value?.running) pollRun()
})

onUnmounted(() => store.stopStatusPolling())

// Reporter dropdown scoped to the chosen site where relevant.
const reportersForRun = computed(() =>
  runSiteId.value ? reporters.value.filter(r => r.site?.id === runSiteId.value) : reporters.value,
)

// A site/reporter scope needs a target id before Run/Delete can act.
const scopeReady = computed(() =>
  scope.value === 'all'
  || (scope.value === 'site' && !!runSiteId.value)
  || (scope.value === 'reporter' && !!runReporterId.value),
)

const scopeLabel = computed(() => {
  if (scope.value === 'site') {
    return sites.value.find(s => s.id === runSiteId.value)?.name ?? 'the selected site'
  }
  if (scope.value === 'reporter') {
    return reporters.value.find(r => r.id === runReporterId.value)?.displayName ?? 'the selected reporter'
  }
  return 'all reporters'
})

// Poll live status; when the run finishes, refresh the articles table.
function pollRun() {
  store.startStatusPolling(() => store.fetchArticles(articleFilterReporter.value || undefined))
}

// Single Run entry point — dispatches on the chosen scope. "All" is async
// (fire-and-forget); site/reporter are sync so the summary returns inline.
async function run() {
  if (!scopeReady.value) return
  note.value = ''
  if (scope.value === 'all') {
    await store.runAllAsync(pages.value, force.value)
    note.value = 'Async scrape started for all enabled reporters. Watch live status below.'
    pollRun()
    return
  }
  pollRun()
  if (scope.value === 'site') {
    await store.runSiteSync(runSiteId.value, pages.value, force.value)
  } else {
    await store.runReporterSync(runReporterId.value, pages.value, force.value)
  }
  await store.fetchArticles(articleFilterReporter.value || undefined)
}

// Delete scraped articles in the chosen scope (independent of any run).
async function deleteScraped() {
  if (!scopeReady.value) return
  const ok = await confirm({
    title: `Delete all scraped articles for ${scopeLabel.value}?`,
    message: 'This cannot be undone.',
    confirmLabel: 'Delete',
    tone: 'danger',
  })
  if (!ok) return
  note.value = ''
  const id = scope.value === 'site' ? runSiteId.value
    : scope.value === 'reporter' ? runReporterId.value
      : undefined
  const deleted = await store.deleteArticles(scope.value, id)
  if (selected.value && !articles.value.some(a => a.id === selected.value?.id)) {
    selected.value = undefined
  }
  note.value = `Deleted ${deleted} scraped article(s) for ${scopeLabel.value}.`
}

async function refreshArticles() {
  await store.fetchArticles(articleFilterReporter.value || undefined)
}
</script>

<template>
  <div>
    <h1 class="text-xl font-bold text-gray-900">Run & Results</h1>
    <p class="mt-1 text-sm text-gray-500">Trigger scrapes at any level and review the articles that land in the DB.</p>

    <!-- unified run / manage panel -->
    <div class="mt-4 rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
      <!-- scope selector -->
      <div class="flex flex-wrap items-center gap-2">
        <span class="text-sm font-bold text-gray-800">Scope</span>
        <div class="inline-flex overflow-hidden rounded-lg border border-gray-300">
          <button
            v-for="opt in (['all', 'site', 'reporter'] as const)"
            :key="opt"
            type="button"
            class="px-3 py-1.5 text-sm capitalize"
            :class="scope === opt ? 'bg-cyan-700 text-white' : 'bg-white text-gray-600 hover:bg-gray-50'"
            @click="scope = opt"
          >
            {{ opt === 'all' ? 'All reporters' : `By ${opt}` }}
          </button>
        </div>
      </div>

      <!-- scope target picker -->
      <div class="mt-3 grid grid-cols-1 gap-3 sm:grid-cols-2 lg:grid-cols-4">
        <select
          v-if="scope === 'site'"
          v-model="runSiteId"
          class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
          <option value="">Select site…</option>
          <option v-for="site in sites" :key="site.id" :value="site.id">{{ site.name }}</option>
        </select>

        <select
          v-if="scope === 'reporter'"
          v-model="runReporterId"
          class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
          <option value="">Select reporter…</option>
          <option v-for="r in reportersForRun" :key="r.id" :value="r.id">
            {{ r.displayName }} ({{ r.site?.name }})
          </option>
        </select>

        <input
          v-model.number="pages"
          type="number"
          min="1"
          placeholder="Pages (blank = max)"
          class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >

        <label class="flex items-center gap-2 text-xs text-gray-600">
          <input v-model="force" type="checkbox" class="rounded border-gray-300 text-cyan-600">
          Force re-scrape (overwrite existing articles in scope)
        </label>
      </div>

      <!-- actions -->
      <div class="mt-4 flex flex-wrap items-center gap-2">
        <button
          type="button"
          class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
          :disabled="running || !scopeReady"
          @click="run"
        >
          {{ running ? 'Running…' : scope === 'all' ? 'Run all (async)' : `Run ${scope} (sync)` }}
        </button>
        <span class="text-sm text-gray-500">Target: <span class="font-medium text-gray-700">{{ scopeLabel }}</span></span>

        <button
          type="button"
          class="ml-auto rounded-lg border border-red-300 px-3 py-2 text-sm font-medium text-red-600 hover:bg-red-50 disabled:opacity-40"
          :disabled="running || !scopeReady"
          @click="deleteScraped"
        >
          Delete scraped ({{ scope }})
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
