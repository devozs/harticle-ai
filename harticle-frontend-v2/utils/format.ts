import dayjs from 'dayjs'

export function formatDateTime(value?: string | Date) {
  if (!value) return ''
  return dayjs(value).format('YYYY-MM-DD HH:mm:ss')
}

export function formatDateShort(value?: string | Date) {
  if (!value) return ''
  return dayjs(value).format('DD/MM/YYYY HH:mm:ss')
}
