<script setup lang="ts">
import type {
  ArticlePreviewResult,
  ListingPreviewResult,
  PreviewRequest,
  ScrapeSiteDto,
} from '~/types/scraper'

// Live rule tester. Operates on the current (possibly unsaved) rule set passed
// in as `rules`, sending them as inline overrides to the preview endpoints so
// you can test BEFORE saving. siteId is optional context for the backend.
const props = defineProps<{
  rules: ScrapeSiteDto
  siteId?: string
}>()

const { previewArticle, previewListing } = useScraperApi()

const mode = ref<'article' | 'listing'>('article')
const url = ref('')
const loading = ref(false)
const error = ref('')
const articleResult = ref<ArticlePreviewResult>()
const listingResult = ref<ListingPreviewResult>()

function buildRequest(): PreviewRequest {
  return {
    siteId: props.siteId,
    url: url.value,
    baseUrl: props.rules.baseUrl,
    parserStrategy: props.rules.parserStrategy,
    articleLinkRule: props.rules.articleLinkRule,
    articleLinkFilter: props.rules.articleLinkFilter,
    titleRule: props.rules.titleRule,
    subtitleRule: props.rules.subtitleRule,
    contentRule: props.rules.contentRule,
    dateRule: props.rules.dateRule,
    reporterRule: props.rules.reporterRule,
  }
}

async function runTest() {
  if (!url.value) {
    error.value = 'Enter a URL to test'
    return
  }
  error.value = ''
  loading.value = true
  articleResult.value = undefined
  listingResult.value = undefined
  try {
    if (mode.value === 'article') {
      articleResult.value = await previewArticle(buildRequest())
    } else {
      listingResult.value = await previewListing(buildRequest())
    }
  } catch (e) {
    error.value = String(e)
    console.error(e)
  } finally {
    loading.value = false
  }
}

const verdict = computed(() => articleResult.value || listingResult.value)
</script>

<template>
  <div class="rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
    <h3 class="text-sm font-bold text-gray-800">Live preview (no save)</h3>
    <p class="mt-1 text-xs text-gray-500">
      Tests the rules currently in the form against a live URL via the dry-run API.
      Nothing is persisted.
    </p>

    <div class="mt-3 flex gap-2">
      <select
        v-model="mode"
        class="rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
      >
        <option value="article">Article</option>
        <option value="listing">Listing</option>
      </select>
      <input
        v-model="url"
        :placeholder="mode === 'article' ? 'https://…/some-article' : 'https://…/Author/…?Page=1'"
        class="flex-1 rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        @keyup.enter="runTest"
      >
      <button
        type="button"
        class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
        :disabled="loading"
        @click="runTest"
      >
        {{ loading ? 'Testing…' : 'Test' }}
      </button>
    </div>

    <p v-if="error" class="mt-2 text-sm text-red-600">{{ error }}</p>

    <!-- verdict banner -->
    <div
      v-if="verdict"
      class="mt-4 rounded-lg p-3 text-sm"
      :class="verdict.success ? 'bg-green-50 text-green-800' : 'bg-amber-50 text-amber-800'"
    >
      <div class="font-medium">{{ verdict.verdict }}</div>
      <div class="mt-1 text-xs opacity-70">
        fetchOk: {{ verdict.fetchOk }} · htmlLength: {{ verdict.htmlLength }}
      </div>
    </div>

    <!-- article: per-field results -->
    <div v-if="articleResult" class="mt-3">
      <ScraperFieldResultTable :fields="articleResult.fields" />
    </div>

    <!-- listing: link count + sample links -->
    <div v-if="listingResult" class="mt-3 text-sm">
      <p class="text-gray-700">
        <span class="font-medium">{{ listingResult.linkCount }}</span> link(s) found
        <code class="ml-2 text-xs text-gray-500">{{ listingResult.linkRule }}</code>
      </p>
      <ul class="mt-2 space-y-1">
        <li
          v-for="link in listingResult.sampleLinks"
          :key="link"
          class="break-all text-xs text-cyan-700"
        >
          {{ link }}
        </li>
      </ul>
    </div>
  </div>
</template>
