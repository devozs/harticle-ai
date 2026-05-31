import { defineStore } from 'pinia'
import Cookies from 'js-cookie'

let waitForArticle;

function apiBase() {
    return useRuntimeConfig().public.apiBase.replace(/\/$/, '')
}

export const useHarticleStore = defineStore("harticle", {

    state: () => {
        return {
            articles: [],
            loading: false,
            creating: false,
            pendingArticle: {},
            creationStatus: '',
            selected: undefined,
            keywords: '',
            reporter: 'DORON_BEN_DOR',
            temperature: 50,
            filter: '',
            error: null,
        }

    }
    ,
    getters: {
        completed(state) {
            return state.articles.filter(article => article.completed && !article.faulted)
                .sort((a, b) => new Date(b.updatedAt) - new Date(a.updatedAt))
        },
        inProgress(state) {
            return state.articles.filter(article => !article.completed && !article.faulted)
        },
        isValidKeywords(state) {
            // return true
            return state.keywords.trim().split(/\s+/).length > 3
        },
        isCreating(state) {
            return state.creating
            // return state.pendingArticle !== undefined && state.pendingArticle.id !== undefined
        },
        getById(state) {
            return (articleId) => state.articles.find((article) => article.id === articleId)
        },
    },
    actions: {
        select(selected) {
            if (selected !== undefined && selected.id !== undefined) {
                this.selected = selected
                const index = this.articles.findIndex(article => article.id == selected.id)
                if (index === -1) {
                    this.articles.push({
                        ...selected
                    })
                } else {
                    this.articles[index] = selected
                }
            }
        },
        async fetchAll() {
            try {
                this.articles = []
                this.loading = true
                const response = await $fetch(`${apiBase()}/article`)
                console.log("Articles retrieved successfully")
                this.articles = response
                console.log(this.articles)
            } catch (error) {
                this.error = error
                console.log(error)
            } finally {
                this.loading = false
            }
        },
        async fetchOne(uid) {
            try {
                this.loading = true
                const response = await $fetch(`${apiBase()}/article/${uid}`)
                console.log("Article retrieved successfully")
                console.log(response.id)
                if (response !== undefined && response.id !== undefined) {
                    const index = this.articles.findIndex(article => article.id == response.id)
                    if (index === -1) {
                        this.articles.push({
                            ...response
                        })
                    } else {
                        this.articles[index] = response
                    }
                }
            } catch (error) {
                this.error = error
                console.log(error)
            } finally {
                this.loading = false
            }
        },
        async create() {
            try {
                this.creating = true
                const url = `${apiBase()}/article/${this.keywords}/${this.reporter}/${this.temperature}`
                console.log('generating article for:', url)
                const response = await $fetch(url, {
                    method: 'POST',
                    initialCache: false,
                    server: true
                })
                this.articles.push({
                    ...response
                })
                var in3Minutes = new Date(new Date().getTime() + 3 * 60 * 1000);
                Cookies.set('pendingArticleId', response.id, { expires: in3Minutes })
                console.log('new article created:', response)
                this.pendingArticle = response
                this.wait()

            }
            catch (error) {
                this.error = error
                this.creationStatus = "failed"
                this.creating = false
                console.log(error)
            } finally {
                this.keywords = ''
            }
        },
        async wait() {
            let pendingArticleId = Cookies.get('pendingArticleId')
            if (pendingArticleId !== undefined) {
                this.creating = true
                const response = await $fetch(`${apiBase()}/article/${pendingArticleId}`)
                this.pendingArticle = response

                waitForArticle = setInterval(() => {
                    console.log("inside interval")
                    let found = this.articles.find(article => article.id === pendingArticleId)
                    this.pendingArticle = found === undefined ? {} : found
                    if (found !== undefined && found.completed && !found.faulted) {
                        this.creationSuccessed = true
                        console.log("article processed: " + pendingArticleId)
                        this.pendingArticle = undefined
                        this.creationStatus = "successed"
                        this.creating = false
                        Cookies.remove('pendingArticleId')
                        clearInterval(waitForArticle);
                    } else {
                        pendingArticleId = Cookies.get('pendingArticleId')
                        if (pendingArticleId !== undefined) {
                            this.creating = true
                            console.log("still waiting for article: " + pendingArticleId)
                            this.fetchOne(pendingArticleId)
                        } else {
                            this.creating = false
                            this.pendingArticle = undefined
                            Cookies.remove('pendingArticleId')
                            this.creationStatus = "failed"
                            clearInterval(waitForArticle);
                        }
                    }
                }, 10000)
            }
        },
    }
})


