<script setup lang="ts">
import type { FieldResult } from '~/types/scraper'

defineProps<{ fields: FieldResult[] }>()
</script>

<template>
  <table class="w-full border-collapse text-sm">
    <thead>
      <tr class="border-b border-gray-200 text-left text-gray-500">
        <th class="py-2 pr-3 font-medium">Field</th>
        <th class="py-2 pr-3 font-medium">Matched</th>
        <th class="py-2 pr-3 font-medium">Len</th>
        <th class="py-2 pr-3 font-medium">Sample</th>
        <th class="py-2 font-medium">Rule</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="f in fields" :key="f.field" class="border-b border-gray-100 align-top">
        <td class="py-2 pr-3 font-medium text-gray-800">{{ f.field }}</td>
        <td class="py-2 pr-3">
          <span
            class="inline-block rounded px-2 py-0.5 text-xs font-medium"
            :class="f.matched ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'"
          >
            {{ f.matched ? 'yes' : 'no' }}
          </span>
        </td>
        <td class="py-2 pr-3 text-gray-600">{{ f.length }}</td>
        <td class="py-2 pr-3 text-gray-700">
          <span class="line-clamp-2 break-words">{{ f.sample || '—' }}</span>
        </td>
        <td class="py-2">
          <code class="break-all text-xs text-gray-500">{{ f.rule || '—' }}</code>
        </td>
      </tr>
    </tbody>
  </table>
</template>
