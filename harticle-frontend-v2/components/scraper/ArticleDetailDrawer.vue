<script setup lang="ts">
import type { ScrapedArticle } from '~/types/scraper'

const props = withDefaults(
  defineProps<{ article: ScrapedArticle, dock?: 'side' | 'bottom' }>(),
  { dock: 'side' },
)
const emit = defineEmits<{ close: [] }>()

// Non-modal reading pane (Outlook-style): no dimming backdrop, and the wrapper
// is pointer-events-none so it never covers the article table — clicking a
// different row stays possible and updates this pane live. Only the pane itself
// is interactive.
const wrapperClass = computed(() =>
  props.dock === 'bottom'
    ? 'pointer-events-none fixed inset-0 z-40 flex flex-col justify-end'
    : 'pointer-events-none fixed inset-0 z-40 flex justify-end',
)
const paneClass = computed(() =>
  props.dock === 'bottom'
    ? 'pointer-events-auto relative z-50 max-h-[70vh] w-full overflow-auto border-t border-gray-200 bg-white p-6 shadow-2xl'
    : 'pointer-events-auto relative z-50 h-full w-full max-w-2xl overflow-auto border-l border-gray-200 bg-white p-6 shadow-2xl',
)
</script>

<template>
  <div :class="wrapperClass">
    <aside :class="paneClass">
      <div class="flex items-start justify-between">
        <h2 class="text-lg font-bold text-gray-900">{{ article.title || 'Untitled' }}</h2>
        <button type="button" class="text-gray-400 hover:text-gray-700" @click="emit('close')">✕</button>
      </div>

      <a :href="article.sourceUrl" target="_blank" class="mt-1 block break-all text-xs text-cyan-700 hover:underline">
        {{ article.sourceUrl }}
      </a>

      <dl class="mt-4 space-y-3 text-sm">
        <div>
          <dt class="text-xs font-medium uppercase text-gray-400">Subtitle</dt>
          <dd class="text-gray-700">{{ article.subTitle || '—' }}</dd>
        </div>
        <div class="flex gap-6">
          <div>
            <dt class="text-xs font-medium uppercase text-gray-400">Reporter</dt>
            <dd class="text-gray-700">{{ article.reporterName || article.reporter?.displayName || '—' }}</dd>
          </div>
          <div>
            <dt class="text-xs font-medium uppercase text-gray-400">Published</dt>
            <dd class="text-gray-700">{{ article.publishedDate || '—' }}</dd>
          </div>
        </div>
        <div>
          <dt class="text-xs font-medium uppercase text-gray-400">Content</dt>
          <dd class="whitespace-pre-wrap text-gray-700">{{ article.content || '—' }}</dd>
        </div>
        <div>
          <dt class="text-xs font-medium uppercase text-gray-400">Prompt (LLM)</dt>
          <dd class="whitespace-pre-wrap rounded bg-gray-50 p-2 font-mono text-xs text-gray-600">{{ article.prompt || '—' }}</dd>
        </div>
        <div>
          <dt class="text-xs font-medium uppercase text-gray-400">Completion (LLM)</dt>
          <dd class="whitespace-pre-wrap rounded bg-gray-50 p-2 font-mono text-xs text-gray-600">{{ article.completion || '—' }}</dd>
        </div>
      </dl>
    </aside>
  </div>
</template>
