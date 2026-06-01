<script setup lang="ts">
const route = useRoute()
const router = useRouter()
const { logout: clearToken } = useAdminAuth()

const nav = [
  { to: '/admin', label: 'Dashboard', exact: true },
  { to: '/admin/scraper/sites', label: 'Sites & Rules' },
  { to: '/admin/scraper/reporters', label: 'Reporters' },
  { to: '/admin/scraper/results', label: 'Run & Results' },
]

function isActive(item: { to: string, exact?: boolean }) {
  return item.exact ? route.path === item.to : route.path.startsWith(item.to)
}

function logout() {
  clearToken()
  router.push('/admin/login')
}
</script>

<template>
  <div dir="ltr" class="min-h-screen bg-gray-50 text-gray-900">
    <header class="flex items-center justify-between border-b border-gray-200 bg-white px-6 py-3">
      <div class="flex items-center gap-2">
        <span class="text-lg font-bold text-cyan-800">Harticle</span>
        <span class="text-sm text-gray-400">/ Scraper Admin</span>
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
      <aside class="min-h-[calc(100vh-49px)] w-56 shrink-0 border-r border-gray-200 bg-white p-3">
        <nav class="flex flex-col gap-1">
          <NuxtLink
            v-for="item in nav"
            :key="item.to"
            :to="item.to"
            class="rounded-lg px-3 py-2 text-sm font-medium"
            :class="isActive(item)
              ? 'bg-cyan-700 text-white'
              : 'text-gray-700 hover:bg-gray-100'"
          >
            {{ item.label }}
          </NuxtLink>
        </nav>
      </aside>

      <main class="flex-1 p-6">
        <slot />
      </main>
    </div>
  </div>
</template>
