import type { CookieRef } from '#app'

// Single source of truth for the admin gate cookie. Every caller MUST go
// through this so the cookie options are identical at every site — Nuxt
// re-emits the Set-Cookie header with whatever options the `useCookie` call
// passed, so an option-less read elsewhere would downgrade this persistent
// cookie to a session cookie and "forget" the login on reload.
const COOKIE_NAME = 'admin_token'
const MAX_AGE = 60 * 60 * 24 * 7 // 1 week

export function useAdminToken(): CookieRef<string | null> {
  return useCookie<string | null>(COOKIE_NAME, {
    maxAge: MAX_AGE,
    path: '/',
    sameSite: 'lax',
  })
}

export function useAdminAuth() {
  const config = useRuntimeConfig()
  const token = useAdminToken()

  const isAuthed = computed(() => token.value === config.public.adminPassphrase)

  function login(passphrase: string): boolean {
    if (passphrase === config.public.adminPassphrase) {
      token.value = passphrase
      return true
    }
    return false
  }

  function logout() {
    token.value = null
  }

  return { token, isAuthed, login, logout }
}
