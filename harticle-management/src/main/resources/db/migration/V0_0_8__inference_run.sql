-- Admin inference testing: run a prompt against a trained model and keep the
-- result as history. A run sources its model from a COMPLETED training_session
-- (model_ref copied at create time so it survives session deletion) and executes
-- either LOCALLY on the deployment CPU (via the co-located engine) or on a
-- GPU/HPU agent claimed through the same pull protocol as training.

create table if not exists inference_run
(
    id                   uuid not null
        constraint inference_run_pkey
        primary key,
    created_at           timestamp,
    updated_at           timestamp,
    version              bigint,
    -- the COMPLETED training_session whose model is tested (loose ref)
    source_session_id    uuid,
    -- copied from the session's output_model_ref: storage URI and/or HF repo id
    model_ref            varchar(1024) not null,
    base_model           varchar(1024),
    prompt               text not null,
    -- {temperature, maxLength, numReturnSequences}
    params               jsonb,
    -- PENDING | ASSIGNED | RUNNING | COMPLETED | FAILED
    status               varchar(24)  not null default 'PENDING',
    -- CUDA | HPU for a GPU/HPU run; null for a LOCAL (CPU) run
    required_type        varchar(16),
    -- true => run on the deployment CPU via the engine; false => on a GPU/HPU agent
    local                boolean      not null default false,
    assigned_resource_id uuid,
    -- generated samples, JSON array of strings
    outputs              jsonb,
    error_message        text,
    error_type           varchar(32),
    started_at           timestamp,
    finished_at          timestamp,
    duration_ms          bigint
);

create index if not exists idx_inference_run_created on inference_run (created_at);

-- mirrors the agent claim filter (LOCAL rows are never claimed): keeps the
-- FOR UPDATE SKIP LOCKED probe cheap.
create index if not exists idx_inference_run_claimable
    on inference_run (required_type, created_at)
    where status = 'PENDING' and local = false;
