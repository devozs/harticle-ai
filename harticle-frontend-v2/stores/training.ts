import { defineStore } from 'pinia'
import type {
  ComputeResource,
  ComputeResourceDto,
  EnrollmentCodeResponse,
  TrainingLogDto,
  TrainingSessionDto,
  TrainingSessionSummary,
} from '~/types/training'

let statusInterval: ReturnType<typeof setInterval> | undefined

// Terminal states where polling can stop.
const TERMINAL = new Set(['COMPLETED', 'FAILED', 'STOPPED'])

export const useTrainingStore = defineStore('training', {
  state: () => ({
    resources: [] as ComputeResource[],
    sessions: [] as TrainingSessionSummary[],
    // monitor view (single session)
    monitor: undefined as TrainingSessionSummary | undefined,
    logs: [] as TrainingLogDto[],
    loading: false,
    error: null as unknown,
  }),

  actions: {
    // --- compute resources -------------------------------------------------
    async fetchResources() {
      const { listResources } = useTrainingApi()
      try {
        this.loading = true
        this.resources = await listResources()
      } catch (error) {
        this.error = error
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async saveResource(dto: ComputeResourceDto, id?: string) {
      const { saveResource } = useTrainingApi()
      const saved = await saveResource(dto, id)
      const index = this.resources.findIndex(r => r.id === saved.id)
      if (index === -1) this.resources.push(saved)
      else this.resources[index] = saved
      return saved
    },

    async deleteResource(id: string) {
      const { deleteResource } = useTrainingApi()
      await deleteResource(id)
      this.resources = this.resources.filter(r => r.id !== id)
    },

    async issueEnrollmentCode(id: string): Promise<EnrollmentCodeResponse> {
      const { issueEnrollmentCode } = useTrainingApi()
      const res = await issueEnrollmentCode(id)
      await this.fetchResources()
      return res
    },

    async reverifyResource(id: string) {
      const { reverifyResource } = useTrainingApi()
      const updated = await reverifyResource(id)
      const index = this.resources.findIndex(r => r.id === updated.id)
      if (index !== -1) this.resources[index] = updated
      return updated
    },

    // --- training sessions -------------------------------------------------
    async fetchSessions() {
      const { listSessions } = useTrainingApi()
      try {
        this.loading = true
        this.sessions = await listSessions()
      } catch (error) {
        this.error = error
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async createSession(dto: TrainingSessionDto) {
      const { createSession } = useTrainingApi()
      const created = await createSession(dto)
      await this.fetchSessions()
      return created
    },

    async deleteSession(id: string) {
      const { deleteSession } = useTrainingApi()
      await deleteSession(id)
      this.sessions = this.sessions.filter(s => s.id !== id)
    },

    async stopSession(id: string) {
      const { stopSession } = useTrainingApi()
      this.monitor = await stopSession(id)
    },

    async resumeSession(id: string) {
      const { resumeSession } = useTrainingApi()
      this.monitor = await resumeSession(id)
      this.startMonitorPolling(id)
    },

    // Re-run a FAILED/STOPPED session as a fresh attempt (new session id). The
    // original is left intact; refresh the list so the new attempt shows up.
    async rerunSession(id: string) {
      const { rerunSession } = useTrainingApi()
      const created = await rerunSession(id)
      await this.fetchSessions()
      return created
    },

    // Fetch a remotely-trained model's files to the management host so it can be
    // tested on LOCAL CPU. The agent pushes on its next heartbeat; poll the list
    // so the status (REQUESTED → UPLOADING → AVAILABLE) updates as it progresses.
    async fetchModelLocal(id: string) {
      const { fetchModelLocal } = useTrainingApi()
      const updated = await fetchModelLocal(id)
      await this.fetchSessions()
      return updated
    },

    // --- monitor (single session live view) --------------------------------
    async fetchMonitor(id: string) {
      const { sessionStatus, sessionLogs } = useTrainingApi()
      try {
        this.monitor = await sessionStatus(id)
        const afterSeq = this.logs.length ? this.logs[this.logs.length - 1].seq : -1
        const fresh = await sessionLogs(id, afterSeq)
        if (fresh.length) this.logs.push(...fresh)
      } catch (error) {
        this.error = error
      }
    },

    // Poll a session's status + logs every 2s; auto-stop once terminal.
    startMonitorPolling(id: string) {
      this.stopMonitorPolling()
      this.logs = []
      const tick = async () => {
        await this.fetchMonitor(id)
        if (this.monitor && TERMINAL.has(this.monitor.status)) {
          this.stopMonitorPolling()
        }
      }
      void tick()
      statusInterval = setInterval(() => void tick(), 2000)
    },

    stopMonitorPolling() {
      if (statusInterval) {
        clearInterval(statusInterval)
        statusInterval = undefined
      }
    },
  },
})
