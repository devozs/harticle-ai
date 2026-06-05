<script setup lang="ts">
// Global confirmation modal. Mounted once (in the admin layout); driven by the
// shared state from useConfirm(). Replaces window.confirm with a styled dialog
// that supports a danger tone and Esc/backdrop to cancel.
const { state, resolve } = useConfirm()

function onKeydown(e: KeyboardEvent) {
  if (!state.open) return
  if (e.key === 'Escape') resolve(false)
  if (e.key === 'Enter') resolve(true)
}

onMounted(() => window.addEventListener('keydown', onKeydown))
onBeforeUnmount(() => window.removeEventListener('keydown', onKeydown))
</script>

<template>
  <Teleport to="body">
    <div
      v-if="state.open"
      class="fixed inset-0 z-[60] flex items-center justify-center p-4"
      role="dialog"
      aria-modal="true"
    >
      <div class="absolute inset-0 bg-black/40" @click="resolve(false)" />

      <div class="relative z-10 w-full max-w-md rounded-2xl bg-white p-6 shadow-xl">
        <h2 class="text-lg font-bold text-gray-900">{{ state.title }}</h2>
        <p v-if="state.message" class="mt-2 text-sm text-gray-600" dir="auto">{{ state.message }}</p>

        <div class="mt-6 flex justify-end gap-2">
          <button
            type="button"
            class="rounded-lg border border-gray-300 px-4 py-2 text-sm text-gray-600 hover:bg-gray-100"
            @click="resolve(false)"
          >
            {{ state.cancelLabel }}
          </button>
          <button
            type="button"
            class="rounded-lg px-4 py-2 text-sm font-medium text-white"
            :class="state.tone === 'danger' ? 'bg-red-600 hover:bg-red-700' : 'bg-cyan-700 hover:bg-cyan-800'"
            @click="resolve(true)"
          >
            {{ state.confirmLabel }}
          </button>
        </div>
      </div>
    </div>
  </Teleport>
</template>
