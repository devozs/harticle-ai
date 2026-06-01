// Friction-only gate for /admin/** (NOT security; backend /scraper/** is permitAll).
// Client-only: the token lives in localStorage (unreadable during SSR), and gating
// on the server added no value — only fragility — so we skip it entirely there.
export default defineNuxtRouteMiddleware((to) => {
  if (import.meta.server) return
  if (to.path === '/admin/login') return

  const { isAuthed } = useAdminAuth()
  if (!isAuthed.value) {
    return navigateTo({ path: '/admin/login', query: { redirect: to.fullPath } })
  }
})
