const isDev = process.env.NODE_ENV === 'development'

const apiHost = process.env.NUXT_PUBLIC_API_HOST
  || (isDev ? 'http://localhost:8080' : 'https://harticle.devozs.com')
const apiBase = process.env.NUXT_PUBLIC_API_BASE || `${apiHost}/api`

export default defineNuxtConfig({
  compatibilityDate: '2025-05-31',
  devtools: { enabled: isDev },

  app: {
    head: {
      title: 'Harticle - Israel Sports AI News Generator',
      charset: 'utf-16',
      meta: [
        { name: 'description', content: 'Israeli Sports News AI Generator' },
        { name: 'twitter:card', content: 'summary_large_image' },
        { name: 'twitter:site', content: '@OziShemesh' },
        { name: 'twitter:title', content: 'Harticle' },
        { name: 'twitter:description', content: 'Israeli Sports News AI Generator' },
        {
          name: 'twitter:image',
          content: `https://www.soccertoday.com/wp-content/uploads/2020/03/Soccer-using-AI.jpg?${Date.now()}`,
        },
        { name: 'twitter:image:alt', content: 'Harticle.devozs.com' },
        { name: 'og:title', content: 'Harticle.devozs.com' },
        { name: 'og:description', content: 'Israeli Sports News AI Generator' },
        {
          name: 'og:image',
          content: `https://www.soccertoday.com/wp-content/uploads/2020/03/Soccer-using-AI.jpg?${Date.now()}`,
        },
        { name: 'og:url', content: 'https://harticle.devozs.com' },
        { name: 'og:site_name', content: 'Israeli Sports News AI Generator' },
        { name: 'og:type', content: 'summary_large_image' },
      ],
    },
  },

  modules: [
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    '@vueuse/nuxt',
    '@nuxtjs/i18n',
  ],

  imports: {
    dirs: ['stores', 'utils'],
  },

  routeRules: {
    '/**': isDev ? {} : { swr: 120 },
  },

  runtimeConfig: {
    public: {
      apiBase,
      apiHost,
    },
  },

  css: ['~/assets/css/tailwind.css'],

  i18n: {
    bundle: {
      optimizeTranslationDirective: false,
    },
    locales: [
      { code: 'he', language: 'he-IL', name: 'עברית', dir: 'rtl' },
      { code: 'en', language: 'en-US', name: 'English', dir: 'ltr' },
    ],
    defaultLocale: 'he',
    strategy: 'no_prefix',
    vueI18n: 'i18n/i18n.config.ts',
  },
})
