<script setup lang="ts">
import { storeToRefs } from 'pinia'
import type { ComputeResource, ComputeResourceDto, ComputeResourceType } from '~/types/training'

definePageMeta({ layout: 'admin', middleware: 'admin' })

const store = useTrainingStore()
const { confirm } = useConfirm()
const { resources, sessions } = storeToRefs(store)
const { apiBase } = useApi()

// Which session (if any) each box is currently uploading the model for, keyed by
// resource id. A fetch-to-local runs concurrently with a compute job, so we show
// it on the card without disturbing the BUSY accelerator status.
const pushingByResource = computed<Record<string, typeof sessions.value[number]>>(() => {
  const map: Record<string, typeof sessions.value[number]> = {}
  for (const s of sessions.value) {
    if (s.modelFetchStatus === 'UPLOADING' && s.assignedResourceId) {
      map[s.assignedResourceId] = s
    }
  }
  return map
})

const drawerOpen = ref(false)
const draft = ref<ComputeResourceDto>({ name: '', type: 'CUDA', enabled: true })
const saving = ref(false)
const error = ref('')
// When set, the drawer renames an existing resource (type is fixed); else it creates one.
const editingId = ref<string | undefined>()
const nameValid = computed(() => !!draft.value.name && draft.value.name.trim().length > 0)

// One-time enrollment code shown after issue, with the right install+run snippet.
const codeModal = ref<{ name: string, code: string, type: ComputeResourceType } | undefined>()

// Management URL the agent should dial. EDITABLE and remembered across visits
// (localStorage): the FE's own apiBase is the browser-side address (localhost in
// dev) which a remote Gaudi VM can't reach — the box needs the management VM's
// LAN address+port (e.g. http://10.111.56.26/api). The FE can't know that LAN IP
// on its own, so the admin sets it once and it sticks. Snippet + sanity check track it.
const mgmtUrl = useLocalStorage('enroll.mgmtUrl', '')

// Standalone GPU/HPU agent repo (project-neutral, reused across projects).
const REPO_SSH = 'git@github.com:devozs/gpu-agent.git'
const GIT_SRC = 'git+ssh://git@github.com/devozs/gpu-agent.git'

// CUDA laptop/host: pip install from git + run. HPU/Gaudi: clone → run the
// bundled setup-agent.sh (bare metal — the SynapseAI userspace must match the
// host driver exactly, which a container can break with synStatus=26 at device
// init) → install the agent into the Habana venv → run.
//
// Ordering matters: bootstrap.sh runs FIRST. It writes /etc/devozs-gpu-agent.env
// (the single source of truth for MGMT_URL / AGENT_TYPE / ENROLL_CODE) and only
// then sanity-checks that this box can actually reach the management URL — so the
// reachability test runs against the real configured URL, and every later step
// (foreground enroll, the systemd service) just reads that one env file.
// The address the box dials. The FE's own apiBase is a browser-side default
// (localhost in dev) that a remote box CANNOT reach, so if the admin hasn't set
// a real URL we surface an obvious placeholder instead of a copy-paste that
// silently contains `localhost` — forcing them to substitute the mgmt host.
const MGMT_PLACEHOLDER = 'http://<management-host>/api'
const mgmtForSnippet = computed(() => {
  const v = mgmtUrl.value.trim()
  if (!v || /\/\/(localhost|127\.0\.0\.1)\b/.test(v)) return MGMT_PLACEHOLDER
  return v
})

const enrollSnippet = computed(() => {
  const m = codeModal.value
  if (!m) return ''
  const mgmt = mgmtForSnippet.value
  if (m.type === 'HPU') {
    return [
      '# On the Gaudi VM (bare metal — recommended over a container).',
      `git clone ${REPO_SSH}    # skip if already cloned`,
      'cd gpu-agent',
      '',
      '# 1) Write the agent config to /etc/devozs-gpu-agent.env AND sanity-check',
      '#    that this box can reach the management URL. "REACHABLE — HTTP 400/401"',
      '#    = the app answered (good). "GATEWAY/PROXY ... HTTP 5xx" = a proxy is in',
      '#    the path (use the LAN IP / an .intel.com FQDN). "UNREACHABLE" = wrong',
      '#    URL or blocked port. Use the management host\'s LAN IP, not a bare name.',
      `sudo ./deploy/bootstrap.sh --mgmt-url ${mgmt} --type HPU --enroll-code ${m.code}`,
      '#    Re-run the connectivity check alone any time (reads the env file):',
      './deploy/bootstrap.sh --check-only',
      '',
      '# 2) Set up + verify the box: driver, Habana venv, MNIST smoke test.',
      '#    Add --with-hf to also verify the Hugging Face fine-tune path.',
      './setup-agent.sh',
      '',
      '# 3) Install the agent INTO the Habana venv without disturbing its',
      '#    matched torch, plus the deps a real job needs.',
      'source ~/habanalabs-venv/bin/activate',
      'pip install --no-deps -e .',
      "pip install 'datasets>=2.18' 'boto3>=1.34' optimum-habana",
      '',
      '# 4) Enroll once (foreground) to cache the bearer token, reading the config',
      '#    from step 1 (incl. PT_HPU_LAZY_MODE=1 — SynapseAI 1.24 defaults to',
      '#    eager). Ctrl+C once it logs "agent up ... ready=True".',
      'set -a; . /etc/devozs-gpu-agent.env; set +a',
      'python -m devozs_gpu_agent',
      '',
      '# 5) Keep it running as a service (starts on boot, restarts on crash, and',
      '#    stays claimable after you log out). Reuses the env file from step 1.',
      './deploy/install-service.sh',
    ].join('\n')
  }
  return [
    '# On the GPU box (Python 3.10+, git SSH access to the repo):',
    `pip install 'devozs-gpu-agent[training,cuda] @ ${GIT_SRC}'`,
    '',
    '# Sanity-check reachability before enrolling: a 400/401 back means the URL',
    '# works (empty body rejected); refused/timeout means wrong URL or blocked port.',
    `curl -sS -m 5 -o /dev/null -w 'HTTP %{http_code}\\n' ${mgmt.replace(/\/$/, '')}/training/agent/heartbeat -X POST -H 'Content-Type: application/json' -d '{}'`,
    '',
    `ENROLL_CODE=${m.code} \\`,
    `MGMT_URL=${mgmt} \\`,
    'AGENT_TYPE=CUDA \\',
    'python -m devozs_gpu_agent',
  ].join('\n')
})

// Just the bare code (no ENROLL_CODE= prefix) so it's a clean one-click copy.
const enrollCodeVar = computed(() => codeModal.value?.code ?? '')

// Light poll so VERIFYING → READY (and heartbeat status) refresh live.
let poll: ReturnType<typeof setInterval> | undefined
const refresh = () => { store.fetchResources(); store.fetchSessions() }
onMounted(() => {
  refresh()
  poll = setInterval(refresh, 4000)
})
onBeforeUnmount(() => { if (poll) clearInterval(poll) })

function openCreate() {
  editingId.value = undefined
  draft.value = { name: '', type: 'CUDA', enabled: true }
  error.value = ''
  drawerOpen.value = true
}

function openRename(resource: ComputeResource) {
  editingId.value = resource.id
  // Only the name is editable on rename; carry type/enabled through unchanged.
  draft.value = { name: resource.name, type: resource.type, enabled: resource.enabled }
  error.value = ''
  drawerOpen.value = true
}

async function save() {
  if (!nameValid.value) {
    error.value = 'Name is required.'
    return
  }
  error.value = ''
  saving.value = true
  try {
    await store.saveResource({ ...draft.value, name: draft.value.name!.trim() }, editingId.value)
    drawerOpen.value = false
  } catch (e) {
    error.value = String(e)
  } finally {
    saving.value = false
  }
}

async function enroll(resource: ComputeResource) {
  const res = await store.issueEnrollmentCode(resource.id)
  // Seed from the BE address only if the admin hasn't set one before; a remembered
  // box-reachable URL (e.g. http://10.111.56.26/api) persists across enrollments.
  if (!mgmtUrl.value) mgmtUrl.value = apiBase.value
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
  const ok = await confirm({
    title: `Delete compute resource "${resource.name}"?`,
    message: 'The agent will no longer be able to enroll or claim jobs with its current token.',
    confirmLabel: 'Delete',
    tone: 'danger',
  })
  if (!ok) return
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
        :pushing-session="pushingByResource[r.id]"
        @enroll="enroll"
        @reverify="reverify"
        @rename="openRename"
        @remove="remove"
      />
      <p v-if="!resources.length" class="text-sm text-gray-400">No compute resources yet.</p>
    </div>

    <!-- create drawer -->
    <div v-if="drawerOpen" class="fixed inset-0 z-40 flex justify-end" @click.self="drawerOpen = false">
      <div class="absolute inset-0 bg-black/30" />
      <aside class="relative z-50 h-full w-full max-w-lg overflow-auto bg-white p-6 shadow-xl">
        <div class="flex items-start justify-between">
          <h2 class="text-lg font-bold text-gray-900">{{ editingId ? 'Rename compute resource' : 'New compute resource' }}</h2>
          <button type="button" class="text-gray-400 hover:text-gray-700" @click="drawerOpen = false">✕</button>
        </div>

        <div class="mt-4 flex flex-col gap-4">
          <label class="flex flex-col gap-1 text-sm">
            <span class="font-medium text-gray-700">Name <span class="text-red-500">*</span></span>
            <input
              v-model="draft.name"
              type="text"
              class="rounded-lg border px-3 py-2"
              :class="!nameValid && draft.name !== '' ? 'border-red-400' : 'border-gray-300'"
              placeholder="my-laptop-gpu"
              @keyup.enter="save"
            >
            <span v-if="!nameValid" class="text-xs text-gray-400">A name is required.</span>
          </label>
          <label v-if="!editingId" class="flex flex-col gap-1 text-sm">
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
            :disabled="saving || !nameValid"
            :title="nameValid ? '' : 'Enter a name first'"
            @click="save"
          >
            {{ saving ? 'Saving…' : editingId ? 'Save' : 'Create' }}
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
      <div class="relative z-50 flex max-h-[90vh] w-full max-w-2xl flex-col overflow-y-auto rounded-2xl bg-white p-6 shadow-xl">
        <h2 class="text-lg font-bold text-gray-900">Enroll {{ codeModal.name }} ({{ codeModal.type }})</h2>
        <p class="mt-1 text-sm text-gray-500">
          The code is shown <span class="font-medium">once</span>. On the box, install the agent then
          run it with the code — it connects outbound, so no inbound access is needed.
        </p>

        <!-- Set the management URL the box must reach BEFORE the snippet so the
             admin fills it in first; step 1 (bootstrap.sh) writes it to the env
             file and sanity-checks reachability against it. -->
        <div class="mt-3">
          <label class="text-xs font-medium text-gray-500">Management URL (the box must reach this — set it before copying the steps)</label>
          <div class="mt-1 flex">
            <input
              v-model="mgmtUrl"
              placeholder="http://10.111.56.26/api"
              class="w-full rounded-l-lg border border-gray-300 bg-white px-3 py-1.5 font-mono text-xs text-gray-800 focus:border-cyan-500 focus:ring-cyan-500"
              @focus="(e) => (e.target as HTMLInputElement).select()"
            >
            <button
              type="button"
              class="rounded-r-lg border border-l-0 border-gray-300 px-3 py-1.5 text-xs text-cyan-800 hover:bg-cyan-50"
              @click="copy(mgmtUrl)"
            >
              Copy
            </button>
          </div>
          <p class="mt-1 text-xs text-gray-400">
            Use the management host's <span class="font-medium">LAN IP</span> (e.g. <span class="font-mono">http://10.111.56.26/api</span>) or an <span class="font-mono">.intel.com</span> FQDN — <span class="font-medium">not</span> a bare hostname, which gets routed through the DMZ proxy and fails. Step 1 (<span class="font-mono">bootstrap.sh</span>) sanity-checks it: <span class="font-mono">HTTP 400/401</span> = the app answered (good); <span class="font-mono">HTTP 5xx</span> = a proxy is in the path (use the LAN IP); refused/timeout = wrong URL or a blocked port (high ports like :18080 are often firewalled — prefer the :80 form).
          </p>
        </div>

        <pre class="mt-3 overflow-auto rounded-lg bg-gray-900 p-4 text-xs leading-relaxed text-green-300">{{ enrollSnippet }}</pre>

        <!-- Quick-copy the enrollment code on its own (no ENROLL_CODE= prefix) for
             a clean one-click copy. The management URL lives above the snippet so
             it's set before the steps are copied. -->
        <div class="mt-3">
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
