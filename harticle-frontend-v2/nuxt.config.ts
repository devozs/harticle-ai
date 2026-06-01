const isDev = import.meta.dev

const apiHost = process.env.NUXT_PUBLIC_API_HOST
  || (isDev ? 'http://localhost:8080' : 'https://harticle.devozs.com')
const apiBase = process.env.NUXT_PUBLIC_API_BASE || `${apiHost}/api`
const siteUrl = process.env.NUXT_PUBLIC_SITE_URL
  || (isDev ? 'http://localhost:3000' : 'https://harticle.devozs.com')
// Friction-only gate for the /admin section (NOT real security; backend stays open).
const adminPassphrase = process.env.NUXT_PUBLIC_ADMIN_PASSPHRASE || 'harticle-admin'

export default defineNuxtConfig({
  compatibilityDate: '2025-05-31',
  devtools: { enabled: isDev },

  future: {
    compatibilityVersion: 4,
  },

  app: {
    head: {
      title: 'Harticle - Israel Sports AI News Generator',
      charset: 'utf-8',
      meta: [
        { name: 'description', content: 'Israeli Sports News AI Generator' },
        { name: 'twitter:card', content: 'summary_large_image' },
        { name: 'twitter:site', content: '@OziShemesh' },
      ],
    },
  },

  modules: [
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    '@vueuse/nuxt',
    '@nuxtjs/i18n',
  ],

  css: ['~/assets/css/main.css'],

  imports: {
    dirs: ['stores', 'composables', 'utils'],
  },

  routeRules: {
    '/**': isDev ? {} : { swr: 120 },
  },

  runtimeConfig: {
    public: {
      apiBase,
      apiHost,
      siteUrl,
      adminPassphrase,
    },
  },

  i18n: {
    bundle: {
      optimizeTranslationDirective: false,
    },
    locales: [
      { code: 'he', language: 'he-IL', name: 'עברית', dir: 'rtl', file: 'he.json' },
      { code: 'en', language: 'en-US', name: 'English', dir: 'ltr', file: 'en.json' },
    ],
    lazy: true,
    langDir: 'locales',
    defaultLocale: 'he',
    strategy: 'no_prefix',
  },
})
