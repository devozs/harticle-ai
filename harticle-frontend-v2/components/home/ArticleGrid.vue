<script setup lang="ts">
import type { Article } from '~/types/article'
import {
  articleCardBackground,
  articleCardFallbackClass,
  articleDisplaySubtitle,
  articleDisplayTitle,
  hasArticleImage,
} from '~/utils/articleDisplay'
import { formatDateTime } from '~/utils/format'

defineProps<{
  articles: Article[]
}>()

const { dir } = useDirection()
const store = useHarticleStore()
const { articleUrl } = useApi()
const { t } = useI18n()

function templateForIndex(index: number): 'sport6' | 'two' | 'yalla' {
  if (index % 3 === 0) return 'sport6'
  if (index % 2 === 0) return 'two'
  return 'yalla'
}
</script>

<template>
  <section class="container mx-auto p-3 md:p-10">
    <div class="grid grid-cols-1 gap-10 sm:grid-cols-2 md:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4">
      <template v-for="(article, index) in articles" :key="article.id">
        <!-- Sport6 style -->
        <article
          v-if="templateForIndex(index) === 'sport6'"
          :dir="dir"
          class="flex min-h-116 cursor-pointer flex-col bg-cover bg-center text-gray-100 transition duration-500 hover:-translate-y-1"
          :class="[
            hasArticleImage(article) ? 'justify-between' : 'justify-start',
            { [articleCardFallbackClass]: !hasArticleImage(article) },
          ]"
          :style="articleCardBackground(article)"
        >
          <div class="flex items-center justify-between px-5 pt-4">
            <span class="rounded bg-black/80 px-2 py-1 font-bold text-white shadow">SPORT6</span>
            <span class="rounded bg-red-600 px-2 py-1 text-xs font-bold text-white">{{ t('sport6_badge') }}</span>
          </div>

          <div
            v-if="!hasArticleImage(article)"
            class="flex flex-1 flex-col justify-center gap-2 px-5 py-6"
          >
            <h3 class="text-2xl font-extrabold leading-tight text-white drop-shadow-sm">
              {{ articleDisplayTitle(article) }}
            </h3>
            <p v-if="articleDisplaySubtitle(article)" class="line-clamp-3 text-base text-slate-200">
              {{ articleDisplaySubtitle(article) }}
            </p>
          </div>

          <NuxtLink :to="`/harticles/sport6/${article.id}`" @click="store.select(article)">
            <div
              class="p-2 shadow-md"
              :class="hasArticleImage(article) ? 'bg-black/65' : 'bg-black/40'"
            >
              <template v-if="hasArticleImage(article)">
                <h3 class="line-clamp-2 text-lg font-bold">{{ articleDisplayTitle(article) }}</h3>
                <p class="line-clamp-2 text-sm">{{ articleDisplaySubtitle(article) }}</p>
              </template>
              <div class="mt-2 flex items-center justify-between text-xs">
                <span>{{ formatDateTime(article.updatedAt) }}</span>
                <ArticleTwitterShare
                  :title="articleDisplayTitle(article)"
                  :url="articleUrl('sport6', article.id)"
                />
                <span>{{ t('sport6_author') }}</span>
              </div>
            </div>
          </NuxtLink>
        </article>

        <!-- TWO style -->
        <article
          v-else-if="templateForIndex(index) === 'two'"
          :dir="dir"
          class="flex min-h-116 cursor-pointer flex-col justify-between bg-indigo-800 text-gray-100 transition duration-500 hover:-translate-y-1"
        >
          <div class="p-4">
            <h3 class="text-xl font-extrabold leading-snug">{{ articleDisplayTitle(article) }}</h3>
            <p v-if="articleDisplaySubtitle(article)" class="mt-2 line-clamp-3 text-sm text-indigo-100">
              {{ articleDisplaySubtitle(article) }}
            </p>
          </div>
          <img
            v-if="hasArticleImage(article)"
            :src="article.image"
            :alt="articleDisplayTitle(article)"
            class="w-full object-cover p-2"
          >
          <NuxtLink :to="`/harticles/two/${article.id}`" @click="store.select(article)">
            <div class="p-2 shadow-md">
              <div class="mt-2 flex items-center justify-between text-xs">
                <span>{{ formatDateTime(article.updatedAt) }}</span>
                <ArticleTwitterShare
                  :title="articleDisplayTitle(article)"
                  :url="articleUrl('two', article.id)"
                />
                <span>{{ t('two_author') }}</span>
              </div>
            </div>
          </NuxtLink>
        </article>

        <!-- Yalla style -->
        <article
          v-else
          :dir="dir"
          class="flex min-h-116 cursor-pointer flex-col bg-cover bg-center text-gray-100 transition duration-500 hover:-translate-y-1"
          :class="[
            hasArticleImage(article) ? 'justify-between' : 'justify-start',
            { [articleCardFallbackClass]: !hasArticleImage(article) },
          ]"
          :style="articleCardBackground(article)"
        >
          <div class="flex items-center justify-between px-5 pt-4">
            <span class="rounded bg-black/80 px-2 py-1 font-bold text-white shadow">YALLA</span>
            <span class="rounded bg-red-600 px-2 py-1 text-xs font-bold text-white">{{ t('yalla_badge') }}</span>
          </div>

          <div
            v-if="!hasArticleImage(article)"
            class="flex flex-1 flex-col justify-center gap-2 px-5 py-6"
          >
            <h3 class="text-2xl font-extrabold leading-tight text-white drop-shadow-sm">
              {{ articleDisplayTitle(article) }}
            </h3>
            <p v-if="articleDisplaySubtitle(article)" class="line-clamp-3 text-base text-slate-200">
              {{ articleDisplaySubtitle(article) }}
            </p>
          </div>

          <NuxtLink :to="`/harticles/${article.id}`" @click="store.select(article)">
            <div
              class="p-2 shadow-md"
              :class="hasArticleImage(article) ? 'bg-black/40' : 'bg-black/30'"
            >
              <template v-if="hasArticleImage(article)">
                <h3 class="text-lg font-bold">{{ articleDisplayTitle(article) }}</h3>
                <p class="line-clamp-2 text-sm">{{ articleDisplaySubtitle(article) }}</p>
              </template>
              <div class="mt-2 flex items-center justify-between text-xs">
                <span>{{ formatDateTime(article.updatedAt) }}</span>
                <ArticleTwitterShare
                  :title="articleDisplayTitle(article)"
                  :url="articleUrl('yalla', article.id)"
                />
                <span>{{ t('yalla_author') }}</span>
              </div>
            </div>
          </NuxtLink>
        </article>
      </template>
    </div>
  </section>
</template>
