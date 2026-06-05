-- Auto-fetch a trained model to the management host on successful completion.
--
-- A model trained on a remote GPU/HPU box lands at a file:// path on THAT box and
-- isn't usable for LOCAL CPU inference until its files are pushed here (see
-- V0_0_9). Doing that by hand after every run is easy to forget, so a session can
-- opt in to fetching automatically the moment it completes. Default ON: the common
-- intent is "train it, then let me test it locally".
--
-- A no-op when the model is already reachable (an s3/hub ref, or a local-fs run),
-- so enabling it for those costs nothing.

alter table training_session
    add column if not exists auto_fetch_local boolean not null default true;
