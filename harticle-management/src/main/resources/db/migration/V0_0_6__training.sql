-- GPU training orchestration schema.
-- Admin registers external GPU/HPU boxes (compute_resource) and triggers
-- background fine-tune runs (training_session) over scraped data. The boxes run
-- a Python agent that connects OUTBOUND (register/heartbeat/claim/report), so
-- nothing here assumes the box is a k8s node. State is persisted (not just
-- in-memory like the scraper tracker) so it survives the agent boundary and is
-- visible for resume + multi-box monitoring.

create table if not exists compute_resource
(
    id uuid not null
        constraint compute_resource_pkey
        primary key,
    created_at           timestamp,
    updated_at           timestamp,
    version              bigint,
    name                 varchar(128) not null,
    -- CUDA (laptop) | HPU (Intel Gaudi VM)
    type                 varchar(16)  not null,
    -- OFFLINE | IDLE | BUSY | ERROR
    status               varchar(16)  not null default 'OFFLINE',
    -- SHA-256 of the one-time enrollment code (cleared after redemption) and of
    -- the per-agent bearer token (set at enrollment). Never store plaintext.
    enrollment_code_hash varchar(128),
    agent_token_hash     varchar(128),
    enrolled             boolean      not null default false,
    last_heartbeat       timestamp,
    -- agent-reported specs: gpu/hpu count, VRAM, driver + agent versions, hostname
    capabilities         jsonb,
    current_session_id   uuid,
    enabled              boolean      not null default true,
    -- Readiness gate: UNVERIFIED | VERIFYING | READY | FAILED. Only READY boxes
    -- may claim jobs. Set by the agent preflight (device id + tiny LLM workload).
    readiness            varchar(16)  not null default 'UNVERIFIED',
    readiness_detail     text,
    readiness_checked_at timestamp,
    reverify_requested   boolean      not null default false,
    constraint compute_resource_name_uk unique (name),
    constraint compute_resource_token_uk unique (agent_token_hash)
);

create table if not exists training_session
(
    id uuid not null
        constraint training_session_pkey
        primary key,
    created_at           timestamp,
    updated_at           timestamp,
    version              bigint,
    name                 varchar(255)  not null,
    base_model           varchar(512)  not null,
    dataset_spec         jsonb,
    dataset_uri          varchar(1024),
    hyperparams          jsonb,
    -- PENDING | ASSIGNED | RUNNING | STOP_REQUESTED | STOPPED | RESUMING | COMPLETED | FAILED
    status               varchar(24)   not null default 'PENDING',
    -- only a compute_resource of this type may claim the session
    required_type        varchar(16)   not null,
    stub_mode            boolean       not null default false,
    progress_percent     integer       not null default 0,
    current_epoch        double precision,
    total_epochs         double precision,
    current_step         bigint,
    total_steps          bigint,
    last_loss            double precision,
    error_message        text,
    error_type           varchar(32),
    assigned_resource_id uuid,
    checkpoint_uri       varchar(1024),
    push_to_hub          boolean       not null default false,
    output_model_ref     varchar(1024),
    last_agent_seen_at   timestamp
);

-- Claim query targets PENDING/RESUMING sessions; keep that scan cheap.
create index if not exists idx_training_session_claimable
    on training_session (status)
    where status in ('PENDING', 'RESUMING');

create table if not exists training_log
(
    id uuid not null
        constraint training_log_pkey
        primary key,
    created_at  timestamp,
    updated_at  timestamp,
    version     bigint,
    session_id  uuid    not null,
    level       varchar(16),
    message     text,
    logged_at   timestamp,
    seq         bigint  not null
);

create index if not exists idx_training_log_session_seq
    on training_log (session_id, seq);
