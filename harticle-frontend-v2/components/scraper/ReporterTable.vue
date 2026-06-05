<script setup lang="ts">
import type { ScrapeReporter } from '~/types/scraper'

defineProps<{ reporters: ScrapeReporter[] }>()
const emit = defineEmits<{
  edit: [reporter: ScrapeReporter]
  remove: [reporter: ScrapeReporter]
  runSync: [reporter: ScrapeReporter]
  runAsync: [reporter: ScrapeReporter]
}>()
</script>

<template>
  <div class="overflow-x-auto -mx-4 sm:mx-0">
    <table class="w-full min-w-[40rem] border-collapse text-sm">
    <thead>
      <tr class="border-b border-gray-200 text-left text-gray-500">
        <th class="py-2 pr-3 font-medium">Display name</th>
        <th class="py-2 pr-3 font-medium">Key</th>
        <th class="py-2 pr-3 font-medium">Path template</th>
        <th class="py-2 pr-3 font-medium">Enabled</th>
        <th class="py-2 font-medium">Actions</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="reporter in reporters" :key="reporter.id" class="border-b border-gray-100">
        <td class="py-2 pr-3 font-medium text-gray-800">{{ reporter.displayName }}</td>
        <td class="py-2 pr-3 text-gray-600">{{ reporter.reporterKey }}</td>
        <td class="py-2 pr-3"><code class="break-all text-xs text-gray-500">{{ reporter.pathTemplate }}</code></td>
        <td class="py-2 pr-3">
          <span
            class="inline-block rounded px-2 py-0.5 text-xs font-medium"
            :class="reporter.enabled ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-500'"
          >
            {{ reporter.enabled ? 'yes' : 'no' }}
          </span>
        </td>
        <td class="py-2">
          <div class="flex gap-2">
            <button type="button" class="text-xs font-medium text-cyan-700 hover:underline" @click="emit('edit', reporter)">
              Edit
            </button>
            <button type="button" class="text-xs font-medium text-cyan-700 hover:underline" @click="emit('runSync', reporter)">
              Run (sync)
            </button>
            <button type="button" class="text-xs font-medium text-gray-600 hover:underline" @click="emit('runAsync', reporter)">
              Run (async)
            </button>
            <button type="button" class="text-xs font-medium text-red-600 hover:underline" @click="emit('remove', reporter)">
              Delete
            </button>
          </div>
        </td>
      </tr>
      <tr v-if="!reporters.length">
        <td colspan="5" class="py-6 text-center text-gray-400">No reporters for this filter.</td>
      </tr>
    </tbody>
    </table>
  </div>
</template>
