<script setup lang="ts">
import type { ScrapeRunSummary } from '~/types/scraper'

defineProps<{ summary: ScrapeRunSummary }>()

const stats: { key: keyof ScrapeRunSummary, label: string }[] = [
  { key: 'reportersProcessed', label: 'Reporters' },
  { key: 'pagesFetched', label: 'Pages' },
  { key: 'articlesSaved', label: 'Saved' },
  { key: 'articlesUpdated', label: 'Updated' },
  { key: 'articlesSkipped', label: 'Skipped' },
  { key: 'errors', label: 'Errors' },
]
</script>

<template>
  <div class="rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
    <h3 class="text-sm font-bold text-gray-800">Run summary</h3>
    <div class="mt-3 grid grid-cols-3 gap-3 sm:grid-cols-6">
      <div v-for="s in stats" :key="s.key" class="rounded-lg bg-gray-50 p-3 text-center">
        <div
          class="text-xl font-bold"
          :class="s.key === 'errors' && (summary[s.key] as number) > 0 ? 'text-red-600' : 'text-cyan-800'"
        >
          {{ summary[s.key] }}
        </div>
        <div class="text-xs text-gray-500">{{ s.label }}</div>
      </div>
    </div>
    <ul v-if="summary.messages?.length" class="mt-3 max-h-40 overflow-auto text-xs text-gray-500">
      <li v-for="(m, i) in summary.messages" :key="i" class="border-b border-gray-100 py-1">{{ m }}</li>
    </ul>
  </div>
</template>
