<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { TrainingSessionDto, TrainingSessionSummary } from '~/types/training'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useTrainingStore()
const scraperStore = useScraperStore()
const { confirm } = useConfirm()
const { sessions, resources } = storeToRefs(store)
// Reporters power the per-reporter ("dedicated model") training scope picker.
const { reporters } = storeToRefs(scraperStore)

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

// While any model fetch is in flight (REQUESTED/UPLOADING), poll the session list
// so its status badge advances to AVAILABLE on its own without a manual refresh.
let fetchPoll: ReturnType<typeof setInterval> | undefined
const anyFetchInFlight = computed(() =>
  store.sessions.some(s => s.modelFetchStatus === 'REQUESTED' || s.modelFetchStatus === 'UPLOADING'))
watch(anyFetchInFlight, (inFlight) => {
  if (inFlight && !fetchPoll) {
    fetchPoll = setInterval(() => store.fetchSessions(), 4000)
  } else if (!inFlight && fetchPoll) {
    clearInterval(fetchPoll)
    fetchPoll = undefined
  }
})
onBeforeUnmount(() => { if (fetchPoll) clearInterval(fetchPoll) })

function emptyDraft(): TrainingSessionDto {
  return {
    name: '',
    baseModel: 'Norod78/hebrew-gpt_neo-small',
    requiredType: 'CUDA',
    stubMode: false,
    pushToHub: false,
    autoFetchLocal: true,
    epochs: 3,
    batchSize: 4,
    learningRate: 0.00005,
    contextLength: 128,
  }
}

onMounted(() => {
  store.fetchSessions()
  scraperStore.fetchReporters()
})

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
  const running = ['ASSIGNED', 'RUNNING', 'RESUMING', 'STOP_REQUESTED'].includes(session.status)
  const ok = await confirm({
    title: `Delete training session "${session.name}"?`,
    message: running
      ? 'This session is in progress. Deleting it will stop the run on its compute resource and remove its dataset, checkpoints, and trained model. This cannot be undone.'
      : 'This removes its dataset, checkpoints, trained model, and logs. This cannot be undone.',
    confirmLabel: 'Delete',
    tone: 'danger',
  })
  if (!ok) return
  await store.deleteSession(session.id)
}

// Re-run a failed/stopped session: same model, hyperparams and dataset, as a
// fresh attempt. The original is kept so you can compare attempts.
const rerunning = ref<string | undefined>()
async function rerun(session: TrainingSessionSummary) {
  rerunning.value = session.id
  try {
    const created = await store.rerunSession(session.id)
    if (created?.id) await navigateTo(`/admin/models/monitor?id=${created.id}`)
  } catch (e) {
    alert(String(e))
  } finally {
    rerunning.value = undefined
  }
}

// Fetch a remotely-trained model to the management host so it can be tested on
// LOCAL CPU. The agent pushes on its next heartbeat; the list polls so the
// status badge advances REQUESTED → UPLOADING → AVAILABLE on its own.
const fetching = ref<string | undefined>()
async function fetchLocal(session: TrainingSessionSummary, fromScratch = false) {
  fetching.value = session.id
  try {
    await store.fetchModelLocal(session.id, fromScratch)
  } catch (e) {
    alert(String(e))
  } finally {
    fetching.value = undefined
  }
}

// A model is fetchable when it's COMPLETED, not already local, and its box can still
// serve the files: ORPHANED (box removed) and REMOTE_OFFLINE (box not heartbeating)
// both show a status note instead of a fetch action.
function canFetch(s: TrainingSessionSummary): boolean {
  return s.status === 'COMPLETED' && !!s.outputModelRef && !s.modelAvailableLocal
    && s.modelReachability !== 'ORPHANED' && s.modelReachability !== 'REMOTE_OFFLINE'
}

// Label for the single fetch button (the FAILED case is handled separately in the
// template with explicit resume / from-scratch actions).
function fetchLabel(s: TrainingSessionSummary): string | undefined {
  if (!canFetch(s)) return undefined
  if (s.modelFetchStatus === 'REQUESTED') return 'Fetch queued…'
  if (s.modelFetchStatus === 'UPLOADING') return 'Fetching…'
  if (s.modelFetchStatus === 'FAILED') return undefined  // see the two-button template branch
  return 'Fetch to local'
}

// Compact fetch-to-local progress for the list row, e.g. "Fetching 3/8 · 40/210 MB".
function fetchProgressText(s: TrainingSessionSummary): string | undefined {
  if (s.modelFetchStatus !== 'UPLOADING') return undefined
  const mb = (b?: number) => b == null ? '?' : `${(b / 1_048_576).toFixed(0)}`
  const files = s.modelFetchFilesTotal ? `${s.modelFetchFilesDone ?? 0}/${s.modelFetchFilesTotal}` : ''
  const bytes = s.modelFetchBytesTotal ? ` · ${mb(s.modelFetchBytesDone)}/${mb(s.modelFetchBytesTotal)} MB` : ''
  return files ? `Fetching ${files}${bytes}` : 'Fetching…'
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
      <div class="overflow-x-auto">
      <table class="w-full min-w-[40rem] text-left text-sm">
        <thead class="bg-gray-50 text-xs uppercase text-gray-500">
          <tr>
            <th class="px-4 py-3">Name</th>
            <th class="px-4 py-3">Base model</th>
            <th class="px-4 py-3">Trained on</th>
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
            <td class="px-4 py-3 text-gray-600">
              <span v-if="s.reporterName" class="rounded bg-violet-100 px-1.5 py-0.5 text-xs font-medium text-violet-700">{{ s.reporterName }}</span>
              <span v-else class="text-xs text-gray-400">All reporters</span>
            </td>
            <td class="px-4 py-3 text-gray-600">{{ s.requiredType }}</td>
            <td class="px-4 py-3">
              <span class="rounded px-2 py-0.5 text-xs font-medium" :class="statusBadge(s.status)">{{ s.status }}</span>
            </td>
            <td class="px-4 py-3 text-gray-600">{{ s.progressPercent }}%</td>
            <td class="px-4 py-3 text-right">
              <NuxtLink :to="`/admin/models/monitor?id=${s.id}`" class="text-sm font-medium text-cyan-700 hover:underline">Monitor</NuxtLink>
              <button
                v-if="s.rerunnable"
                type="button"
                class="ml-3 text-sm font-medium text-cyan-700 hover:underline disabled:opacity-40"
                :disabled="rerunning === s.id"
                title="Start a fresh attempt with the same config and dataset"
                @click="rerun(s)"
              >{{ rerunning === s.id ? 'Re-running…' : 'Re-run' }}</button>
              <!-- While uploading, show live file/byte progress instead of a button. -->
              <span
                v-if="fetchProgressText(s)"
                class="ml-3 text-xs font-medium text-cyan-700"
                title="Copying the model's files to this host"
              >{{ fetchProgressText(s) }}</span>
              <!-- A failed fetch offers two retries: resume (keep what landed, re-send
                   the rest) or from scratch (wipe + re-upload everything). -->
              <template
                v-else-if="canFetch(s) && s.modelFetchStatus === 'FAILED'"
              >
                <button
                  type="button"
                  class="ml-3 text-sm font-medium text-cyan-700 hover:underline disabled:opacity-40"
                  :disabled="fetching === s.id"
                  title="Resume: keep files already copied and re-send only the missing/incomplete ones"
                  @click="fetchLocal(s, false)"
                >{{ fetching === s.id ? 'Fetching…' : 'Resume fetch' }}</button>
                <button
                  type="button"
                  class="ml-2 text-xs text-gray-400 hover:text-cyan-700 disabled:opacity-40"
                  :disabled="fetching === s.id"
                  title="From scratch: discard partial files and re-copy the whole model"
                  @click="fetchLocal(s, true)"
                >from scratch</button>
              </template>
              <button
                v-else-if="fetchLabel(s)"
                type="button"
                class="ml-3 text-sm font-medium text-cyan-700 hover:underline disabled:opacity-40"
                :disabled="fetching === s.id || s.modelFetchStatus === 'REQUESTED' || s.modelFetchStatus === 'UPLOADING'"
                title="Copy this model's files to this host so it can be tested on Local (CPU)"
                @click="fetchLocal(s)"
              >{{ fetching === s.id ? 'Fetching…' : fetchLabel(s) }}</button>
              <span
                v-else-if="s.status === 'COMPLETED' && s.modelReachability === 'ORPHANED'"
                class="ml-3 text-xs font-medium text-red-700"
                title="The training box was removed; this model's files are lost and it can no longer be run or fetched. Re-train it."
              >Model lost — training box removed</span>
              <span
                v-else-if="s.status === 'COMPLETED' && s.modelReachability === 'REMOTE_OFFLINE'"
                class="ml-3 text-xs font-medium text-amber-700"
                title="The training box that holds this model is offline (heartbeat failing). Bring it back online and the model can be fetched to local — the files are not lost."
              >Training box offline — bring it online to fetch</span>
              <span
                v-else-if="s.status === 'COMPLETED' && s.modelAvailableLocal"
                class="ml-3 text-xs text-green-700"
                title="Model files are on this host — testable on Local (CPU)"
              >✓ Local</span>
              <button type="button" class="ml-3 text-sm text-gray-400 hover:text-red-600" @click="remove(s)">Delete</button>
            </td>
          </tr>
          <tr v-if="!sessions.length">
            <td colspan="7" class="px-4 py-6 text-center text-sm text-gray-400">No training sessions yet.</td>
          </tr>
        </tbody>
      </table>
      </div>
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
            :reporters="reporters"
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
