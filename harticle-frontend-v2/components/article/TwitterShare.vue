<script setup lang="ts">
const props = defineProps<{
  title: string
  url: string
}>()

const { twitterShareUrl } = useApi()
const href = computed(() => twitterShareUrl(props.title, props.url))
</script>

<template>
  <a :href="href" target="_blank" rel="noopener noreferrer" class="inline-flex">
    <img
      loading="lazy"
      src="https://images.one.co.il/images/msites/2022/03/02/f1122ca799164f8406265daf2332cd5a.png"
      alt="Share on X"
      class="h-6 w-6"
    >
  </a>
</template>
