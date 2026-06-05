-- Fetch a remotely-trained model's files down to the management host so it can be
-- used for LOCAL (CPU) inference.
--
-- A model trained on a GPU/HPU box lands at a file:// path on THAT box; with no
-- shared mount, management can't read it. Since the agent is outbound-only,
-- management can't pull either — instead it flags the agent (model_upload_session_id,
-- mirroring reverify_requested), the agent observes it on its next heartbeat and
-- PUSHes the model files up, and management tracks progress on the session
-- (model_fetch_status). Under S3 storage both sides already share the ref, so this
-- is a no-op there.

alter table compute_resource
    add column if not exists model_upload_session_id uuid;

alter table training_session
    add column if not exists model_fetch_status varchar(16) not null default 'NONE';
