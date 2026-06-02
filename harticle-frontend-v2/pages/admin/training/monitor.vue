<script setup lang="ts">
import { storeToRefs } from 'pinia'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const route = useRoute()
const store = useTrainingStore()
const { monitor, logs } = storeToRefs(store)

const sessionId = computed(() => String(route.query.id ?? ''))
const busy = ref(false)

onMounted(() => {
  if (sessionId.value) store.startMonitorPolling(sessionId.value)
})
onBeforeUnmount(() => store.stopMonitorPolling())

const canStop = computed(() =>
  monitor.value && ['RUNNING', 'ASSIGNED', 'RESUMING'].includes(monitor.value.status))

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
</script>

<template>
  <div>
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-xl font-bold text-gray-900">{{ monitor?.name ?? 'Training monitor' }}</h1>
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
          class="rounded-lg bg-cyan-700 px-3 py-1.5 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
          :disabled="busy"
          @click="resume"
        >
          Resume
        </button>
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

    <!-- progress bar -->
    <div v-if="monitor" class="mt-4 h-2 w-full overflow-hidden rounded-full bg-gray-200">
      <div class="h-full bg-cyan-600 transition-all" :style="{ width: `${monitor.progressPercent}%` }" />
    </div>

    <!-- log tail -->
    <div class="mt-4">
      <h2 class="text-sm font-semibold text-gray-700">Logs</h2>
      <pre class="mt-2 h-80 overflow-auto rounded-2xl bg-gray-900 p-4 text-xs text-gray-100">
<template v-for="line in logs" :key="line.seq"><span :class="line.level === 'ERROR' ? 'text-red-400' : line.level === 'WARN' ? 'text-amber-300' : 'text-gray-300'">[{{ line.level }}] {{ line.message }}</span>
</template><span v-if="!logs.length" class="text-gray-500">no log lines yet…</span></pre>
    </div>
  </div>
</template>
