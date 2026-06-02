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
  checkpointUri?: string
  resumable: boolean
  pushToHub: boolean
  outputModelRef?: string
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
