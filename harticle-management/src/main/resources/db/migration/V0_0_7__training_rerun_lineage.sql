-- Re-run lineage for training sessions.
-- A failed/stopped run can be re-run as a NEW session that copies the original's
-- exact config and reuses its dataset_uri (no re-export). The original row is
-- never mutated, so the attempt history is preserved and walkable: each re-run
-- points at its parent and carries an incrementing attempt_number, letting the
-- UI travel back over previous (failed) attempts.

alter table training_session
    add column if not exists parent_session_id uuid,
    add column if not exists attempt_number integer not null default 1;

-- Walk a session's attempt chain (children of a given parent) cheaply.
create index if not exists idx_training_session_parent
    on training_session (parent_session_id);
