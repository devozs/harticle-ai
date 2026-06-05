<script setup lang="ts">
import type { ScrapeSite } from '~/types/scraper'

defineProps<{ sites: ScrapeSite[] }>()
const emit = defineEmits<{
  edit: [site: ScrapeSite]
  remove: [site: ScrapeSite]
  run: [site: ScrapeSite]
}>()
</script>

<template>
  <div class="overflow-x-auto -mx-4 sm:mx-0">
    <table class="w-full min-w-[40rem] border-collapse text-sm">
    <thead>
      <tr class="border-b border-gray-200 text-left text-gray-500">
        <th class="py-2 pr-3 font-medium">Name</th>
        <th class="py-2 pr-3 font-medium">Base URL</th>
        <th class="py-2 pr-3 font-medium">Strategy</th>
        <th class="py-2 pr-3 font-medium">Enabled</th>
        <th class="py-2 font-medium">Actions</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="site in sites" :key="site.id" class="border-b border-gray-100">
        <td class="py-2 pr-3 font-medium text-gray-800">{{ site.name }}</td>
        <td class="py-2 pr-3 text-cyan-700">{{ site.baseUrl }}</td>
        <td class="py-2 pr-3 text-gray-600">{{ site.parserStrategy }}</td>
        <td class="py-2 pr-3">
          <span
            class="inline-block rounded px-2 py-0.5 text-xs font-medium"
            :class="site.enabled ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-500'"
          >
            {{ site.enabled ? 'yes' : 'no' }}
          </span>
        </td>
        <td class="py-2">
          <div class="flex gap-2">
            <button type="button" class="text-xs font-medium text-cyan-700 hover:underline" @click="emit('edit', site)">
              Edit rules
            </button>
            <button type="button" class="text-xs font-medium text-gray-600 hover:underline" @click="emit('run', site)">
              Run site
            </button>
            <button type="button" class="text-xs font-medium text-red-600 hover:underline" @click="emit('remove', site)">
              Delete
            </button>
          </div>
        </td>
      </tr>
      <tr v-if="!sites.length">
        <td colspan="5" class="py-6 text-center text-gray-400">No sites configured.</td>
      </tr>
    </tbody>
    </table>
  </div>
</template>
