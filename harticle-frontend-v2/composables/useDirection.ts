export function useDirection() {
  const { locale } = useI18n()
  const dir = computed(() => (locale.value === 'en' ? 'ltr' : 'rtl'))
  return { locale, dir }
}
