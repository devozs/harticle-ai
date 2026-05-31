export interface Article {
  id: string
  title?: string
  subTitle?: string
  keywords?: string
  content?: string
  image: string
  completed: boolean
  faulted: boolean
  updatedAt: string
  votes?: number
}

export type ArticleTemplate = 'sport6' | 'two' | 'yalla'
