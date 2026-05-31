export default defineI18nConfig(() => ({
  legacy: false,
  locale: 'he',
  fallbackLocale: 'en',
  messages: {
    en: {
      heading: 'Harticle AI',
      sub_heading: 'AI Israeli fake news',
      search_placeholder: 'keywords for fake article',
      disclaimer: 'TODO add site disclaimer - just for fun',
      github: 'DevOzs',
      submit: 'Submit',
      creating_article_title: 'Creating New Article',
      creating_article_subtitle: 'We are generating new article... (~2 min)',
      slider_title: 'Cringe Level',
    },
    he: {
      heading: 'Harticle AI',
      sub_heading: 'מחולל כתבות ספורט ישראלי',
      search_placeholder: 'מילות מפתח לחירטוט',
      disclaimer:
        'אתר הומוריסטי שמכיל כתבות מומצאות על ידי בינה מלאכותית ולא באמת נכתבו על ידי כתב אמיתי ולכן אין ליחס חשיבות או להסתמך על כתבות אלו',
      github: 'DevOzs',
      submit: 'חרטט אותי!',
      creating_article_title: 'כתבה חדשה מחורטטת',
      creating_article_subtitle: 'קצת סבלנות והחירטוט מוכן... (כ-2 דקות)',
      slider_title: 'רמת חירטוט',
    },
  },
}))
