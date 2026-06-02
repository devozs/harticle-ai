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

// Repo + pip-from-git source (repo: devozs/harticle-ai, engine subdir).
const REPO_SSH = 'git@github.com:devozs/harticle-ai.git'
const GIT_SRC = 'git+ssh://git@github.com/devozs/harticle-ai.git#subdirectory=harticle-engine'

// CUDA laptop/host: pip install from git + run. HPU/Gaudi: clone → build the
// Habana-based agent image → run. The Gaudi base image MUST match the VM's
// SynapseAI driver (check `hl-smi`), so the snippet builds locally on the box
// rather than pulling a possibly-mismatched published image.
const enrollSnippet = computed(() => {
  const m = codeModal.value
  if (!m) return ''
  const mgmt = apiBase.value
  if (m.type === 'HPU') {
    return [
      '# On the Gaudi VM. The Dockerfile.agent lives in harticle-engine/, so you',
      '# must clone the repo and cd into it first (this avoids the common',
      '# "open Dockerfile.agent: no such file or directory" error).',
      `git clone ${REPO_SSH}    # skip if already cloned`,
      'cd harticle-ai/harticle-engine',
      '',
      '# Match <synapse>/<pytorch> to your driver — run `hl-smi` to see the',
      '# SynapseAI version, then pick the matching tag from vault.habana.ai.',
      'docker build -f Dockerfile.agent \\',
      '  --build-arg BASE_IMAGE=vault.habana.ai/gaudi-docker/<synapse>/ubuntu22.04/habanalabs/pytorch-installer-<pytorch>:latest \\',
      '  --build-arg EXTRAS=training,gaudi -t harticle-agent:gaudi .',
      '',
      '# Requires the Habana container runtime registered in Docker',
      '# (`docker info | grep -i runtime` should list "habana").',
      'docker run --rm --runtime=habana \\',
      '  -e HABANA_VISIBLE_DEVICES=all -e OMPI_MCA_btl_vader_single_copy_mechanism=none \\',
      `  -e ENROLL_CODE=${m.code} \\`,
      `  -e MGMT_URL=${mgmt} \\`,
      '  -e AGENT_TYPE=HPU \\',
      '  harticle-agent:gaudi',
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

// Individually-copyable bits, for when the user already has the agent set up and
// only needs the new code (or to point it at a different management URL).
const enrollCodeVar = computed(() =>
  codeModal.value ? `ENROLL_CODE=${codeModal.value.code}` : '')
const mgmtUrlVar = computed(() => `MGMT_URL=${apiBase.value}`)

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

function copy(text: string) {
  if (import.meta.client && navigator.clipboard) {
    navigator.clipboard.writeText(text)
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

        <!-- Quick-copy individual bits, for an already-configured box that just
             needs the new code or a different management URL. -->
        <div class="mt-3 grid grid-cols-1 gap-2 sm:grid-cols-2">
          <div>
            <label class="text-xs font-medium text-gray-500">Enrollment code</label>
            <div class="mt-1 flex">
              <input
                :value="enrollCodeVar"
                readonly
                class="w-full rounded-l-lg border border-gray-300 bg-gray-50 px-3 py-1.5 font-mono text-xs text-gray-800"
                @focus="(e) => (e.target as HTMLInputElement).select()"
              >
              <button
                type="button"
                class="rounded-r-lg border border-l-0 border-gray-300 px-3 py-1.5 text-xs text-cyan-800 hover:bg-cyan-50"
                @click="copy(enrollCodeVar)"
              >
                Copy
              </button>
            </div>
          </div>
          <div>
            <label class="text-xs font-medium text-gray-500">Target management URL</label>
            <div class="mt-1 flex">
              <input
                :value="mgmtUrlVar"
                readonly
                class="w-full rounded-l-lg border border-gray-300 bg-gray-50 px-3 py-1.5 font-mono text-xs text-gray-800"
                @focus="(e) => (e.target as HTMLInputElement).select()"
              >
              <button
                type="button"
                class="rounded-r-lg border border-l-0 border-gray-300 px-3 py-1.5 text-xs text-cyan-800 hover:bg-cyan-50"
                @click="copy(mgmtUrlVar)"
              >
                Copy
              </button>
            </div>
          </div>
        </div>

        <p class="mt-2 text-xs text-gray-400">
          On first run the agent enrolls (redeeming this code for a saved bearer token), runs its
          readiness preflight, then waits for jobs. The token is cached, so restarts don't re-enroll.
        </p>
        <div class="mt-4 flex justify-end gap-2">
          <button
            type="button"
            class="rounded-lg border border-gray-300 px-4 py-2 text-sm text-gray-600 hover:bg-gray-100"
            @click="copy(enrollSnippet)"
          >
            Copy all
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
