export function useAdminAuth() {
  const config = useRuntimeConfig()
  // Friction-only gate (NOT security). Persisted in localStorage so the login
  // sticks across refreshes/tabs until logout. SSR-safe: useLocalStorage returns
  // the default ('') on the server, the real value on the client after hydration.
  // (useStorage is NOT auto-imported by @vueuse/nuxt — useLocalStorage is.)
  const token = useLocalStorage<string>('admin_token', '')

  const isAuthed = computed(() => token.value === config.public.adminPassphrase)

  function login(passphrase: string): boolean {
    if (passphrase === config.public.adminPassphrase) {
      token.value = passphrase
      return true
    }
    return false
  }

  function logout() {
    token.value = ''
  }

  return { token, isAuthed, login, logout }
}
