<script setup lang="ts">
import type { Article } from '~/types/article'
import { formatDateShort } from '~/utils/format'

const props = defineProps<{
  article: Article
  articlePath: string
}>()

const { dir } = useDirection()
const { articleUrl } = useApi()
const shareUrl = computed(() => articleUrl('two', props.article.id))
</script>

<template>
  <ArticleMeta :article="article" :path="articlePath" />

  <section class="container mx-auto p-5 lg:mt-3">
    <article :dir="dir" class="article-two">
      <header>
        <h1 class="article-main-title text-4xl font-bold text-red-700">
          {{ article.title }}
        </h1>
        <h2 class="article-sub-title text-2xl font-bold text-gray-500">
          {{ article.subTitle }}
        </h2>
      </header>

      <div class="article-credit my-4 flex flex-wrap items-center gap-2 text-gray-600">
        <ArticleTwitterShare :title="article.title" :url="shareUrl" />
        <span>מערכת TWO</span>
        <time>{{ formatDateShort(article.updatedAt) }}</time>
      </div>

      <figure class="my-6">
        <img :src="`${article.image}?width=700`" :alt="article.title" class="w-full max-w-3xl rounded">
      </figure>

      <div
        v-if="article.content"
        class="article-body prose max-w-none text-xl leading-relaxed"
        v-html="article.content"
      />
    </article>
  </section>
</template>

<style scoped>
.article-two {
  direction: rtl;
  text-align: right;
}
</style>
