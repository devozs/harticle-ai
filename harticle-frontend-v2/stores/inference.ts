import { defineStore } from 'pinia'
import type {
  InferenceModelOption,
  InferenceRunDto,
  InferenceRunSummary,
} from '~/types/inference'

let statusInterval: ReturnType<typeof setInterval> | undefined

// Terminal states where polling can stop.
const TERMINAL = new Set(['COMPLETED', 'FAILED'])

export const useInferenceStore = defineStore('inference', {
  state: () => ({
    models: [] as InferenceModelOption[],
    runs: [] as InferenceRunSummary[],
    // single-run live view (the result panel)
    current: undefined as InferenceRunSummary | undefined,
    loading: false,
    error: null as unknown,
  }),

  actions: {
    async fetchModels() {
      const { listModels } = useInferenceApi()
      try {
        this.models = await listModels()
      } catch (error) {
        this.error = error
        console.error(error)
      }
    },

    async fetchRuns() {
      const { listRuns } = useInferenceApi()
      try {
        this.loading = true
        this.runs = await listRuns()
      } catch (error) {
        this.error = error
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async createRun(dto: InferenceRunDto) {
      const { createRun } = useInferenceApi()
      const created = await createRun(dto)
      await this.fetchRuns()
      // Immediately watch the new run so the result appears as it completes.
      this.startRunPolling(created.id)
      return created
    },

    async deleteRun(id: string) {
      const { deleteRun } = useInferenceApi()
      await deleteRun(id)
      this.runs = this.runs.filter(r => r.id !== id)
      if (this.current?.id === id) {
        this.stopRunPolling()
        this.current = undefined
      }
    },

    // --- single-run polling (both LOCAL @Async and GPU pull are asynchronous) --
    async fetchRun(id: string) {
      const { runStatus } = useInferenceApi()
      try {
        this.current = await runStatus(id)
      } catch (error) {
        this.error = error
      }
    },

    startRunPolling(id: string) {
      this.stopRunPolling()
      const tick = async () => {
        await this.fetchRun(id)
        if (this.current && TERMINAL.has(this.current.status)) {
          this.stopRunPolling()
          // Reflect the terminal state in the list too.
          await this.fetchRuns()
        }
      }
      void tick()
      statusInterval = setInterval(() => void tick(), 2000)
    },

    stopRunPolling() {
      if (statusInterval) {
        clearInterval(statusInterval)
        statusInterval = undefined
      }
    },
  },
})
