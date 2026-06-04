import type {
  ComputeResource,
  ComputeResourceDto,
  EnrollmentCodeResponse,
  TrainingLogDto,
  TrainingSessionDto,
  TrainingSessionSummary,
} from '~/types/training'

// Typed $fetch wrappers for the training REST API (harticle-management).
// Mirrors useScraperApi(); built on useApi().apiBase.
export function useTrainingApi() {
  const { apiBase } = useApi()
  const base = () => `${apiBase.value}/training`

  // --- compute resources ---------------------------------------------------
  const listResources = () =>
    $fetch<ComputeResource[]>(`${base()}/resources`)

  const saveResource = (dto: ComputeResourceDto, id?: string) =>
    id
      ? $fetch<ComputeResource>(`${base()}/resources/${id}`, { method: 'PUT', body: dto })
      : $fetch<ComputeResource>(`${base()}/resources`, { method: 'POST', body: dto })

  const deleteResource = (id: string) =>
    $fetch(`${base()}/resources/${id}`, { method: 'DELETE' })

  const issueEnrollmentCode = (id: string) =>
    $fetch<EnrollmentCodeResponse>(`${base()}/resources/${id}/enrollment-code`, { method: 'POST' })

  const reverifyResource = (id: string) =>
    $fetch<ComputeResource>(`${base()}/resources/${id}/reverify`, { method: 'POST' })

  // --- training sessions ---------------------------------------------------
  const listSessions = () =>
    $fetch<TrainingSessionSummary[]>(`${base()}/sessions`)

  const createSession = (dto: TrainingSessionDto) =>
    $fetch<TrainingSessionSummary>(`${base()}/sessions`, { method: 'POST', body: dto })

  const deleteSession = (id: string) =>
    $fetch(`${base()}/sessions/${id}`, { method: 'DELETE' })

  const sessionStatus = (id: string) =>
    $fetch<TrainingSessionSummary>(`${base()}/sessions/${id}/status`)

  const sessionLogs = (id: string, afterSeq = -1) =>
    $fetch<TrainingLogDto[]>(`${base()}/sessions/${id}/logs`, { query: { afterSeq } })

  const stopSession = (id: string) =>
    $fetch<TrainingSessionSummary>(`${base()}/sessions/${id}/stop`, { method: 'POST' })

  const resumeSession = (id: string) =>
    $fetch<TrainingSessionSummary>(`${base()}/sessions/${id}/resume`, { method: 'POST' })

  // Re-run a FAILED/STOPPED session: creates a NEW session, returns its summary.
  const rerunSession = (id: string) =>
    $fetch<TrainingSessionSummary>(`${base()}/sessions/${id}/rerun`, { method: 'POST' })

  return {
    listResources,
    saveResource,
    deleteResource,
    issueEnrollmentCode,
    reverifyResource,
    listSessions,
    createSession,
    deleteSession,
    sessionStatus,
    sessionLogs,
    stopSession,
    resumeSession,
    rerunSession,
  }
}
