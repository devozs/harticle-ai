<script setup lang="ts">
import type { ScrapeReporterDto } from '~/types/scraper'

// Bulk-add reporters by pasting a JSON array (same shape as scrape-cli.sh's file).
const emit = defineEmits<{ submit: [dtos: ScrapeReporterDto[]] }>()

const example = `[
  { "reporterKey": "new_guy", "displayName": "New Guy", "pathTemplate": "/Author/New_Guy?Page={}", "enabled": true }
]`

const text = ref('')
const error = ref('')

function submit() {
  error.value = ''
  let parsed: unknown
  try {
    parsed = JSON.parse(text.value)
  } catch {
    error.value = 'Invalid JSON'
    return
  }
  if (!Array.isArray(parsed)) {
    error.value = 'Expected a JSON array of reporters'
    return
  }
  const missing = parsed.find((r: ScrapeReporterDto) => !r.reporterKey || !r.pathTemplate)
  if (missing) {
    error.value = 'Each reporter needs at least reporterKey and pathTemplate'
    return
  }
  emit('submit', parsed as ScrapeReporterDto[])
  text.value = ''
}
</script>

<template>
  <div>
    <p class="text-xs text-gray-500">Paste a JSON array of reporters. They all attach to the selected site.</p>
    <textarea
      v-model="text"
      rows="6"
      spellcheck="false"
      :placeholder="example"
      class="mt-2 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 font-mono text-xs focus:border-cyan-500 focus:ring-cyan-500"
    />
    <p v-if="error" class="mt-1 text-sm text-red-600">{{ error }}</p>
    <button
      type="button"
      class="mt-2 rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800"
      @click="submit"
    >
      Bulk add
    </button>
  </div>
</template>
