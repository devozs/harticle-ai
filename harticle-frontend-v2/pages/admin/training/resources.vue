<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { ComputeResource, ComputeResourceDto, ComputeResourceType } from '~/types/training'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useTrainingStore()
const { resources } = storeToRefs(store)
const { apiBase } = useApi()

const drawerOpen = ref(false)
const draft = ref<ComputeResourceDto>({ name: '', type: 'CUDA', enabled: true })
const saving = ref(false)
const error = ref('')

// One-time enrollment code shown after issue, with the right install+run snippet.
const codeModal = ref<{ name: string, code: string, type: ComputeResourceType } | undefined>()

// Git source for pip-from-git (repo: devozs/harticle-ai, engine subdir).
const GIT_SRC = 'git+ssh://git@github.com/devozs/harticle-ai.git#subdirectory=harticle-engine'

// CUDA laptop/host: pip install from git + run. HPU/Gaudi: docker run on Habana base.
const enrollSnippet = computed(() => {
  const m = codeModal.value
  if (!m) return ''
  const mgmt = apiBase.value
  if (m.type === 'HPU') {
    return [
      '# On the Gaudi VM (agent image built on Habana base — see harticle-engine/Dockerfile.agent):',
      'docker run --rm --runtime=habana \\',
      '  -e HABANA_VISIBLE_DEVICES=all -e OMPI_MCA_btl_vader_single_copy_mechanism=none \\',
      `  -e ENROLL_CODE=${m.code} \\`,
      `  -e MGMT_URL=${mgmt} \\`,
      '  -e AGENT_TYPE=HPU \\',
      '  ghcr.io/devozs/harticle-agent:gaudi',
    ].join('\n')
  }
  return [
    '# On the GPU box (Python 3.10+, git SSH access to the repo):',
    `pip install 'harticle[training,cuda] @ ${GIT_SRC}'`,
    '',
    `ENROLL_CODE=${m.code} \\`,
    `MGMT_URL=${mgmt} \\`,
    'AGENT_TYPE=CUDA \\',
    'python -m harticle.training',
  ].join('\n')
})

// Light poll so VERIFYING → READY (and heartbeat status) refresh live.
let poll: ReturnType<typeof setInterval> | undefined
onMounted(() => {
  store.fetchResources()
  poll = setInterval(() => store.fetchResources(), 4000)
})
onBeforeUnmount(() => { if (poll) clearInterval(poll) })

function openCreate() {
  draft.value = { name: '', type: 'CUDA', enabled: true }
  error.value = ''
  drawerOpen.value = true
}

async function save() {
  error.value = ''
  saving.value = true
  try {
    await store.saveResource(draft.value)
    drawerOpen.value = false
  } catch (e) {
    error.value = String(e)
  } finally {
    saving.value = false
  }
}

async function enroll(resource: ComputeResource) {
  const res = await store.issueEnrollmentCode(resource.id)
  codeModal.value = { name: resource.name, code: res.enrollmentCode, type: resource.type }
}

async function reverify(resource: ComputeResource) {
  try {
    await store.reverifyResource(resource.id)
  } catch (e) {
    alert(String(e))
  }
}

async function remove(resource: ComputeResource) {
  if (!confirm(`Delete compute resource "${resource.name}"?`)) return
  await store.deleteResource(resource.id)
}

function copySnippet() {
  if (import.meta.client && navigator.clipboard) {
    navigator.clipboard.writeText(enrollSnippet.value)
  }
}
</script>

<template>
  <div>
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-xl font-bold text-gray-900">Compute Resources</h1>
        <p class="mt-1 text-sm text-gray-500">
          Register GPU/HPU machines. Each runs the agent and connects outbound — works behind NAT/proxy.
        </p>
      </div>
      <button
        type="button"
        class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800"
        @click="openCreate"
      >
        New resource
      </button>
    </div>

    <div class="mt-4 grid grid-cols-1 gap-4 lg:grid-cols-2">
      <TrainingResourceCard
        v-for="r in resources"
        :key="r.id"
        :resource="r"
        @enroll="enroll"
        @reverify="reverify"
        @remove="remove"
      />
      <p v-if="!resources.length" class="text-sm text-gray-400">No compute resources yet.</p>
    </div>

    <!-- create drawer -->
    <div v-if="drawerOpen" class="fixed inset-0 z-40 flex justify-end" @click.self="drawerOpen = false">
      <div class="absolute inset-0 bg-black/30" />
      <aside class="relative z-50 h-full w-full max-w-lg overflow-auto bg-white p-6 shadow-xl">
        <div class="flex items-start justify-between">
          <h2 class="text-lg font-bold text-gray-900">New compute resource</h2>
          <button type="button" class="text-gray-400 hover:text-gray-700" @click="drawerOpen = false">✕</button>
        </div>

        <div class="mt-4 flex flex-col gap-4">
          <label class="flex flex-col gap-1 text-sm">
            <span class="font-medium text-gray-700">Name</span>
            <input v-model="draft.name" type="text" class="rounded-lg border border-gray-300 px-3 py-2" placeholder="my-laptop-gpu">
          </label>
          <label class="flex flex-col gap-1 text-sm">
            <span class="font-medium text-gray-700">Type</span>
            <select v-model="draft.type" class="rounded-lg border border-gray-300 px-3 py-2">
              <option value="CUDA">CUDA (GPU)</option>
              <option value="HPU">HPU (Gaudi)</option>
            </select>
          </label>
        </div>

        <p v-if="error" class="mt-3 text-sm text-red-600">{{ error }}</p>

        <div class="mt-4 flex gap-2">
          <button
            type="button"
            class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800 disabled:opacity-40"
            :disabled="saving"
            @click="save"
          >
            {{ saving ? 'Saving…' : 'Create' }}
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

    <!-- enrollment-code modal (shown once) -->
    <div v-if="codeModal" class="fixed inset-0 z-50 flex items-center justify-center p-4" @click.self="codeModal = undefined">
      <div class="absolute inset-0 bg-black/40" />
      <div class="relative z-50 w-full max-w-2xl rounded-2xl bg-white p-6 shadow-xl">
        <h2 class="text-lg font-bold text-gray-900">Enroll {{ codeModal.name }} ({{ codeModal.type }})</h2>
        <p class="mt-1 text-sm text-gray-500">
          The code is shown <span class="font-medium">once</span>. On the box, install the agent then
          run it with the code — it connects outbound, so no inbound access is needed.
        </p>
        <pre class="mt-3 overflow-auto rounded-lg bg-gray-900 p-4 text-xs leading-relaxed text-green-300">{{ enrollSnippet }}</pre>
        <p class="mt-2 text-xs text-gray-400">
          On first run the agent enrolls (redeeming this code for a saved bearer token), runs its
          readiness preflight, then waits for jobs. The token is cached, so restarts don't re-enroll.
        </p>
        <div class="mt-4 flex justify-end gap-2">
          <button
            type="button"
            class="rounded-lg border border-gray-300 px-4 py-2 text-sm text-gray-600 hover:bg-gray-100"
            @click="copySnippet"
          >
            Copy
          </button>
          <button
            type="button"
            class="rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800"
            @click="codeModal = undefined"
          >
            Done
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
