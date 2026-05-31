<script setup lang="ts">
import type { Article } from '~/types/article'

const props = defineProps<{
  article: Article
  path: string
}>()

const { siteUrl } = useApi()
const image = computed(() => `${props.article.image}?t=${Date.now()}`)
const url = computed(() => `${siteUrl.value}${props.path}`)
</script>

<template>
  <Head>
    <Title>{{ article.title }}</Title>
    <Meta name="description" :content="article.subTitle" />
    <Meta name="twitter:card" content="summary_large_image" />
    <Meta name="twitter:site" content="@OziShemesh" />
    <Meta name="twitter:title" :content="article.title" />
    <Meta name="twitter:description" :content="article.subTitle" />
    <Meta name="twitter:image" :content="image" />
    <Meta property="og:title" :content="article.title" />
    <Meta property="og:description" :content="article.subTitle" />
    <Meta property="og:image" :content="image" />
    <Meta property="og:url" :content="url" />
    <Meta property="og:type" content="article" />
  </Head>
</template>
