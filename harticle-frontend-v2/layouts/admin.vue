<script setup lang="ts">
const route = useRoute()
const router = useRouter()
const { logout: clearToken } = useAdminAuth()

// Grouped nav: a flat Dashboard plus labeled sections. Models routes live under
// /admin/models/* (Compute Resources, Training, Inference).
const nav = [
  {
    group: 'Scraper',
    items: [
      { to: '/admin/scraper/sites', label: 'Sites & Rules' },
      { to: '/admin/scraper/reporters', label: 'Reporters' },
      { to: '/admin/scraper/results', label: 'Run & Results' },
    ],
  },
  {
    group: 'Models',
    items: [
      { to: '/admin/models/resources', label: 'Compute Resources' },
      { to: '/admin/models/training', label: 'Training' },
      { to: '/admin/models/inference', label: 'Inference' },
    ],
  },
]

function isActive(item: { to: string, exact?: boolean }) {
  return item.exact ? route.path === item.to : route.path.startsWith(item.to)
}

// Off-canvas drawer state (mobile only). Close on navigation so picking a link
// doesn't leave the overlay covering the page.
const mobileNavOpen = ref(false)
watch(() => route.path, () => { mobileNavOpen.value = false })

function logout() {
  clearToken()
  router.push('/admin/login')
}
</script>

<template>
  <div dir="ltr" class="min-h-screen bg-gray-50 text-gray-900">
    <header class="flex items-center justify-between border-b border-gray-200 bg-white px-4 py-3 sm:px-6">
      <div class="flex items-center gap-2">
        <button
          type="button"
          class="-ml-1 rounded-lg p-1.5 text-gray-600 hover:bg-gray-100 lg:hidden"
          aria-label="Toggle navigation"
          @click="mobileNavOpen = !mobileNavOpen"
        >
          <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
          </svg>
        </button>
        <span class="text-lg font-bold text-cyan-800">Harticle</span>
        <span class="hidden text-sm text-gray-400 sm:inline">/ Admin</span>
      </div>
      <button
        type="button"
        class="rounded-lg border border-gray-300 px-3 py-1.5 text-sm text-gray-600 hover:bg-gray-100"
        @click="logout"
      >
        Logout
      </button>
    </header>

    <div class="flex">
      <!-- backdrop (mobile only, when drawer open) -->
      <div
        v-if="mobileNavOpen"
        class="fixed inset-0 z-30 bg-black/30 lg:hidden"
        @click="mobileNavOpen = false"
      />

      <aside
        class="fixed inset-y-0 left-0 z-40 w-64 shrink-0 transform overflow-y-auto border-r border-gray-200 bg-white p-3 pt-16 transition-transform lg:static lg:z-auto lg:min-h-[calc(100vh-57px)] lg:w-56 lg:translate-x-0 lg:pt-3"
        :class="mobileNavOpen ? 'translate-x-0' : '-translate-x-full'"
      >
        <nav class="flex flex-col gap-4">
          <NuxtLink
            to="/admin"
            class="rounded-lg px-3 py-2 text-sm font-medium"
            :class="isActive({ to: '/admin', exact: true })
              ? 'bg-cyan-700 text-white'
              : 'text-gray-700 hover:bg-gray-100'"
          >
            Dashboard
          </NuxtLink>

          <div v-for="section in nav" :key="section.group" class="flex flex-col gap-1">
            <div class="px-3 text-xs font-semibold uppercase tracking-wide text-gray-400">
              {{ section.group }}
            </div>
            <NuxtLink
              v-for="item in section.items"
              :key="item.to"
              :to="item.to"
              class="rounded-lg px-3 py-2 text-sm font-medium"
              :class="isActive(item)
                ? 'bg-cyan-700 text-white'
                : 'text-gray-700 hover:bg-gray-100'"
            >
              {{ item.label }}
            </NuxtLink>
          </div>
        </nav>
      </aside>

      <main class="min-w-0 flex-1 p-4 sm:p-6">
        <slot />
      </main>
    </div>

    <!-- global confirmation modal (replaces window.confirm) -->
    <ConfirmDialog />
  </div>
</template>
