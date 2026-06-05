import type {
  InferenceModelOption,
  InferenceRunDto,
  InferenceRunSummary,
} from '~/types/inference'

// Typed $fetch wrappers for the inference REST API (harticle-management).
// Mirrors useTrainingApi(); built on useApi().apiBase.
export function useInferenceApi() {
  const { apiBase } = useApi()
  const base = () => `${apiBase.value}/inference`

  // Trained models available to test (COMPLETED sessions with an output model).
  const listModels = () =>
    $fetch<InferenceModelOption[]>(`${base()}/models`)

  const listRuns = () =>
    $fetch<InferenceRunSummary[]>(`${base()}/runs`)

  const createRun = (dto: InferenceRunDto) =>
    $fetch<InferenceRunSummary>(`${base()}/runs`, { method: 'POST', body: dto })

  const runStatus = (id: string) =>
    $fetch<InferenceRunSummary>(`${base()}/runs/${id}/status`)

  const deleteRun = (id: string) =>
    $fetch(`${base()}/runs/${id}`, { method: 'DELETE' })

  return { listModels, listRuns, createRun, runStatus, deleteRun }
}
