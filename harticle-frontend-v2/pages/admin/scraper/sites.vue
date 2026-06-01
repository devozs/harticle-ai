<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { ScrapeSite, ScrapeSiteDto } from '~/types/scraper'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useScraperStore()
const { sites, runSummary } = storeToRefs(store)

const drawerOpen = ref(false)
const editingId = ref<string | undefined>()
const draft = ref<ScrapeSiteDto>(emptyDraft())
const saving = ref(false)
const error = ref('')

function emptyDraft(): ScrapeSiteDto {
  return {
    name: '',
    baseUrl: '',
    parserStrategy: 'GENERIC_REGEX',
    ruleType: 'REGEX',
    enabled: true,
  }
}

function toDraft(site: ScrapeSite): ScrapeSiteDto {
  const { id, version, createdAt, updatedAt, ...rest } = site
  return { ...rest }
}

onMounted(() => store.fetchSites())

function openCreate() {
  editingId.value = undefined
  draft.value = emptyDraft()
  error.value = ''
  drawerOpen.value = true
}

function openEdit(site: ScrapeSite) {
  editingId.value = site.id
  draft.value = toDraft(site)
  error.value = ''
  drawerOpen.value = true
}

async function save() {
  error.value = ''
  saving.value = true
  try {
    await store.saveSite(draft.value, editingId.value)
    drawerOpen.value = false
  } catch (e) {
    error.value = String(e)
  } finally {
    saving.value = false
  }
}

async function remove(site: ScrapeSite) {
  if (!confirm(`Delete site "${site.name}"? Its reporters and rules go with it.`)) return
  await store.deleteSite(site.id)
}

async function runSite(site: ScrapeSite) {
  if (!confirm(`Run a full scrape of all enabled reporters on "${site.name}"?`)) return
  await store.runSiteSync(site.id)
}
</script>

<template>
  <div>
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-xl font-bold text-gray-900">Sites & Rules</h1>
        <p class="mt-1 text-sm text-gray-500">Configure sites and their extraction rules. Test rules live before saving.</p>
      </div>
      <button
        type="button"
        class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800"
        @click="openCreate"
      >
        New site
      </button>
    </div>

    <ScraperRunSummaryCard v-if="runSummary" :summary="runSummary" class="mt-4" />

    <div class="mt-4 rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
      <ScraperSiteTable
        :sites="sites"
        @edit="openEdit"
        @remove="remove"
        @run="runSite"
      />
    </div>

    <!-- edit drawer -->
    <div v-if="drawerOpen" class="fixed inset-0 z-40 flex justify-end" @click.self="drawerOpen = false">
      <div class="absolute inset-0 bg-black/30" />
      <aside class="relative z-50 h-full w-full max-w-3xl overflow-auto bg-white p-6 shadow-xl">
        <div class="flex items-start justify-between">
          <h2 class="text-lg font-bold text-gray-900">{{ editingId ? 'Edit site' : 'New site' }}</h2>
          <button type="button" class="text-gray-400 hover:text-gray-700" @click="drawerOpen = false">✕</button>
        </div>

        <div class="mt-4">
          <ScraperSiteForm v-model="draft" />
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

        <div class="mt-6">
          <ScraperRulePreviewWorkbench :rules="draft" :site-id="editingId" />
        </div>
      </aside>
    </div>
  </div>
</template>
