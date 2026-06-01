<script setup lang="ts">
import type { ScrapeReporterDto } from '~/types/scraper'

const model = defineModel<ScrapeReporterDto>({ required: true })
</script>

<template>
  <div class="grid grid-cols-1 gap-3 sm:grid-cols-2">
    <label class="block">
      <span class="text-xs font-medium text-gray-600">Display name</span>
      <input
        v-model="model.displayName"
        placeholder="מוטי פשכצקי"
        class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
      >
    </label>
    <label class="block">
      <span class="text-xs font-medium text-gray-600">Reporter key</span>
      <input
        v-model="model.reporterKey"
        placeholder="moti_psheca"
        class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
      >
    </label>
    <label class="block sm:col-span-2">
      <span class="text-xs font-medium text-gray-600">Path template</span>
      <span class="ml-1 text-xs text-gray-400">— use <code>{}</code> for the page number</span>
      <input
        v-model="model.pathTemplate"
        spellcheck="false"
        placeholder="/Author/מוטי_פשכצקי?Page={}"
        class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 font-mono text-xs focus:border-cyan-500 focus:ring-cyan-500"
      >
    </label>
    <label class="flex items-center gap-2">
      <input v-model="model.enabled" type="checkbox" class="rounded border-gray-300 text-cyan-600">
      <span class="text-sm text-gray-700">Enabled</span>
    </label>
  </div>
</template>
