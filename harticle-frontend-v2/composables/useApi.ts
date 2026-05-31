export function useApi() {
  const config = useRuntimeConfig()

  const apiBase = computed(() =>
    config.public.apiBase.replace(/\/$/, ''),
  )

  const siteUrl = computed(() =>
    config.public.siteUrl.replace(/\/$/, ''),
  )

  function articleUrl(template: string, id: string) {
    if (template === 'yalla') {
      return `${siteUrl.value}/harticles/${id}`
    }
    return `${siteUrl.value}/harticles/${template}/${id}`
  }

  function twitterShareUrl(text: string, url: string) {
    const params = new URLSearchParams({
      text,
      url,
    })
    return `https://twitter.com/intent/tweet?${params.toString()}`
  }

  return { apiBase, siteUrl, articleUrl, twitterShareUrl }
}
