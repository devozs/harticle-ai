<script setup lang="ts">
import type { ScrapeProgress } from '~/types/scraper'

const props = defineProps<{ progress: ScrapeProgress }>()
const emit = defineEmits<{ stop: [] }>()

// A run is "stuck" if it claims to be running but hasn't touched anything for
// a while (most often: fetches hanging behind the corporate proxy).
const STALL_SECONDS = 30
const stalled = computed(() =>
  props.progress.running
  && (props.progress.secondsSinceActivity ?? 0) >= STALL_SECONDS,
)

const statusLabel = computed(() => {
  if (!props.progress.running) return props.progress.finishedAtEpochMs ? 'Finished' : 'Idle'
  return stalled.value ? 'Possibly stuck' : 'Running'
})

const statusClass = computed(() => {
  if (!props.progress.running) return 'bg-gray-100 text-gray-600'
  return stalled.value ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'
})

const stats = computed(() => [
  { label: 'Reporters', value: props.progress.reportersProcessed },
  { label: 'Pages', value: props.progress.pagesFetched },
  { label: 'Saved', value: props.progress.articlesSaved },
  { label: 'Updated', value: props.progress.articlesUpdated },
  { label: 'Skipped', value: props.progress.articlesSkipped },
  { label: 'Errors', value: props.progress.errors },
])
</script>

<template>
  <div class="rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
    <div class="flex items-center justify-between">
      <h3 class="text-sm font-bold text-gray-800">Live run status</h3>
      <div class="flex items-center gap-2">
        <span class="inline-flex items-center gap-1.5 rounded-full px-3 py-1 text-xs font-medium" :class="statusClass">
          <span
            v-if="progress.running && !stalled"
            class="h-2 w-2 animate-pulse rounded-full bg-green-500"
          />
          {{ statusLabel }}
        </span>
        <button
          v-if="progress.running"
          type="button"
          class="rounded-lg bg-red-600 px-3 py-1 text-xs font-medium text-white hover:bg-red-700 disabled:opacity-50"
          :disabled="progress.cancelRequested"
          @click="emit('stop')"
        >
          {{ progress.cancelRequested ? 'Stopping…' : 'Stop' }}
        </button>
      </div>
    </div>

    <!-- stuck warning: the whole point of this card -->
    <div v-if="stalled" class="mt-3 rounded-lg bg-red-50 p-3 text-sm text-red-800">
      No activity for {{ progress.secondsSinceActivity }}s while running — the backend may be stuck
      (often a blocked outbound fetch / proxy). Last: <code>{{ progress.lastMessage }}</code>
    </div>

    <!-- current activity -->
    <div class="mt-3 text-sm text-gray-600">
      <div><span class="text-gray-400">Phase:</span> {{ progress.phase }}</div>
      <div v-if="progress.currentReporter">
        <span class="text-gray-400">Reporter:</span> {{ progress.currentReporter }}
        <span v-if="progress.currentSite" class="text-gray-400">({{ progress.currentSite }})</span>
      </div>
      <div v-if="progress.currentUrl" class="truncate">
        <span class="text-gray-400">URL:</span>
        <span class="text-cyan-700">{{ progress.currentUrl }}</span>
        <span v-if="progress.currentPage" class="text-gray-400">· page {{ progress.currentPage }}</span>
      </div>
      <div v-if="progress.running">
        <span class="text-gray-400">Last activity:</span> {{ progress.secondsSinceActivity ?? 0 }}s ago
      </div>
    </div>

    <div class="mt-3 grid grid-cols-3 gap-3 sm:grid-cols-6">
      <div v-for="s in stats" :key="s.label" class="rounded-lg bg-gray-50 p-2 text-center">
        <div
          class="text-lg font-bold"
          :class="s.label === 'Errors' && s.value > 0 ? 'text-red-600' : 'text-cyan-800'"
        >
          {{ s.value }}
        </div>
        <div class="text-xs text-gray-500">{{ s.label }}</div>
      </div>
    </div>
  </div>
</template>
