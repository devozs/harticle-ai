<script setup lang="ts">
import { storeToRefs } from 'pinia'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const route = useRoute()
const store = useTrainingStore()
const { monitor, logs, sessions } = storeToRefs(store)

const sessionId = computed(() => String(route.query.id ?? ''))
const busy = ref(false)

// Re-arm polling whenever the session id changes — not just on mount. Navigating
// between attempts (e.g. after Re-run) only swaps the ?id= query, which reuses
// this same page component, so onMounted won't fire again; without this watch the
// poller stays bound to the previous session and the screen/logs never update.
watch(sessionId, (id) => {
  // Polling uses setInterval — client only. The immediate run fires during setup,
  // which on SSR is the server, so guard it (Nuxt warns otherwise and the timer
  // would never fire server-side anyway).
  if (!import.meta.client) return
  store.stopMonitorPolling()
  if (id) {
    store.startMonitorPolling(id)
    // Refresh the list too so the attempt-history panel reflects the new attempt.
    store.fetchSessions()
  }
}, { immediate: true })

onBeforeUnmount(() => store.stopMonitorPolling())

const canStop = computed(() =>
  monitor.value && ['RUNNING', 'ASSIGNED', 'RESUMING'].includes(monitor.value.status))

// Fetch-to-local progress: shown while a model push is in flight or has failed.
const fetchInFlight = computed(() =>
  monitor.value && ['REQUESTED', 'UPLOADING', 'FAILED'].includes(monitor.value.modelFetchStatus))

function mb(bytes?: number): string {
  if (bytes == null) return '—'
  return `${(bytes / 1_048_576).toFixed(1)} MB`
}

// Fraction by bytes when totals are known, else by files; 0 until totals arrive.
const fetchPercent = computed(() => {
  const m = monitor.value
  if (!m) return 0
  if (m.modelFetchBytesTotal) return Math.min(100, Math.round((m.modelFetchBytesDone ?? 0) / m.modelFetchBytesTotal * 100))
  if (m.modelFetchFilesTotal) return Math.min(100, Math.round((m.modelFetchFilesDone ?? 0) / m.modelFetchFilesTotal * 100))
  return 0
})

const refetching = ref(false)
async function refetch(fromScratch: boolean) {
  if (!monitor.value) return
  refetching.value = true
  try {
    await store.fetchModelLocal(monitor.value.id, fromScratch)
  } catch (e) {
    alert(String(e))
  } finally {
    refetching.value = false
  }
}

function fetchStatusText(status?: string): string {
  switch (status) {
    case 'REQUESTED': return 'Queued — waiting for the training box to push'
    case 'UPLOADING': return 'Copying model files to this host'
    case 'FAILED': return 'Fetch failed'
    default: return ''
  }
}

// All attempts in this session's re-run chain (original + every re-run), oldest
// attempt first. Derived from the sessions list: the chain "root" is the
// parentSessionId if this is a re-run, else this session's own id.
const chain = computed(() => {
  const m = monitor.value
  if (!m) return []
  const rootId = m.parentSessionId ?? m.id
  return sessions.value
    .filter(s => s.id === rootId || s.parentSessionId === rootId)
    .sort((a, b) => a.attemptNumber - b.attemptNumber)
})

function attemptBadge(status: string) {
  switch (status) {
    case 'COMPLETED': return 'bg-green-100 text-green-800'
    case 'FAILED': return 'bg-red-100 text-red-800'
    case 'RUNNING':
    case 'ASSIGNED':
    case 'RESUMING': return 'bg-amber-100 text-amber-800'
    default: return 'bg-gray-200 text-gray-700'
  }
}

async function stop() {
  busy.value = true
  try {
    await store.stopSession(sessionId.value)
  } finally {
    busy.value = false
  }
}

async function resume() {
  busy.value = true
  try {
    await store.resumeSession(sessionId.value)
  } finally {
    busy.value = false
  }
}

async function rerun() {
  busy.value = true
  try {
    const created = await store.rerunSession(sessionId.value)
    if (created?.id) await navigateTo(`/admin/models/monitor?id=${created.id}`)
  } catch (e) {
    alert(String(e))
  } finally {
    busy.value = false
  }
}
</script>

<template>
  <div>
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-xl font-bold text-gray-900">
          {{ monitor?.name ?? 'Training monitor' }}
          <span
            v-if="monitor && (monitor.attemptNumber > 1 || monitor.parentSessionId)"
            class="ml-1 align-middle rounded bg-indigo-100 px-1.5 py-0.5 text-xs font-medium text-indigo-700"
          >attempt #{{ monitor.attemptNumber }}</span>
        </h1>
        <p class="mt-1 text-sm text-gray-500">{{ monitor?.baseModel }}</p>
      </div>
      <div class="flex gap-2">
        <button
          v-if="canStop"
          type="button"
          class="rounded-lg border border-amber-400 px-3 py-1.5 text-sm font-medium text-amber-700 hover:bg-amber-50 disabled:opacity-40"
          :disabled="busy"
          @click="stop"
        >
          Stop
        </button>
        <button
          v-if="monitor?.resumable"
          type="button"
          class="rounded-lg border border-cyan-300 px-3 py-1.5 text-sm font-medium text-cyan-800 hover:bg-cyan-50 disabled:opacity-40"
          :disabled="busy"
          title="Continue this run from its last checkpoint"
          @click="resume"
        >
          Resume
        </button>
        <button
          v-if="monitor?.rerunnable"
          type="button"
          class="rounded-lg bg-cyan-700 px-3 py-1.5 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
          :disabled="busy"
          title="Start a fresh attempt with the same config and dataset (keeps this one)"
          @click="rerun"
        >
          Re-run
        </button>
      </div>
    </div>

    <!-- attempt history: travel over previous (failed) attempts of this run -->
    <div v-if="chain.length > 1" class="mt-4 rounded-2xl border border-gray-200 bg-white p-4">
      <div class="text-xs font-semibold text-gray-500">Attempt history</div>
      <div class="mt-2 flex flex-wrap gap-2">
        <NuxtLink
          v-for="a in chain"
          :key="a.id"
          :to="`/admin/models/monitor?id=${a.id}`"
          class="flex items-center gap-1.5 rounded-lg border px-2.5 py-1 text-xs hover:bg-gray-50"
          :class="a.id === monitor?.id ? 'border-cyan-400 ring-1 ring-cyan-200' : 'border-gray-200'"
        >
          <span class="font-medium text-gray-700">#{{ a.attemptNumber }}</span>
          <span class="rounded px-1.5 py-0.5 font-medium" :class="attemptBadge(a.status)">{{ a.status }}</span>
        </NuxtLink>
      </div>
    </div>

    <div v-if="monitor" class="mt-4 grid grid-cols-2 gap-4 sm:grid-cols-4">
      <div class="rounded-2xl border border-gray-200 bg-white p-4">
        <div class="text-xs text-gray-400">Status</div>
        <div class="mt-1 font-semibold text-gray-900">{{ monitor.status }}</div>
      </div>
      <div class="rounded-2xl border border-gray-200 bg-white p-4">
        <div class="text-xs text-gray-400">Progress</div>
        <div class="mt-1 font-semibold text-gray-900">{{ monitor.progressPercent }}%</div>
      </div>
      <div class="rounded-2xl border border-gray-200 bg-white p-4">
        <div class="text-xs text-gray-400">Epoch / Step</div>
        <div class="mt-1 font-semibold text-gray-900">
          {{ monitor.currentEpoch?.toFixed?.(2) ?? '—' }} / {{ monitor.currentStep ?? '—' }}<span v-if="monitor.totalSteps" class="text-gray-400">/{{ monitor.totalSteps }}</span>
        </div>
      </div>
      <div class="rounded-2xl border border-gray-200 bg-white p-4">
        <div class="text-xs text-gray-400">Last loss</div>
        <div class="mt-1 font-semibold text-gray-900">{{ monitor.lastLoss?.toFixed?.(4) ?? '—' }}</div>
      </div>
    </div>

    <div v-if="monitor" class="mt-3 grid grid-cols-1 gap-4 sm:grid-cols-2">
      <div class="rounded-2xl border border-gray-200 bg-white p-4 text-sm">
        <div class="text-xs text-gray-400">Assigned to</div>
        <div class="mt-1 text-gray-900">{{ monitor.assignedResourceName ?? '—' }}</div>
        <div class="mt-2 text-xs text-gray-400">Trained on</div>
        <div class="mt-1 text-gray-900">{{ monitor.reporterName ?? 'All reporters (general model)' }}</div>
        <div class="mt-2 text-xs text-gray-400">Activity</div>
        <div class="mt-1 text-gray-900">
          {{ monitor.secondsSinceActivity != null ? `${monitor.secondsSinceActivity}s ago` : '—' }}
        </div>
      </div>
      <div class="rounded-2xl border border-gray-200 bg-white p-4 text-sm">
        <div class="text-xs text-gray-400">Output model</div>
        <div class="mt-1 break-all text-gray-900">{{ monitor.outputModelRef ?? '—' }}</div>
        <p v-if="monitor.errorMessage" class="mt-2 text-red-600">{{ monitor.errorMessage }}</p>
      </div>
    </div>

    <!-- fetch-to-local progress: live file/byte counts + source → target -->
    <div v-if="monitor && fetchInFlight" class="mt-4 rounded-2xl border p-4 text-sm"
         :class="monitor.modelFetchStatus === 'FAILED' ? 'border-red-200 bg-red-50' : 'border-cyan-200 bg-cyan-50'">
      <div class="flex items-center justify-between">
        <span class="font-semibold" :class="monitor.modelFetchStatus === 'FAILED' ? 'text-red-700' : 'text-cyan-800'">
          Fetch to local — {{ fetchStatusText(monitor.modelFetchStatus) }}
        </span>
        <span v-if="monitor.modelFetchFilesTotal" class="text-xs text-gray-500">
          {{ monitor.modelFetchFilesDone ?? 0 }}/{{ monitor.modelFetchFilesTotal }} files ·
          {{ mb(monitor.modelFetchBytesDone) }}/{{ mb(monitor.modelFetchBytesTotal) }}
        </span>
      </div>
      <div v-if="monitor.modelFetchStatus !== 'FAILED'" class="mt-2 h-1.5 w-full overflow-hidden rounded-full bg-cyan-100">
        <div class="h-full bg-cyan-600 transition-all" :style="{ width: `${fetchPercent}%` }" />
      </div>
      <p class="mt-2 break-all text-xs text-gray-500">
        <span class="text-gray-400">from</span> {{ monitor.modelFetchSource ?? '—' }}
        <span class="text-gray-400">→</span> models/{{ monitor.id }}
      </p>
      <!-- A failed fetch can resume (keep what landed) or restart from scratch. -->
      <div v-if="monitor.modelFetchStatus === 'FAILED'" class="mt-3 flex gap-3">
        <button
          type="button"
          class="rounded-lg bg-cyan-700 px-3 py-1.5 text-xs font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
          :disabled="refetching"
          title="Keep files already copied and re-send only the missing/incomplete ones"
          @click="refetch(false)"
        >{{ refetching ? 'Retrying…' : 'Resume fetch' }}</button>
        <button
          type="button"
          class="rounded-lg border border-gray-300 px-3 py-1.5 text-xs text-gray-600 hover:bg-gray-100 disabled:opacity-40"
          :disabled="refetching"
          title="Discard partial files and re-copy the whole model"
          @click="refetch(true)"
        >From scratch</button>
      </div>
    </div>

    <!-- progress bar -->
    <div v-if="monitor" class="mt-4 h-2 w-full overflow-hidden rounded-full bg-gray-200">
      <div class="h-full bg-cyan-600 transition-all" :style="{ width: `${monitor.progressPercent}%` }" />
    </div>

    <!-- log tail -->
    <div class="mt-4">
      <h2 class="text-sm font-semibold text-gray-700">Logs</h2>
      <pre class="mt-2 h-80 overflow-auto whitespace-pre rounded-2xl bg-gray-900 p-4 font-mono text-xs leading-relaxed text-gray-100">
<template v-for="line in logs" :key="line.seq"><span :class="line.level === 'ERROR' ? 'text-red-400' : line.level === 'WARN' ? 'text-amber-300' : 'text-gray-300'">[{{ line.level }}] {{ line.message }}</span>
</template><span v-if="!logs.length" class="text-gray-500">no log lines yet…</span></pre>
    </div>
  </div>
</template>
