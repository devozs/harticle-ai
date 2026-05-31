<script setup lang="ts">
import type { Article } from '~/types/article'
import { formatDateShort } from '~/utils/format'

const props = defineProps<{
  article: Article
  articlePath: string
}>()

const { dir } = useDirection()
const { articleUrl } = useApi()
const shareUrl = computed(() => articleUrl('sport6', props.article.id))
</script>

<template>
  <ArticleMeta :article="article" :path="articlePath" />

  <section class="container mx-auto p-5 lg:mt-3">
    <article :dir="dir" class="article-sport6">
      <h1 class="text-3xl font-bold text-gray-900">{{ article.title }}</h1>
      <h2 class="text-xl text-gray-600">{{ article.subTitle }}</h2>

      <div class="mt-4 flex items-center gap-3">
        <img
          src="https://www.sport5.co.il/Sip_Storage/FILES/2/Black137X104/1130372.jpg"
          alt="SPORT6"
          class="h-14 w-14 rounded-full object-cover"
        >
        <div class="text-sm text-gray-600">
          <span class="font-semibold">SPORT6 מערכת</span>
          <span class="mx-2">·</span>
          <time>{{ formatDateShort(article.updatedAt) }}</time>
          <div class="mt-2">
            <ArticleTwitterShare :title="article.title" :url="shareUrl" />
          </div>
        </div>
      </div>

      <figure class="my-6">
        <img :src="`${article.image}?width=700`" :alt="article.title" class="w-full max-w-4xl rounded shadow">
      </figure>

      <div
        v-if="article.content"
        class="article-body prose max-w-none text-lg"
        v-html="article.content"
      />
    </article>
  </section>
</template>

<style scoped>
.article-sport6 {
  direction: rtl;
  text-align: right;
}
</style>
