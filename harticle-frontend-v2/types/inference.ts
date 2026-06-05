// Mirrors the inference API in harticle-management (camelCase JSON keys).
import type { ComputeResourceType } from '~/types/training'

export type InferenceStatus = 'PENDING' | 'ASSIGNED' | 'RUNNING' | 'COMPLETED' | 'FAILED'

/** A trained model the admin can test: one COMPLETED training session's output. */
export interface InferenceModelOption {
  sessionId: string
  name: string
  baseModel: string
  outputModelRef: string
  /** Whether this model's files are reachable for LOCAL CPU inference. */
  availableLocal: boolean
}

/** Create + submit request. target is 'LOCAL' or a compute resource id. */
export interface InferenceRunDto {
  sourceSessionId: string
  target: string
  prompt: string
  temperature?: number
  maxLength?: number
  numReturnSequences?: number
}

export interface InferenceRunSummary {
  id: string
  sourceSessionId?: string
  modelRef: string
  baseModel?: string
  prompt: string
  status: InferenceStatus
  local: boolean
  requiredType?: ComputeResourceType
  assignedResourceId?: string
  assignedResourceName?: string
  outputs?: string[]
  errorMessage?: string
  errorType?: string
  createdAtEpochMs?: number
  durationMs?: number
}
