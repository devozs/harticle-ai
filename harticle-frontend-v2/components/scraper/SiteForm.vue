<script setup lang="ts">
import type { ContentSource, ParserStrategy, RuleType, ScrapeSiteDto } from '~/types/scraper'

// Two-way bound working copy of the site rule set (used by the edit drawer and
// shared live with the preview workbench so tests reflect unsaved edits).
const model = defineModel<ScrapeSiteDto>({ required: true })

const parserStrategies: ParserStrategy[] = ['GENERIC_REGEX', 'SPORT5', 'WALLA', 'ONE']
const ruleTypes: RuleType[] = ['REGEX', 'CSS']
const contentSources: ContentSource[] = ['REGEX', 'JSON_LD']

const ruleFields: { key: keyof ScrapeSiteDto, label: string, hint?: string }[] = [
  { key: 'articleLinkRule', label: 'Article link rule', hint: 'regex capturing article hrefs on a listing page' },
  { key: 'articleLinkFilter', label: 'Article link filter', hint: 'substring a captured href must contain' },
  { key: 'titleRule', label: 'Title rule' },
  { key: 'subtitleRule', label: 'Subtitle rule' },
  { key: 'contentRule', label: 'Content rule' },
  { key: 'dateRule', label: 'Date rule' },
  { key: 'reporterRule', label: 'Reporter rule' },
]
</script>

<template>
  <div class="space-y-4">
    <div class="grid grid-cols-1 gap-4 sm:grid-cols-2">
      <label class="block">
        <span class="text-xs font-medium text-gray-600">Name</span>
        <input
          v-model="model.name"
          class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
      </label>
      <label class="block">
        <span class="text-xs font-medium text-gray-600">Base URL</span>
        <input
          v-model="model.baseUrl"
          placeholder="https://www.example.co.il"
          class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
      </label>
      <label class="block">
        <span class="text-xs font-medium text-gray-600">Parser strategy</span>
        <select
          v-model="model.parserStrategy"
          class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
          <option v-for="p in parserStrategies" :key="p" :value="p">{{ p }}</option>
        </select>
      </label>
      <label class="block">
        <span class="text-xs font-medium text-gray-600">Rule type</span>
        <select
          v-model="model.ruleType"
          class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
          <option v-for="r in ruleTypes" :key="r" :value="r">{{ r }}</option>
        </select>
      </label>
      <label class="block">
        <span class="text-xs font-medium text-gray-600">Content source</span>
        <span class="ml-1 text-xs text-gray-400">— JSON_LD reads "articleBody"</span>
        <select
          v-model="model.contentSource"
          class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
          <option v-for="c in contentSources" :key="c" :value="c">{{ c }}</option>
        </select>
      </label>
      <label class="block">
        <span class="text-xs font-medium text-gray-600">Max history pages</span>
        <span class="ml-1 text-xs text-gray-400">— blank = global default</span>
        <input
          v-model.number="model.maxHistoryPages"
          type="number"
          min="1"
          placeholder="(default)"
          class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm focus:border-cyan-500 focus:ring-cyan-500"
        >
      </label>
    </div>

    <p class="text-xs text-gray-400">
      Note: parser strategy <code>SPORT5/WALLA/ONE</code> keep per-site link logic;
      <code>GENERIC_REGEX</code> drives everything from the rules below.
      Content source <code>JSON_LD</code> ignores the content rule and reads the
      page's JSON-LD <code>articleBody</code>.
    </p>

    <div class="space-y-3">
      <label v-for="rf in ruleFields" :key="rf.key" class="block">
        <span class="text-xs font-medium text-gray-600">{{ rf.label }}</span>
        <span v-if="rf.hint" class="ml-1 text-xs text-gray-400">— {{ rf.hint }}</span>
        <input
          v-model="(model[rf.key] as string)"
          spellcheck="false"
          class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 font-mono text-xs focus:border-cyan-500 focus:ring-cyan-500"
        >
      </label>

      <label class="block">
        <span class="text-xs font-medium text-gray-600">Listing stop marker</span>
        <span class="ml-1 text-xs text-gray-400">— cut listing here (e.g. class="paging") to skip recommended sections</span>
        <input
          v-model="model.listingStopMarker"
          spellcheck="false"
          class="mt-1 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 font-mono text-xs focus:border-cyan-500 focus:ring-cyan-500"
        >
      </label>
    </div>

    <label class="flex items-center gap-2">
      <input v-model="model.enabled" type="checkbox" class="rounded border-gray-300 text-cyan-600">
      <span class="text-sm text-gray-700">Enabled</span>
    </label>
  </div>
</template>
