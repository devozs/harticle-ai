<script setup lang="ts">
import type { ComputeResource } from '~/types/training'
import type { InferenceModelOption, InferenceRunDto } from '~/types/inference'

const props = defineProps<{
  models: InferenceModelOption[]
  resources: ComputeResource[]
}>()

// v-model:modelValue is the draft DTO; v-model:valid gates the submit button.
const model = defineModel<InferenceRunDto>({ required: true })
const valid = defineModel<boolean>('valid')

const LOCAL = 'LOCAL'

// A resource is "live" (eligible) when it heartbeats recently and has passed its
// readiness preflight. Mirrors SessionForm's isLive. BUSY boxes still qualify —
// the run queues (PENDING) and they claim it once free.
function isLive(r: ComputeResource): boolean {
  const beat = r.lastHeartbeat ? Date.now() - new Date(r.lastHeartbeat).getTime() : Infinity
  return beat < 60_000 && r.readiness === 'READY'
}

// Live GPU/HPU resources across BOTH types — a trained model can run on either,
// so unlike training we don't filter by a single requiredType. Live first.
const liveResources = computed(() =>
  props.resources
    .filter(isLive)
    .sort((a, b) => a.name.localeCompare(b.name)))

function resourceLabel(r: ComputeResource): string {
  return `${r.name} (${r.type}) — ${r.status.toLowerCase()}`
}

const selectedModel = computed(() =>
  props.models.find(m => m.sessionId === model.value.sourceSessionId))

watchEffect(() => {
  valid.value = !!model.value.sourceSessionId
    && !!model.value.target
    && !!(model.value.prompt && model.value.prompt.trim())
})
</script>

<template>
  <div class="flex flex-col gap-4">
    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Trained model</span>
      <select v-model="model.sourceSessionId" class="rounded-lg border border-gray-300 px-3 py-2">
        <option :value="undefined" disabled>
          {{ models.length ? 'Select a completed model…' : 'No completed training runs yet' }}
        </option>
        <option v-for="m in models" :key="m.sessionId" :value="m.sessionId">
          {{ m.name }} — {{ m.baseModel }}
        </option>
      </select>
      <span v-if="selectedModel" class="break-all text-xs text-gray-400">{{ selectedModel.outputModelRef }}</span>
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Run on</span>
      <select v-model="model.target" class="rounded-lg border border-gray-300 px-3 py-2">
        <option :value="undefined" disabled>Select where to run…</option>
        <option :value="LOCAL">Local (CPU, this deployment)</option>
        <option v-for="r in liveResources" :key="r.id" :value="r.id">{{ resourceLabel(r) }}</option>
      </select>
      <span class="text-xs text-gray-400">
        Local runs on the deployment CPU (good for small models). GPU/HPU resources run via the agent.
      </span>
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Prompt</span>
      <textarea
        v-model="model.prompt"
        rows="4"
        dir="auto"
        class="rounded-lg border border-gray-300 px-3 py-2"
        placeholder="מכבי חיפה שוב מנצחת אחרי שער בדקות הסיום"
      />
    </label>

    <div class="grid grid-cols-1 gap-4 sm:grid-cols-3">
      <label class="flex flex-col gap-1 text-sm">
        <span class="font-medium text-gray-700">Temperature (0–100)</span>
        <input v-model.number="model.temperature" type="number" min="0" max="100" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="50">
      </label>
      <label class="flex flex-col gap-1 text-sm">
        <span class="font-medium text-gray-700">Max length</span>
        <input v-model.number="model.maxLength" type="number" min="8" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="512">
      </label>
      <label class="flex flex-col gap-1 text-sm">
        <span class="font-medium text-gray-700">Samples</span>
        <input v-model.number="model.numReturnSequences" type="number" min="1" max="5" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="3">
      </label>
    </div>
  </div>
</template>
