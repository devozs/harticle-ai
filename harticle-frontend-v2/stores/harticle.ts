import { defineStore } from 'pinia'
import Cookies from 'js-cookie'
import type { Article } from '~/types/article'

let waitInterval: ReturnType<typeof setInterval> | undefined

export const useHarticleStore = defineStore('harticle', {
  state: () => ({
    articles: [] as Article[],
    loading: false,
    creating: false,
    pendingArticle: {} as Partial<Article>,
    creationStatus: '',
    selected: undefined as Article | undefined,
    keywords: '',
    reporter: 'DORON_BEN_DOR',
    temperature: 50,
    error: null as unknown,
  }),

  getters: {
    completed: (state) =>
      state.articles
        .filter(article => article.completed && !article.faulted)
        .sort((a, b) => new Date(b.updatedAt).getTime() - new Date(a.updatedAt).getTime()),

    isCreating: state => state.creating,

    getById: state => (articleId: string) =>
      state.articles.find(article => article.id === articleId),
  },

  actions: {
    select(selected?: Article) {
      if (!selected?.id) return
      this.selected = selected
      const index = this.articles.findIndex(article => article.id === selected.id)
      if (index === -1) {
        this.articles.push({ ...selected })
      } else {
        this.articles[index] = selected
      }
    },

    async fetchAll() {
      const { apiBase } = useApi()
      try {
        this.loading = true
        this.articles = await $fetch<Article[]>(`${apiBase.value}/article`)
      } catch (error) {
        this.error = error
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async fetchOne(uid: string) {
      const { apiBase } = useApi()
      try {
        this.loading = true
        const response = await $fetch<Article>(`${apiBase.value}/article/${uid}`)
        if (response?.id) {
          const index = this.articles.findIndex(article => article.id === response.id)
          if (index === -1) {
            this.articles.push({ ...response })
          } else {
            this.articles[index] = response
          }
        }
        return response
      } catch (error) {
        this.error = error
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async create() {
      const { apiBase } = useApi()
      try {
        this.creating = true
        const url = `${apiBase.value}/article/${encodeURIComponent(this.keywords)}/${this.reporter}/${this.temperature}`
        const response = await $fetch<Article>(url, { method: 'POST' })
        this.articles.push({ ...response })
        const expires = new Date(Date.now() + 3 * 60 * 1000)
        Cookies.set('pendingArticleId', response.id, { expires })
        this.pendingArticle = response
        await this.wait()
      } catch (error) {
        this.error = error
        this.creationStatus = 'failed'
        this.creating = false
        console.error(error)
      } finally {
        this.keywords = ''
      }
    },

    async wait() {
      let pendingArticleId = Cookies.get('pendingArticleId')
      if (!pendingArticleId) return

      this.creating = true
      const { apiBase } = useApi()
      this.pendingArticle = await $fetch<Article>(`${apiBase.value}/article/${pendingArticleId}`)

      if (waitInterval) clearInterval(waitInterval)

      const poll = () => {
        const id = Cookies.get('pendingArticleId')
        if (!id) {
          this.creating = false
          this.pendingArticle = {}
          this.creationStatus = 'failed'
          if (waitInterval) {
            clearInterval(waitInterval)
            waitInterval = undefined
          }
          return
        }
        pendingArticleId = id
        void this.fetchOne(id).then((fresh) => {
          if (fresh?.id) {
            this.pendingArticle = fresh
          }
          const found = this.articles.find(article => article.id === id)
          if (found?.completed && !found.faulted) {
            this.creationStatus = 'successed'
            this.creating = false
            this.pendingArticle = {}
            Cookies.remove('pendingArticleId')
            if (waitInterval) {
              clearInterval(waitInterval)
              waitInterval = undefined
            }
            void this.fetchAll()
          }
        })
      }

      poll()
      waitInterval = setInterval(poll, 2000)
    },

    clearWait() {
      if (waitInterval) {
        clearInterval(waitInterval)
        waitInterval = undefined
      }
    },
  },
})
