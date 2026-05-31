import type { Article } from '~/types/article'

export function articleDisplayTitle(article: Article): string {
  const title = article.title?.trim()
  if (title) return title
  const keywords = article.keywords?.trim()
  if (keywords) return keywords
  return ''
}

export function articleDisplaySubtitle(article: Article): string {
  const sub = article.subTitle?.trim()
  if (sub) return sub
  const content = article.content?.trim()
  if (content) return content.slice(0, 160)
  return ''
}

export function hasArticleImage(article: Article): boolean {
  const url = article.image?.trim()
  return Boolean(url && url !== 'null' && url !== 'undefined')
}

export function articleCardBackground(article: Article): Record<string, string> | undefined {
  if (!hasArticleImage(article)) {
    return undefined
  }
  return { backgroundImage: `url(${article.image})` }
}

export const articleCardFallbackClass =
  'bg-gradient-to-br from-slate-800 via-slate-900 to-cyan-950'
