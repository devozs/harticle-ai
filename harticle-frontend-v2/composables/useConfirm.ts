import { reactive } from 'vue'

/**
 * Promise-based confirmation dialog, replacing the browser's blocking
 * window.confirm with our own modal. A single <ConfirmDialog> mounted in the
 * admin layout renders this shared state; calling confirm() opens it and
 * resolves true/false when the user chooses.
 *
 * Usage:
 *   const { confirm } = useConfirm()
 *   if (!await confirm({ title: 'Delete X?', message: '…', tone: 'danger' })) return
 */
export interface ConfirmOptions {
  title: string
  message?: string
  confirmLabel?: string
  cancelLabel?: string
  tone?: 'danger' | 'default'
}

interface ConfirmState extends ConfirmOptions {
  open: boolean
  busy: boolean
  _resolve?: (ok: boolean) => void
}

// Module-level singleton so the composable and the dialog component share it.
const state = reactive<ConfirmState>({
  open: false,
  busy: false,
  title: '',
  message: '',
  confirmLabel: 'Confirm',
  cancelLabel: 'Cancel',
  tone: 'default',
})

export function useConfirm() {
  function confirm(opts: ConfirmOptions): Promise<boolean> {
    state.title = opts.title
    state.message = opts.message ?? ''
    state.confirmLabel = opts.confirmLabel ?? 'Confirm'
    state.cancelLabel = opts.cancelLabel ?? 'Cancel'
    state.tone = opts.tone ?? 'default'
    state.busy = false
    state.open = true
    return new Promise<boolean>((resolve) => {
      state._resolve = resolve
    })
  }

  function resolve(ok: boolean) {
    state.open = false
    state._resolve?.(ok)
    state._resolve = undefined
  }

  return { state, confirm, resolve }
}
