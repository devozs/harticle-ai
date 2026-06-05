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

const isLocalTarget = computed(() => model.value.target === LOCAL)

// Reporter picker: a model trained for a reporter generates in that reporter's
// voice. The dropdown is built ONLY from reporters that actually have a model in
// the library, so we never offer a reporter we can't generate for. The sentinel
// '' = the general model(s) trained over all reporters (reporterId null).
const GENERAL = ''
const selectedReporterId = ref<string>(GENERAL)

// LOCAL can only load models whose files are on the management host. When the
// target is Local, hide models that aren't available locally (they must be
// fetched first from the training screen, or run on their GPU/HPU box). For a
// GPU target, hide models that can't run there: ORPHANED (files lost with a removed
// box) and REMOTE_OFFLINE (files on a box whose heartbeat lapsed — can't run until
// it's back). The server rejects both too; filtering keeps the picker to runnables.
const localFilteredModels = computed(() =>
  isLocalTarget.value
    ? props.models.filter(m => m.availableLocal)
    : props.models.filter(m => m.reachability !== 'ORPHANED' && m.reachability !== 'REMOTE_OFFLINE'))

// Distinct reporters present across the available (target-filtered) models.
const reporterOptions = computed(() => {
  const seen = new Map<string, string>()
  for (const m of localFilteredModels.value) {
    if (m.reporterId) seen.set(m.reporterId, m.reporterName || m.reporterId)
  }
  return [...seen.entries()]
    .map(([id, name]) => ({ id, name }))
    .sort((a, b) => a.name.localeCompare(b.name))
})

// Whether any general (all-reporters) model exists for the current target.
const hasGeneralModel = computed(() => localFilteredModels.value.some(m => !m.reporterId))

// Then narrow to the chosen reporter (or the general models when '' is selected).
const selectableModels = computed(() =>
  localFilteredModels.value.filter(m =>
    selectedReporterId.value ? m.reporterId === selectedReporterId.value : !m.reporterId))

// Default the reporter to a real option: prefer the general model, else the first
// reporter that has a model. Avoids the select sitting on a hidden '' entry when
// only per-reporter models exist for the current target.
watchEffect(() => {
  if (selectedReporterId.value === GENERAL && !hasGeneralModel.value && reporterOptions.value.length) {
    selectedReporterId.value = reporterOptions.value[0].id
  }
})

// If switching target/reporter makes the current model invalid, clear the pick.
watch([isLocalTarget, selectedReporterId], () => {
  if (selectedModel.value && !selectableModels.value.some(m => m.sessionId === selectedModel.value!.sessionId)) {
    model.value.sourceSessionId = undefined as unknown as string
  }
})

watchEffect(() => {
  valid.value = !!model.value.sourceSessionId
    && !!model.value.target
    && !!(model.value.prompt && model.value.prompt.trim())
})
</script>

<template>
  <div class="flex flex-col gap-4">
    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Reporter</span>
      <select v-model="selectedReporterId" class="rounded-lg border border-gray-300 px-3 py-2">
        <option v-if="hasGeneralModel" :value="GENERAL">All reporters (general model)</option>
        <option v-for="r in reporterOptions" :key="r.id" :value="r.id">{{ r.name }}</option>
        <option v-if="!hasGeneralModel && !reporterOptions.length" :value="GENERAL" disabled>
          {{ isLocalTarget ? 'No models available locally' : 'No completed models yet' }}
        </option>
      </select>
      <span class="text-xs text-gray-400">
        Pick a reporter to generate in their voice (uses the model trained on their reports), or
        “All reporters” for the general model. Only reporters with a trained model appear.
      </span>
    </label>

    <label class="flex flex-col gap-1 text-sm">
      <span class="font-medium text-gray-700">Trained model</span>
      <select v-model="model.sourceSessionId" class="rounded-lg border border-gray-300 px-3 py-2">
        <option :value="undefined" disabled>
          {{ selectableModels.length
            ? 'Select a completed model…'
            : isLocalTarget ? 'No models available locally — fetch one first' : 'No completed training runs yet' }}
        </option>
        <option v-for="m in selectableModels" :key="m.sessionId" :value="m.sessionId">
          {{ m.name }} — {{ m.baseModel }}
        </option>
      </select>
      <span v-if="selectedModel" class="break-all text-xs text-gray-400">{{ selectedModel.outputModelRef }}</span>
      <span v-if="isLocalTarget" class="text-xs text-gray-400">
        Only models present on this host are shown. To test a model trained on a GPU/HPU box locally,
        fetch it to local from the Training screen first.
      </span>
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

    <label class="flex flex-col gap-1 text-sm">
      <div class="flex items-center justify-between">
        <span class="font-medium text-gray-700">Absurdity</span>
        <span class="text-xs font-semibold text-cyan-700">{{ model.absurdity ?? 50 }}</span>
      </div>
      <input v-model.number="model.absurdity" type="range" min="0" max="100" step="1" class="accent-cyan-700">
      <div class="flex justify-between text-xs text-gray-400">
        <span>tame &amp; accurate</span>
        <span>unhinged</span>
      </div>
      <span class="text-xs text-gray-400">
        One dial for how wild the article gets — it sets the generation temperature and length for you.
      </span>
    </label>

    <details class="rounded-lg border border-gray-200 px-3 py-2 text-sm">
      <summary class="cursor-pointer font-medium text-gray-600">Advanced</summary>
      <p class="mt-1 text-xs text-gray-400">
        Override the raw knobs. Leaving Temperature / Max length blank lets the Absurdity dial drive them.
      </p>
      <div class="mt-3 grid grid-cols-1 gap-4 sm:grid-cols-3">
        <label class="flex flex-col gap-1 text-sm">
          <span class="font-medium text-gray-700">Temperature (0–100)</span>
          <input v-model.number="model.temperature" type="number" min="0" max="100" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="from absurdity">
        </label>
        <label class="flex flex-col gap-1 text-sm">
          <span class="font-medium text-gray-700">Max length</span>
          <input v-model.number="model.maxLength" type="number" min="8" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="from absurdity">
        </label>
        <label class="flex flex-col gap-1 text-sm">
          <span class="font-medium text-gray-700">Samples</span>
          <input v-model.number="model.numReturnSequences" type="number" min="1" max="5" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="3">
        </label>
      </div>
    </details>
  </div>
</template>
