<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { TrainingSessionDto, TrainingSessionSummary } from '~/types/training'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useTrainingStore()
const { sessions, resources } = storeToRefs(store)

const drawerOpen = ref(false)
const draft = ref<TrainingSessionDto>(emptyDraft())
const targetResourceId = ref<string | undefined>()
const formValid = ref(false)
const saving = ref(false)
const error = ref('')

// Keep the resource list fresh while the create drawer is open so the live
// resource picker (and its READY badges) reflect heartbeats in near-real-time.
let resourcePoll: ReturnType<typeof setInterval> | undefined
watch(drawerOpen, (open) => {
  if (open) {
    store.fetchResources()
    resourcePoll = setInterval(() => store.fetchResources(), 4000)
  } else if (resourcePoll) {
    clearInterval(resourcePoll)
    resourcePoll = undefined
  }
})
onBeforeUnmount(() => { if (resourcePoll) clearInterval(resourcePoll) })

function emptyDraft(): TrainingSessionDto {
  return {
    name: '',
    baseModel: 'Norod78/hebrew-gpt_neo-small',
    requiredType: 'CUDA',
    stubMode: false,
    pushToHub: false,
    epochs: 3,
    batchSize: 4,
    learningRate: 0.00005,
    contextLength: 128,
  }
}

onMounted(() => store.fetchSessions())

function openCreate() {
  draft.value = emptyDraft()
  targetResourceId.value = undefined
  formValid.value = false
  error.value = ''
  drawerOpen.value = true
}

async function save() {
  error.value = ''
  saving.value = true
  try {
    await store.createSession(draft.value)
    drawerOpen.value = false
  } catch (e) {
    error.value = String(e)
  } finally {
    saving.value = false
  }
}

async function remove(session: TrainingSessionSummary) {
  if (!confirm(`Delete training session "${session.name}"?`)) return
  await store.deleteSession(session.id)
}

// Re-run a failed/stopped session: same model, hyperparams and dataset, as a
// fresh attempt. The original is kept so you can compare attempts.
const rerunning = ref<string | undefined>()
async function rerun(session: TrainingSessionSummary) {
  rerunning.value = session.id
  try {
    const created = await store.rerunSession(session.id)
    if (created?.id) await navigateTo(`/admin/training/monitor?id=${created.id}`)
  } catch (e) {
    alert(String(e))
  } finally {
    rerunning.value = undefined
  }
}

function statusBadge(status: string) {
  switch (status) {
    case 'RUNNING':
    case 'ASSIGNED':
    case 'RESUMING': return 'bg-amber-100 text-amber-800'
    case 'COMPLETED': return 'bg-green-100 text-green-800'
    case 'FAILED': return 'bg-red-100 text-red-800'
    case 'STOPPED':
    case 'STOP_REQUESTED': return 'bg-gray-200 text-gray-700'
    default: return 'bg-cyan-100 text-cyan-800'
  }
}
</script>

<template>
  <div>
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-xl font-bold text-gray-900">Training Sessions</h1>
        <p class="mt-1 text-sm text-gray-500">Fine-tune a HuggingFace model on the scraped corpus.</p>
      </div>
      <button
        type="button"
        class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800"
        @click="openCreate"
      >
        New session
      </button>
    </div>

    <div class="mt-4 overflow-hidden rounded-2xl border border-gray-200 bg-white shadow-sm">
      <table class="w-full text-left text-sm">
        <thead class="bg-gray-50 text-xs uppercase text-gray-500">
          <tr>
            <th class="px-4 py-3">Name</th>
            <th class="px-4 py-3">Base model</th>
            <th class="px-4 py-3">Type</th>
            <th class="px-4 py-3">Status</th>
            <th class="px-4 py-3">Progress</th>
            <th class="px-4 py-3" />
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-100">
          <tr v-for="s in sessions" :key="s.id" class="hover:bg-gray-50">
            <td class="px-4 py-3 font-medium text-gray-900">
              {{ s.name }}<span v-if="s.stubMode" class="ml-1 text-xs text-gray-400">(stub)</span>
              <span
                v-if="s.attemptNumber > 1 || s.parentSessionId"
                class="ml-2 rounded bg-indigo-100 px-1.5 py-0.5 text-xs font-medium text-indigo-700"
                title="Re-run attempt — same config and dataset as the original"
              >#{{ s.attemptNumber }}</span>
            </td>
            <td class="px-4 py-3 text-gray-600">{{ s.baseModel }}</td>
            <td class="px-4 py-3 text-gray-600">{{ s.requiredType }}</td>
            <td class="px-4 py-3">
              <span class="rounded px-2 py-0.5 text-xs font-medium" :class="statusBadge(s.status)">{{ s.status }}</span>
            </td>
            <td class="px-4 py-3 text-gray-600">{{ s.progressPercent }}%</td>
            <td class="px-4 py-3 text-right">
              <NuxtLink :to="`/admin/training/monitor?id=${s.id}`" class="text-sm font-medium text-cyan-700 hover:underline">Monitor</NuxtLink>
              <button
                v-if="s.rerunnable"
                type="button"
                class="ml-3 text-sm font-medium text-cyan-700 hover:underline disabled:opacity-40"
                :disabled="rerunning === s.id"
                title="Start a fresh attempt with the same config and dataset"
                @click="rerun(s)"
              >{{ rerunning === s.id ? 'Re-running…' : 'Re-run' }}</button>
              <button type="button" class="ml-3 text-sm text-gray-400 hover:text-red-600" @click="remove(s)">Delete</button>
            </td>
          </tr>
          <tr v-if="!sessions.length">
            <td colspan="6" class="px-4 py-6 text-center text-sm text-gray-400">No training sessions yet.</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- create drawer -->
    <div v-if="drawerOpen" class="fixed inset-0 z-40 flex justify-end" @click.self="drawerOpen = false">
      <div class="absolute inset-0 bg-black/30" />
      <aside class="relative z-50 h-full w-full max-w-2xl overflow-auto bg-white p-6 shadow-xl">
        <div class="flex items-start justify-between">
          <h2 class="text-lg font-bold text-gray-900">New training session</h2>
          <button type="button" class="text-gray-400 hover:text-gray-700" @click="drawerOpen = false">✕</button>
        </div>

        <div class="mt-4">
          <TrainingSessionForm
            v-model="draft"
            v-model:resource-id="targetResourceId"
            v-model:valid="formValid"
            :resources="resources"
          />
        </div>

        <p v-if="error" class="mt-3 text-sm text-red-600">{{ error }}</p>

        <div class="mt-4 flex gap-2">
          <button
            type="button"
            class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
            :disabled="saving || !formValid"
            :title="formValid ? '' : 'Pick a live compute resource and verify the base model first'"
            @click="save"
          >
            {{ saving ? 'Creating…' : 'Create & queue' }}
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
