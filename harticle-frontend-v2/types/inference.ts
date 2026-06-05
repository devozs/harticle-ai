// Mirrors the inference API in harticle-management (camelCase JSON keys).
import type { ComputeResourceType, ModelReachability } from '~/types/training'

export type InferenceStatus = 'PENDING' | 'ASSIGNED' | 'RUNNING' | 'COMPLETED' | 'FAILED'

/** A trained model the admin can test: one COMPLETED training session's output. */
export interface InferenceModelOption {
  sessionId: string
  name: string
  baseModel: string
  outputModelRef: string
  /** Whether this model's files are reachable for LOCAL CPU inference. */
  availableLocal: boolean
  /** Where the model can be loaded from; ORPHANED models are hidden from the picker. */
  reachability?: ModelReachability
  /** The reporter this model was trained for (undefined = general model over all reporters). */
  reporterId?: string
  /** That reporter's display name (undefined for a general model); drives the reporter picker. */
  reporterName?: string
}

/** One generated article sample, parsed into title / sub-title / paragraph. */
export interface ArticleSample {
  title: string
  subTitle: string
  paragraph: string
}

/** Create + submit request. target is 'LOCAL' or a compute resource id. */
export interface InferenceRunDto {
  sourceSessionId: string
  target: string
  prompt: string
  /** Single "absurdity" dial (0..100); the server derives temperature + maxLength from it. */
  absurdity?: number
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
  outputs?: ArticleSample[]
  errorMessage?: string
  errorType?: string
  createdAtEpochMs?: number
  durationMs?: number
}
