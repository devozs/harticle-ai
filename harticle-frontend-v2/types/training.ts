// Mirrors the training API in harticle-management (camelCase JSON keys).

export type ComputeResourceType = 'CUDA' | 'HPU'
export type ComputeResourceStatus = 'OFFLINE' | 'IDLE' | 'BUSY' | 'ERROR'
export type ComputeResourceReadiness = 'UNVERIFIED' | 'VERIFYING' | 'READY' | 'FAILED'
export type TrainingStatus =
  | 'PENDING'
  | 'ASSIGNED'
  | 'RUNNING'
  | 'STOP_REQUESTED'
  | 'STOPPED'
  | 'RESUMING'
  | 'COMPLETED'
  | 'FAILED'

export type ModelFetchStatus = 'NONE' | 'REQUESTED' | 'UPLOADING' | 'AVAILABLE' | 'FAILED'

export type ModelReachability = 'PORTABLE' | 'LOCAL_AVAILABLE' | 'REMOTE_ONLY' | 'REMOTE_OFFLINE' | 'ORPHANED'

interface BaseEntity {
  id: string
  version?: number
  createdAt?: string
  updatedAt?: string
}

export interface ComputeResource extends BaseEntity {
  name: string
  type: ComputeResourceType
  status: ComputeResourceStatus
  enrolled: boolean
  enabled: boolean
  lastHeartbeat?: string
  capabilities?: string
  currentSessionId?: string
  readiness: ComputeResourceReadiness
  readinessDetail?: string
  readinessCheckedAt?: string
  reverifyRequested: boolean
}

export interface ComputeResourceDto {
  name?: string
  type?: ComputeResourceType
  enabled?: boolean
}

export interface EnrollmentCodeResponse {
  resourceId: string
  enrollmentCode: string
}

export interface TrainingSessionDto {
  name?: string
  baseModel?: string
  requiredType?: ComputeResourceType
  stubMode?: boolean
  pushToHub?: boolean
  /** Fetch the trained model to local on successful completion (default true). */
  autoFetchLocal?: boolean
  reporterIds?: string[]
  epochs?: number
  batchSize?: number
  learningRate?: number
  warmupSteps?: number
  weightDecay?: number
  saveSteps?: number
  contextLength?: number
}

// The live snapshot the monitor polls (DB-backed analogue of ScrapeProgress).
export interface TrainingSessionSummary {
  id: string
  name: string
  baseModel: string
  status: TrainingStatus
  requiredType: ComputeResourceType
  stubMode: boolean
  progressPercent: number
  currentEpoch?: number
  totalEpochs?: number
  currentStep?: number
  totalSteps?: number
  lastLoss?: number
  assignedResourceId?: string
  assignedResourceName?: string
  /** The reporter this model was trained for (null = general model over all reporters). */
  reporterId?: string
  /** Snapshot of that reporter's display name at train time (null for a general model). */
  reporterName?: string
  checkpointUri?: string
  resumable: boolean
  rerunnable: boolean
  pushToHub: boolean
  outputModelRef?: string
  /** Whether this model's files are reachable for LOCAL CPU inference on the mgmt host. */
  modelAvailableLocal: boolean
  /** Where the model can be loaded from (undefined until COMPLETED); ORPHANED = lost with its box. */
  modelReachability?: ModelReachability
  /** Progress of fetching a remote model's files to the mgmt host. */
  modelFetchStatus: ModelFetchStatus
  /** Live fetch-to-local counters + source dir (undefined when no fetch has run). Target is models/{id}/. */
  modelFetchFilesTotal?: number
  modelFetchFilesDone?: number
  modelFetchBytesTotal?: number
  modelFetchBytesDone?: number
  modelFetchSource?: string
  parentSessionId?: string
  attemptNumber: number
  errorMessage?: string
  errorType?: string
  createdAtEpochMs?: number
  lastAgentSeenAtEpochMs?: number
  secondsSinceActivity?: number
}

export interface TrainingLogDto {
  seq: number
  level: string
  message: string
  loggedAtEpochMs?: number
}
