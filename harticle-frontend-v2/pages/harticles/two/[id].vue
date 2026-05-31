<script setup lang="ts">
import type { Article } from '~/types/article'

const route = useRoute()
const id = computed(() => String(route.params.id))
const store = useHarticleStore()
const { apiBase } = useApi()

const { data: article } = await useFetch<Article>(
  () => `${apiBase.value}/article/${id.value}`,
  { key: `article-two-${id.value}` },
)

watch(article, (value) => {
  if (value) store.select(value)
}, { immediate: true })

const articlePath = computed(() => `/harticles/two/${id.value}`)
</script>

<template>
  <ArticleDetailTwo
    v-if="article"
    :article="article"
    :article-path="articlePath"
  />
</template>
