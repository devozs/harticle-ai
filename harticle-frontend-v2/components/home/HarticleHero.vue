<script setup lang="ts">
const { dir } = useDirection()
const { t } = useI18n()
</script>

<template>
  <section class="container mx-auto p-5 lg:mt-3">
    <div
      class="mb-1 flex h-12 w-12 items-center justify-center rounded-xl bg-gradient-to-br from-cyan-400 to-cyan-900"
    >
      <span class="text-2xl font-bold text-white">H</span>
    </div>
    <a
      class="mb-3 inline-block text-sm font-semibold uppercase tracking-wide text-cyan-600"
      href="https://devozs.com"
      target="_blank"
      rel="noopener noreferrer"
    >
      {{ t('github') }}
    </a>
    <h1 class="text-4xl font-extrabold tracking-tight text-gray-900 sm:text-5xl lg:text-6xl">
      {{ t('heading') }}
    </h1>
    <p :dir="dir" class="mt-2 max-w-5xl text-lg font-extrabold text-gray-700 sm:text-2xl">
      {{ t('sub_heading') }}
    </p>
    <p :dir="dir" class="mt-4 text-gray-600">
      {{ t('disclaimer') }}
    </p>
  </section>
</template>
