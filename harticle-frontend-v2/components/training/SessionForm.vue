<script setup lang="ts">
import type { ComputeResource, ComputeResourceType, TrainingSessionDto } from '~/types/training'

const props = defineProps<{ resources: ComputeResource[] }>()

// v-model:modelValue is the draft DTO.
const model = defineModel<TrainingSessionDto>({ required: true })
// v-model:resourceId is the chosen target resource (advisory — the backend
// claims by type, but we make the admin pick a live box so a session is never
// queued against a compute type with nothing to run it).
const resourceId = defineModel<string | undefined>('resourceId')
// v-model:valid is the overall "ok to queue" flag the page uses to gate Create:
// a live resource is selected AND the base model has been verified to exist.
const valid = defineModel<boolean>('valid')

// A resource is "live" (eligible to be picked) when it heartbeats recently and
// has passed its readiness preflight. BUSY boxes still qualify — the job queues
// (PENDING) and they claim it once free — but OFFLINE/unverified ones don't.
function isLive(r: ComputeResource): boolean {
  const beat = r.lastHeartbeat ? Date.now() - new Date(r.lastHeartbeat).getTime() : Infinity
  return beat < 60_000 && r.readiness === 'READY'
}

// Resources of the currently-selected compute type, live ones first.
const resourcesForType = computed(() =>
  props.resources
    .filter(r => r.type === model.value.requiredType)
    .sort((a, b) => Number(isLive(b)) - Number(isLive(a))))

const liveResourcesForType = computed(() => resourcesForType.value.filter(isLive))

function resourceLabel(r: ComputeResource): string {
  const live = isLive(r)
  const state = !live
    ? (r.readiness !== 'READY' ? r.readiness.toLowerCase() : 'offline')
    : r.status.toLowerCase()
  return `${r.name} — ${state}`
}

// When the compute type changes, drop a selection that no longer matches.
watch(() => model.value.requiredType, () => {
  const sel = props.resources.find(r => r.id === resourceId.value)
  if (!sel || sel.type !== model.value.requiredType) resourceId.value = undefined
})

// --- base-model existence check (HuggingFace Hub, CORS-enabled) -------------
type HfState = 'idle' | 'checking' | 'valid' | 'invalid' | 'error'
const hfState = ref<HfState>('idle')
const hfMessage = ref('')
let hfTimer: ReturnType<typeof setTimeout> | undefined
let hfSeq = 0

async function verifyModel(id: string) {
  const seq = ++hfSeq
  hfState.value = 'checking'
  hfMessage.value = ''
  try {
    await $fetch(`https://huggingface.co/api/models/${id}`)
    if (seq !== hfSeq) return // a newer check superseded this one
    hfState.value = 'valid'
  } catch (e: any) {
    if (seq !== hfSeq) return
    const status = e?.response?.status ?? e?.status
    if (status === 404 || status === 401) {
      hfState.value = 'invalid'
      hfMessage.value = 'No such public model on HuggingFace Hub.'
    } else {
      hfState.value = 'error'
      hfMessage.value = 'Could not reach HuggingFace to verify — check the id or your connection.'
    }
  }
}

// Debounce checks as the admin types; require a re-verify whenever it changes.
watch(() => model.value.baseModel, (id) => {
  if (hfTimer) clearTimeout(hfTimer)
  const trimmed = (id ?? '').trim()
  if (!trimmed) {
    hfState.value = 'idle'
    hfMessage.value = ''
    return
  }
  hfState.value = 'checking'
  hfTimer = setTimeout(() => verifyModel(trimmed), 500)
}, { immediate: true })

const hfBadge = computed(() => {
  switch (hfState.value) {
    case 'checking': return { text: 'Checking…', cls: 'text-gray-500' }
    case 'valid': return { text: '✓ Found on HuggingFace', cls: 'text-green-600' }
    case 'invalid': return { text: '✗ ' + hfMessage.value, cls: 'text-red-600' }
    case 'error': return { text: '⚠ ' + hfMessage.value, cls: 'text-amber-600' }
    default: return { text: '', cls: '' }
  }
})

// Overall readiness, pushed up to the page to gate the Create button.
watchEffect(() => {
  valid.value = !!resourceId.value && hfState.value === 'valid'
})

onBeforeUnmount(() => { if (hfTimer) clearTimeout(hfTimer) })
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
      <span v-if="hfBadge.text" class="text-xs" :class="hfBadge.cls">{{ hfBadge.text }}</span>
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Compute type</span>
      <select v-model="model.requiredType" class="rounded-lg border border-gray-300 px-3 py-2">
        <option value="CUDA">CUDA (GPU)</option>
        <option value="HPU">HPU (Gaudi)</option>
      </select>
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Compute resource</span>
      <select
        v-model="resourceId"
        class="rounded-lg border border-gray-300 px-3 py-2 disabled:bg-gray-100"
        :disabled="!liveResourcesForType.length"
      >
        <option :value="undefined" disabled>
          {{ liveResourcesForType.length ? 'Select a live resource…' : 'No live resource for this type' }}
        </option>
        <option
          v-for="r in resourcesForType"
          :key="r.id"
          :value="r.id"
          :disabled="!isLive(r)"
        >
          {{ resourceLabel(r) }}
        </option>
      </select>
      <span v-if="!liveResourcesForType.length" class="text-xs text-amber-600">
        Register and enroll a {{ model.requiredType }} resource, and wait for it to reach READY, before queueing.
      </span>
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
