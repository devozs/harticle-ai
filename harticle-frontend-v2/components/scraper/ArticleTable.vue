<script setup lang="ts">
import dayjs from 'dayjs'
import customParseFormat from 'dayjs/plugin/customParseFormat'
import type { ScrapedArticle } from '~/types/scraper'
import { formatDateTime } from '~/utils/format'

dayjs.extend(customParseFormat)

const props = defineProps<{ articles: ScrapedArticle[], selectedId?: string }>()
const emit = defineEmits<{ open: [article: ScrapedArticle] }>()

// Display helpers (reporter name can come from the extracted field or the relation).
const reporterOf = (a: ScrapedArticle) => a.reporterName || a.reporter?.displayName || '—'
const siteOf = (a: ScrapedArticle) => a.site?.name || '—'

// Timestamp helpers for date sorting. publishedDate is rendered as DD.MM.YY,
// scrapedAt is an ISO string; rows without a value sort last (NaN).
const publishedTime = (a: ScrapedArticle) =>
  a.publishedDate ? dayjs(a.publishedDate, ['DD.MM.YY', 'DD.MM.YYYY', 'YYYY-MM-DD']).valueOf() : NaN
const scrapedTime = (a: ScrapedArticle) => (a.scrapedAt ? dayjs(a.scrapedAt).valueOf() : NaN)

// --- filter + sort on Reporter / Site / Published / Scraped columns --------
const filterReporter = ref('')
const filterSite = ref('')
type SortKey = 'reporter' | 'site' | 'published' | 'scraped'
const sortKey = ref<SortKey | null>(null)
const sortAsc = ref(true)

// Distinct values for the filter dropdowns, derived from the current data.
const reporterOptions = computed(() =>
  [...new Set(props.articles.map(reporterOf))].sort((a, b) => a.localeCompare(b)),
)
const siteOptions = computed(() =>
  [...new Set(props.articles.map(siteOf))].sort((a, b) => a.localeCompare(b)),
)

function toggleSort(key: SortKey) {
  if (sortKey.value === key) {
    sortAsc.value = !sortAsc.value
  } else {
    sortKey.value = key
    sortAsc.value = true
  }
}
function sortIcon(key: SortKey) {
  if (sortKey.value !== key) return '↕'
  return sortAsc.value ? '↑' : '↓'
}

const displayed = computed(() => {
  let rows = props.articles
  if (filterReporter.value) rows = rows.filter(a => reporterOf(a) === filterReporter.value)
  if (filterSite.value) rows = rows.filter(a => siteOf(a) === filterSite.value)
  if (sortKey.value) {
    const key = sortKey.value
    rows = [...rows].sort((a, b) => {
      let cmp: number
      if (key === 'reporter' || key === 'site') {
        const get = key === 'reporter' ? reporterOf : siteOf
        cmp = get(a).localeCompare(get(b))
      } else {
        const get = key === 'published' ? publishedTime : scrapedTime
        const ta = get(a)
        const tb = get(b)
        // Missing/unparseable dates (NaN) always sort to the bottom.
        if (Number.isNaN(ta) && Number.isNaN(tb)) cmp = 0
        else if (Number.isNaN(ta)) return 1
        else if (Number.isNaN(tb)) return -1
        else cmp = ta - tb
      }
      return sortAsc.value ? cmp : -cmp
    })
  }
  return rows
})
</script>

<template>
  <div class="overflow-x-auto -mx-4 sm:mx-0">
    <table class="w-full min-w-[40rem] border-collapse text-sm">
    <thead>
      <tr class="border-b border-gray-200 text-left text-gray-500">
        <th class="py-2 pr-3 font-medium">Title</th>
        <th class="py-2 pr-3 font-medium">
          <button type="button" class="font-medium hover:text-gray-800" @click="toggleSort('reporter')">
            Reporter <span class="text-gray-400">{{ sortIcon('reporter') }}</span>
          </button>
          <select
            v-model="filterReporter"
            class="mt-1 block w-full rounded border border-gray-200 bg-gray-50 p-1 text-xs font-normal focus:border-cyan-500 focus:ring-cyan-500"
            @click.stop
          >
            <option value="">All</option>
            <option v-for="r in reporterOptions" :key="r" :value="r">{{ r }}</option>
          </select>
        </th>
        <th class="py-2 pr-3 font-medium">
          <button type="button" class="font-medium hover:text-gray-800" @click="toggleSort('site')">
            Site <span class="text-gray-400">{{ sortIcon('site') }}</span>
          </button>
          <select
            v-model="filterSite"
            class="mt-1 block w-full rounded border border-gray-200 bg-gray-50 p-1 text-xs font-normal focus:border-cyan-500 focus:ring-cyan-500"
            @click.stop
          >
            <option value="">All</option>
            <option v-for="s in siteOptions" :key="s" :value="s">{{ s }}</option>
          </select>
        </th>
        <th class="py-2 pr-3 font-medium align-top">
          <button type="button" class="font-medium hover:text-gray-800" @click="toggleSort('published')">
            Published <span class="text-gray-400">{{ sortIcon('published') }}</span>
          </button>
        </th>
        <th class="py-2 font-medium align-top">
          <button type="button" class="font-medium hover:text-gray-800" @click="toggleSort('scraped')">
            Scraped <span class="text-gray-400">{{ sortIcon('scraped') }}</span>
          </button>
        </th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="article in displayed"
        :key="article.id"
        class="cursor-pointer border-b border-gray-100 hover:bg-gray-50"
        :class="{ 'bg-cyan-50': article.id === selectedId }"
        @click="emit('open', article)"
      >
        <td class="py-2 pr-3 font-medium text-gray-800">
          <span class="line-clamp-1">{{ article.title || '—' }}</span>
        </td>
        <td class="py-2 pr-3 text-gray-600">{{ reporterOf(article) }}</td>
        <td class="py-2 pr-3 text-gray-600">{{ siteOf(article) }}</td>
        <td class="py-2 pr-3 text-gray-500">{{ article.publishedDate || '—' }}</td>
        <td class="py-2 text-gray-500">{{ formatDateTime(article.scrapedAt) }}</td>
      </tr>
      <tr v-if="!displayed.length">
        <td colspan="5" class="py-6 text-center text-gray-400">
          {{ articles.length ? 'No articles match the filter.' : 'No scraped articles yet.' }}
        </td>
      </tr>
    </tbody>
    </table>
  </div>
</template>
