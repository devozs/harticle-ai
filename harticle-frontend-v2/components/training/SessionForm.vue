<script setup lang="ts">
import type { TrainingSessionDto } from '~/types/training'

// v-model:modelValue is the draft DTO.
const model = defineModel<TrainingSessionDto>({ required: true })
</script>

<template>
  <div class="grid grid-cols-1 gap-4 sm:grid-cols-2">
    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Name</span>
      <input v-model="model.name" type="text" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="soccer-news-v2">
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Base HF model</span>
      <input v-model="model.baseModel" type="text" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="Norod78/hebrew-gpt_neo-small">
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Compute type</span>
      <select v-model="model.requiredType" class="rounded-lg border border-gray-300 px-3 py-2">
        <option value="CUDA">CUDA (GPU)</option>
        <option value="HPU">HPU (Gaudi)</option>
      </select>
    </label>

    <div class="flex items-end gap-4">
      <label class="flex items-center gap-2 text-sm">
        <input v-model="model.stubMode" type="checkbox" class="h-4 w-4 rounded border-gray-300">
        <span class="text-gray-700">Stub (no-ML dev run)</span>
      </label>
      <label class="flex items-center gap-2 text-sm">
        <input v-model="model.pushToHub" type="checkbox" class="h-4 w-4 rounded border-gray-300">
        <span class="text-gray-700">Push to HF Hub</span>
      </label>
    </div>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Epochs</span>
      <input v-model.number="model.epochs" type="number" min="1" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="3">
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Batch size</span>
      <input v-model.number="model.batchSize" type="number" min="1" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="4">
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Learning rate</span>
      <input v-model.number="model.learningRate" type="number" step="0.00001" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="0.00005">
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Context length</span>
      <input v-model.number="model.contextLength" type="number" min="8" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="128">
    </label>
  </div>
</template>
