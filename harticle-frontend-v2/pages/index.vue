<script setup lang="ts">
import { storeToRefs } from 'pinia'

const store = useHarticleStore()
const { completed } = storeToRefs(store)

onMounted(() => {
  store.fetchAll()
  store.wait()
})

onUnmounted(() => {
  store.clearWait()
})

function createArticle() {
  store.create()
}
</script>

<template>
  <div>
    <HomeHarticleHero />
    <HomeArticleCreator @create="createArticle" />
    <HomeArticleGrid :articles="completed" />
  </div>
</template>
