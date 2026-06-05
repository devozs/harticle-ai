<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { InferenceRunDto, InferenceRunSummary } from '~/types/inference'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useInferenceStore()
const trainingStore = useTrainingStore()
const { confirm } = useConfirm()
const { models, runs, current } = storeToRefs(store)
const { resources } = storeToRefs(trainingStore)

const drawerOpen = ref(false)
const draft = ref<InferenceRunDto>(emptyDraft())
const formValid = ref(false)
const saving = ref(false)
const error = ref('')

function emptyDraft(): InferenceRunDto {
  return { sourceSessionId: '', target: '', prompt: '', temperature: 50, maxLength: 512, numReturnSequences: 3 }
}

onMounted(() => {
  store.fetchRuns()
  store.fetchModels()
  trainingStore.fetchResources()
})

// Keep models + resources fresh while the drawer is open so the live resource
// picker and the completed-model list reflect new heartbeats/runs.
let poll: ReturnType<typeof setInterval> | undefined
watch(drawerOpen, (open) => {
  if (open) {
    store.fetchModels()
    trainingStore.fetchResources()
    poll = setInterval(() => trainingStore.fetchResources(), 4000)
  } else if (poll) {
    clearInterval(poll)
    poll = undefined
  }
})
onBeforeUnmount(() => {
  if (poll) clearInterval(poll)
  store.stopRunPolling()
})

function openCreate() {
  draft.value = emptyDraft()
  formValid.value = false
  error.value = ''
  drawerOpen.value = true
}

async function save() {
  error.value = ''
  saving.value = true
  try {
    await store.createRun(draft.value)
    drawerOpen.value = false
  } catch (e) {
    error.value = String(e)
  } finally {
    saving.value = false
  }
}

function view(run: InferenceRunSummary) {
  store.startRunPolling(run.id)
}

function closeResult() {
  store.stopRunPolling()
  store.current = undefined
}

async function remove(run: InferenceRunSummary) {
  const running = ['PENDING', 'ASSIGNED', 'RUNNING'].includes(run.status)
  const ok = await confirm({
    title: 'Delete this inference run?',
    message: running
      ? 'This run is in progress. Deleting it frees its compute resource and discards the result. The trained model itself is not affected.'
      : 'This removes the run and its result. The trained model itself is not affected.',
    confirmLabel: 'Delete',
    tone: 'danger',
  })
  if (!ok) return
  await store.deleteRun(run.id)
}

function statusBadge(status: string) {
  switch (status) {
    case 'RUNNING':
    case 'ASSIGNED': return 'bg-amber-100 text-amber-800'
    case 'COMPLETED': return 'bg-green-100 text-green-800'
    case 'FAILED': return 'bg-red-100 text-red-800'
    default: return 'bg-cyan-100 text-cyan-800'
  }
}

function targetLabel(run: InferenceRunSummary) {
  return run.local ? 'Local (CPU)' : (run.assignedResourceName ?? run.requiredType ?? 'GPU/HPU')
}
</script>

<template>
  <div>
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-xl font-bold text-gray-900">Inference</h1>
        <p class="mt-1 text-sm text-gray-500">Test a trained model against a prompt before releasing it to users.</p>
      </div>
      <button
        type="button"
        class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800"
        @click="openCreate"
      >
        New inference
      </button>
    </div>

    <!-- current run result -->
    <div v-if="current" class="mt-4 rounded-2xl border border-gray-200 bg-white p-4 shadow-sm">
      <div class="flex items-center justify-between">
        <div class="text-sm font-semibold text-gray-700">
          Result
          <span class="ml-2 rounded px-2 py-0.5 text-xs font-medium" :class="statusBadge(current.status)">{{ current.status }}</span>
        </div>
        <button type="button" class="text-xs text-gray-400 hover:text-gray-700" @click="closeResult">close</button>
      </div>
      <p class="mt-2 text-xs text-gray-400">{{ current.baseModel }} · {{ targetLabel(current) }}</p>
      <p class="mt-1 text-sm text-gray-600" dir="auto"><span class="font-medium">Prompt:</span> {{ current.prompt }}</p>

      <div v-if="current.status === 'PENDING' || current.status === 'ASSIGNED' || current.status === 'RUNNING'" class="mt-3 text-sm text-gray-500">
        Generating…
      </div>
      <p v-if="current.errorMessage" class="mt-3 text-sm text-red-600">{{ current.errorMessage }}</p>
      <div v-if="current.outputs?.length" class="mt-3 flex flex-col gap-2">
        <div
          v-for="(out, i) in current.outputs"
          :key="i"
          dir="auto"
          class="whitespace-pre-wrap rounded-lg bg-gray-50 p-3 text-sm text-gray-800"
        >
          <span class="mr-2 text-xs font-semibold text-gray-400">#{{ i + 1 }}</span>{{ out }}
        </div>
      </div>
      <p v-if="current.durationMs != null" class="mt-2 text-xs text-gray-400">took {{ (current.durationMs / 1000).toFixed(1) }}s</p>
    </div>

    <!-- runs history -->
    <div class="mt-4 overflow-hidden rounded-2xl border border-gray-200 bg-white shadow-sm">
      <div class="overflow-x-auto">
        <table class="w-full min-w-[40rem] text-left text-sm">
          <thead class="bg-gray-50 text-xs uppercase text-gray-500">
            <tr>
              <th class="px-4 py-3">Model</th>
              <th class="px-4 py-3">Prompt</th>
              <th class="px-4 py-3">Target</th>
              <th class="px-4 py-3">Status</th>
              <th class="px-4 py-3" />
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr v-for="r in runs" :key="r.id" class="hover:bg-gray-50">
              <td class="px-4 py-3 font-medium text-gray-900">{{ r.baseModel ?? r.modelRef }}</td>
              <td class="px-4 py-3 max-w-xs truncate text-gray-600" dir="auto">{{ r.prompt }}</td>
              <td class="px-4 py-3 text-gray-600">{{ targetLabel(r) }}</td>
              <td class="px-4 py-3">
                <span class="rounded px-2 py-0.5 text-xs font-medium" :class="statusBadge(r.status)">{{ r.status }}</span>
              </td>
              <td class="px-4 py-3 text-right">
                <button type="button" class="text-sm font-medium text-cyan-700 hover:underline" @click="view(r)">View</button>
                <button type="button" class="ml-3 text-sm text-gray-400 hover:text-red-600" @click="remove(r)">Delete</button>
              </td>
            </tr>
            <tr v-if="!runs.length">
              <td colspan="5" class="px-4 py-6 text-center text-sm text-gray-400">No inference runs yet.</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- create drawer -->
    <div v-if="drawerOpen" class="fixed inset-0 z-40 flex justify-end" @click.self="drawerOpen = false">
      <div class="absolute inset-0 bg-black/30" />
      <aside class="relative z-50 h-full w-full max-w-lg overflow-y-auto bg-white p-6 shadow-xl">
        <div class="flex items-start justify-between">
          <h2 class="text-lg font-bold text-gray-900">New inference</h2>
          <button type="button" class="text-gray-400 hover:text-gray-700" @click="drawerOpen = false">✕</button>
        </div>

        <div class="mt-4">
          <InferenceForm
            v-model="draft"
            v-model:valid="formValid"
            :models="models"
            :resources="resources"
          />
        </div>

        <p v-if="error" class="mt-3 text-sm text-red-600">{{ error }}</p>

        <div class="mt-4 flex gap-2">
          <button
            type="button"
            class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
            :disabled="saving || !formValid"
            :title="formValid ? '' : 'Pick a model, a target, and enter a prompt'"
            @click="save"
          >
            {{ saving ? 'Submitting…' : 'Run' }}
          </button>
          <button
            type="button"
            class="rounded-lg border border-gray-300 px-4 py-2 text-sm text-gray-600 hover:bg-gray-100"
            @click="drawerOpen = false"
          >
            Cancel
          </button>
        </div>
      </aside>
    </div>
  </div>
</template>
