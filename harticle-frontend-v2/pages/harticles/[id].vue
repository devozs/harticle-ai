<script setup lang="ts">
import type { Article } from '~/types/article'

const route = useRoute()
const id = computed(() => String(route.params.id))
const store = useHarticleStore()
const { apiBase } = useApi()

const { data: article } = await useFetch<Article>(
  () => `${apiBase.value}/article/${id.value}`,
  { key: `article-yalla-${id.value}` },
)

watch(article, (value) => {
  if (value) store.select(value)
}, { immediate: true })

const articlePath = computed(() => `/harticles/${id.value}`)
</script>

<template>
  <section v-if="article" class="container mx-auto p-5">
    <ArticleMeta :article="article" :path="articlePath" />
    <h1 class="text-3xl font-bold">{{ article.title }}</h1>
    <p class="mt-2 text-xl text-gray-600">{{ article.subTitle }}</p>
    <img :src="article.image" :alt="article.title" class="my-6 w-full max-w-3xl rounded shadow">
    <div v-if="article.content" class="prose max-w-none" v-html="article.content" />
  </section>
</template>
