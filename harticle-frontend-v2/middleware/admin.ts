// Friction-only gate for /admin/** (NOT real security; the backend /scraper/**
// is still permitAll). Redirects to the login page when the admin cookie is
// missing or stale. Uses the shared useAdminAuth so cookie options match the
// login page exactly (otherwise SSR re-emits a downgraded session cookie and
// the login is "forgotten" on reload).
export default defineNuxtRouteMiddleware((to) => {
  if (to.path === '/admin/login') return

  const { isAuthed } = useAdminAuth()
  if (!isAuthed.value) {
    return navigateTo({ path: '/admin/login', query: { redirect: to.fullPath } })
  }
})
