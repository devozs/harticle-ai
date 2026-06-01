<script setup lang="ts">
import { storeToRefs } from 'pinia'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useScraperStore()
const { sites, reporters } = storeToRefs(store)

onMounted(async () => {
  await Promise.all([store.fetchSites(), store.fetchReporters()])
})

const cards = computed(() => [
  { label: 'Sites', value: sites.value.length, to: '/admin/scraper/sites' },
  { label: 'Reporters', value: reporters.value.length, to: '/admin/scraper/reporters' },
  { label: 'Run & Results', value: '→', to: '/admin/scraper/results' },
])
</script>

<template>
  <div>
    <h1 class="text-xl font-bold text-gray-900">Dashboard</h1>
    <p class="mt-1 text-sm text-gray-500">Manage scraper sites, reporters, extraction rules and runs.</p>

    <div class="mt-6 grid grid-cols-1 gap-4 sm:grid-cols-3">
      <NuxtLink
        v-for="card in cards"
        :key="card.label"
        :to="card.to"
        class="rounded-2xl border border-gray-200 bg-white p-5 shadow-sm hover:border-cyan-300"
      >
        <div class="text-3xl font-bold text-cyan-800">{{ card.value }}</div>
        <div class="mt-1 text-sm text-gray-500">{{ card.label }}</div>
      </NuxtLink>
    </div>
  </div>
</template>
