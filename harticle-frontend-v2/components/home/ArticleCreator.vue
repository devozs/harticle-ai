<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { Article } from '~/types/article'
import { articleDisplaySubtitle, articleDisplayTitle } from '~/utils/articleDisplay'

const pendingAsArticle = (pending: Partial<Article>) => pending as Article

const { dir } = useDirection()
const { t } = useI18n()
const store = useHarticleStore()
const { keywords, temperature, isCreating, pendingArticle } = storeToRefs(store)

const emit = defineEmits<{
  create: []
}>()
</script>

<template>
  <section :dir="dir" class="container mx-auto p-5 md:p-3">
    <div v-if="isCreating" class="rounded-2xl bg-white p-4 shadow-md">
      <p class="font-bold text-gray-800">{{ t('creating_article_title') }}</p>
      <p class="text-sm text-gray-600">{{ t('creating_article_subtitle') }}</p>
      <p
        v-if="articleDisplayTitle(pendingAsArticle(pendingArticle))"
        class="mt-3 text-lg font-semibold text-gray-900"
      >
        {{ articleDisplayTitle(pendingAsArticle(pendingArticle)) }}
      </p>
      <p
        v-if="articleDisplaySubtitle(pendingAsArticle(pendingArticle))"
        class="mt-1 text-sm text-gray-600"
      >
        {{ articleDisplaySubtitle(pendingAsArticle(pendingArticle)) }}
      </p>
    </div>

    <div v-else class="relative">
      <input
        v-model="keywords"
        type="search"
        :placeholder="t('search_placeholder')"
        class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-4 pr-10 text-sm text-gray-900 focus:border-cyan-500 focus:ring-cyan-500"
      >
      <button
        type="button"
        class="absolute bottom-2.5 left-2.5 rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
        :disabled="isCreating"
        @click="emit('create')"
      >
        {{ t('submit') }}
      </button>
    </div>

    <div class="mt-6 w-full max-w-md">
      <label class="font-bold text-gray-600">{{ t('slider_title') }}</label>
      <input v-model="temperature" type="range" min="1" max="100" class="h-2 w-full appearance-none bg-cyan-100">
    </div>
  </section>
</template>
