<script setup lang="ts">
import type { ComputeResource } from '~/types/training'

const props = defineProps<{ resource: ComputeResource }>()
defineEmits<{
  (e: 'enroll', resource: ComputeResource): void
  (e: 'remove', resource: ComputeResource): void
  (e: 'reverify', resource: ComputeResource): void
  (e: 'rename', resource: ComputeResource): void
}>()

// Treat a heartbeat older than ~60s as offline, regardless of stored status.
const online = computed(() => {
  if (!props.resource.lastHeartbeat) return false
  const last = new Date(props.resource.lastHeartbeat).getTime()
  return Date.now() - last < 60_000
})

const statusColor = computed(() => {
  switch (props.resource.status) {
    case 'IDLE': return 'bg-green-500'
    case 'BUSY': return 'bg-amber-500'
    case 'ERROR': return 'bg-red-500'
    default: return 'bg-gray-300'
  }
})

const readinessBadge = computed(() => {
  switch (props.resource.readiness) {
    case 'READY': return 'bg-green-100 text-green-800'
    case 'VERIFYING': return 'bg-amber-100 text-amber-800'
    case 'FAILED': return 'bg-red-100 text-red-800'
    default: return 'bg-gray-100 text-gray-600'
  }
})

// The agent reports specs as a free-form JSON string (deviceCount/devices[]/
// deviceName, shape varies by enroll vs. preflight). Parse defensively and
// surface the accelerator card count + a device name when present.
const caps = computed<Record<string, any>>(() => {
  try {
    return props.resource.capabilities ? JSON.parse(props.resource.capabilities) : {}
  } catch {
    return {}
  }
})

const cardCount = computed<number | undefined>(() => {
  const c = caps.value
  if (typeof c.deviceCount === 'number') return c.deviceCount
  if (Array.isArray(c.devices)) return c.devices.length
  return undefined
})

const deviceName = computed<string | undefined>(() => {
  const c = caps.value
  return c.deviceName ?? (Array.isArray(c.devices) ? c.devices[0]?.name : undefined)
})

</script>

<template>
  <div class="rounded-2xl border border-gray-200 bg-white p-5 shadow-sm">
    <div class="flex items-start justify-between">
      <div>
        <div class="flex items-center gap-2">
          <span class="inline-block h-2.5 w-2.5 rounded-full" :class="online ? statusColor : 'bg-gray-300'" />
          <span class="font-semibold" :class="resource.name ? 'text-gray-900' : 'italic text-gray-400'">{{ resource.name || 'Unnamed' }}</span>
          <span class="rounded bg-gray-100 px-2 py-0.5 text-xs font-medium text-gray-600">{{ resource.type }}</span>
          <span
            v-if="cardCount !== undefined"
            class="rounded bg-indigo-100 px-2 py-0.5 text-xs font-medium text-indigo-800"
            :title="deviceName ?? ''"
          >{{ cardCount }} {{ resource.type === 'HPU' ? 'HPU' : 'GPU' }}{{ cardCount === 1 ? '' : 's' }}</span>
        </div>
        <div class="mt-1 flex items-center gap-2 text-sm text-gray-500">
          <span>{{ resource.status }}<template v-if="!resource.enrolled"> · not enrolled</template></span>
          <span
            class="rounded px-2 py-0.5 text-xs font-medium"
            :class="readinessBadge"
            :title="resource.readinessDetail ?? ''"
          >{{ resource.readiness }}</span>
        </div>
      </div>
      <div class="flex flex-wrap justify-end gap-2">
        <button
          type="button"
          class="rounded-lg border border-cyan-300 px-2.5 py-1 text-xs font-medium text-cyan-800 hover:bg-cyan-50"
          @click="$emit('enroll', resource)"
        >
          {{ resource.enrolled ? 'Re-issue code' : 'Enrollment code' }}
        </button>
        <button
          type="button"
          class="rounded-lg border border-gray-300 px-2.5 py-1 text-xs text-gray-600 hover:bg-gray-100"
          title="Rename this resource"
          @click="$emit('rename', resource)"
        >
          Rename
        </button>
        <button
          type="button"
          class="rounded-lg border border-gray-300 px-2.5 py-1 text-xs text-gray-600 hover:bg-gray-100 disabled:opacity-40"
          :disabled="resource.status === 'BUSY'"
          :title="resource.status === 'BUSY' ? 'cannot re-verify a busy resource' : 'run the readiness preflight again'"
          @click="$emit('reverify', resource)"
        >
          Re-verify
        </button>
        <button
          type="button"
          class="rounded-lg border border-gray-300 px-2.5 py-1 text-xs text-gray-500 hover:bg-gray-100"
          @click="$emit('remove', resource)"
        >
          Delete
        </button>
      </div>
    </div>

    <p v-if="resource.readiness === 'FAILED' && resource.readinessDetail" class="mt-2 text-xs text-red-600">
      {{ resource.readinessDetail }}
    </p>

    <dl class="mt-3 grid grid-cols-2 gap-x-4 gap-y-1 text-xs text-gray-500">
      <div>
        <dt class="text-gray-400">Last heartbeat</dt>
        <dd>{{ resource.lastHeartbeat ? new Date(resource.lastHeartbeat).toLocaleString() : '—' }}</dd>
      </div>
      <div>
        <dt class="text-gray-400">Current session</dt>
        <dd class="truncate">{{ resource.currentSessionId ?? '—' }}</dd>
      </div>
      <div v-if="cardCount !== undefined">
        <dt class="text-gray-400">Accelerators</dt>
        <dd class="truncate" :title="deviceName ?? ''">
          {{ cardCount }}× {{ deviceName ?? resource.type }}
        </dd>
      </div>
    </dl>
  </div>
</template>
