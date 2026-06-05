-- Live progress for fetch-to-local model pushes.
--
-- The agent streams a model dir up one file at a time (see V0_0_9). Until now the
-- only signal was the coarse model_fetch_status (REQUESTED → UPLOADING → AVAILABLE),
-- so a large or slow push looked indistinguishable from a stuck one. These counters
-- let the monitor show "X/Y files, N/M MB" and where the bytes are coming from.
--
-- Totals are seeded by the agent's pre-walk of the model dir (sent on the first
-- file); *_done advance as each file lands. model_fetch_source is the file:// path
-- on the training box; the target is always models/{id}/ on the management host.

alter table training_session
    add column if not exists model_fetch_files_total integer,
    add column if not exists model_fetch_files_done  integer,
    add column if not exists model_fetch_bytes_total bigint,
    add column if not exists model_fetch_bytes_done  bigint,
    add column if not exists model_fetch_source      varchar(1024);
