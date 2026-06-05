<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { ScrapeReporter, ScrapeReporterDto } from '~/types/scraper'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useScraperStore()
const { confirm } = useConfirm()
const { sites, reporters, runSummary } = storeToRefs(store)

const siteFilter = ref<string>('')
const drawerOpen = ref(false)
const editingId = ref<string | undefined>()
const draft = ref<ScrapeReporterDto>(emptyDraft())
const error = ref('')
const saving = ref(false)

function emptyDraft(): ScrapeReporterDto {
  return { displayName: '', reporterKey: '', pathTemplate: '', enabled: true }
}

const filtered = computed(() =>
  siteFilter.value
    ? reporters.value.filter(r => r.site?.id === siteFilter.value)
    : reporters.value,
)

onMounted(async () => {
  await Promise.all([store.fetchSites(), store.fetchReporters()])
})

function openCreate() {
  if (!siteFilter.value) {
    error.value = 'Pick a site first (filter) to attach the new reporter to.'
    return
  }
  editingId.value = undefined
  draft.value = emptyDraft()
  error.value = ''
  drawerOpen.value = true
}

function openEdit(reporter: ScrapeReporter) {
  editingId.value = reporter.id
  draft.value = {
    siteId: reporter.site?.id,
    reporterKey: reporter.reporterKey,
    displayName: reporter.displayName,
    pathTemplate: reporter.pathTemplate,
    enabled: reporter.enabled,
  }
  error.value = ''
  drawerOpen.value = true
}

async function save() {
  error.value = ''
  saving.value = true
  try {
    const dto: ScrapeReporterDto = { ...draft.value, siteId: draft.value.siteId || siteFilter.value }
    await store.saveReporter(dto, editingId.value)
    drawerOpen.value = false
  } catch (e) {
    error.value = String(e)
  } finally {
    saving.value = false
  }
}

async function bulkAdd(dtos: ScrapeReporterDto[]) {
  if (!siteFilter.value) {
    error.value = 'Pick a site first to bulk-add reporters.'
    return
  }
  await store.bulkAddReporters(siteFilter.value, dtos)
}

async function remove(reporter: ScrapeReporter) {
  const ok = await confirm({
    title: `Delete reporter "${reporter.displayName}"?`,
    confirmLabel: 'Delete',
    tone: 'danger',
  })
  if (!ok) return
  await store.deleteReporter(reporter.id)
}

async function runSync(reporter: ScrapeReporter) {
  await store.runReporterSync(reporter.id)
}

async function runAsync(reporter: ScrapeReporter) {
  await store.runReporterAsync(reporter.id)
  alert(`Async scrape started for ${reporter.displayName}.`)
}
</script>

<template>
  <div>
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-xl font-bold text-gray-900">Reporters</h1>
        <p class="mt-1 text-sm text-gray-500">Reporters per site. Trigger a scrape for any one of them.</p>
      </div>
      <button
        type="button"
        class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800"
        @click="openCreate"
      >
        New reporter
      </button>
    </div>

    <div class="mt-4 flex items-center gap-2">
      <label class="text-sm text-gray-600">Site:</label>
      <select
        v-model="siteFilter"
        class="rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
      >
        <option value="">All sites</option>
        <option v-for="site in sites" :key="site.id" :value="site.id">{{ site.name }}</option>
      </select>
    </div>

    <p v-if="error" class="mt-2 text-sm text-red-600">{{ error }}</p>

    <ScraperRunSummaryCard v-if="runSummary" :summary="runSummary" class="mt-4" />

    <div class="mt-4 rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
      <ScraperReporterTable
        :reporters="filtered"
        @edit="openEdit"
        @remove="remove"
        @run-sync="runSync"
        @run-async="runAsync"
      />
    </div>

    <div v-if="siteFilter" class="mt-4 rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
      <h3 class="text-sm font-bold text-gray-800">Bulk add to selected site</h3>
      <div class="mt-2">
        <ScraperBulkReporterForm @submit="bulkAdd" />
      </div>
    </div>

    <!-- edit/create drawer -->
    <div v-if="drawerOpen" class="fixed inset-0 z-40 flex justify-end" @click.self="drawerOpen = false">
      <div class="absolute inset-0 bg-black/30" />
      <aside class="relative z-50 h-full w-full max-w-xl overflow-auto bg-white p-6 shadow-xl">
        <div class="flex items-start justify-between">
          <h2 class="text-lg font-bold text-gray-900">{{ editingId ? 'Edit reporter' : 'New reporter' }}</h2>
          <button type="button" class="text-gray-400 hover:text-gray-700" @click="drawerOpen = false">✕</button>
        </div>

        <div class="mt-4">
          <ScraperReporterForm v-model="draft" />
        </div>

        <p v-if="error" class="mt-3 text-sm text-red-600">{{ error }}</p>

        <div class="mt-4 flex gap-2">
          <button
            type="button"
            class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
            :disabled="saving"
            @click="save"
          >
            {{ saving ? 'Saving…' : 'Save' }}
          </button>
          <button
            type="button"
            class="rounded-lg border border-gray-300 px-4 py-2 text-sm text-gray-600 hover:bg-gray-100"
            @click="drawerOpen = false"
          >
            Cancel
          </button>
        </div>
      </aside>
    </div>
  </div>
</template>
