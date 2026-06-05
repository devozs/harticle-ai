-- Promote per-reporter training scope to first-class state on the trained model.
--
-- Reporter scope was only ever encoded inside the dataset_spec JSON blob
-- (reporterIds: [...]), which is fine for the export filter but invisible to the
-- model library and fragile for history/re-train. We want every trained model to
-- durably record WHICH reporter it was trained for so that: training history shows
-- it, re-runs stay attributed, and the inference reporter picker can offer only
-- reporters that actually have an available model.
--
-- reporter_id is a loose UUID ref (matching assigned_resource_id's style — no FK,
-- so deleting a reporter never blocks an old training row). reporter_name is a
-- snapshot of the display name at train time (same pattern as
-- scraped_article.reporter_name) so history stays readable after a rename/delete.
-- Both null = a general model trained on all reporters.

alter table training_session
    add column if not exists reporter_id   uuid,
    add column if not exists reporter_name varchar(128);

-- Backfill: a session scoped to exactly one reporter (dataset_spec.reporterIds has
-- a single element) is a per-reporter model — promote that id to the new column,
-- and snapshot the current display name where the reporter still exists.
update training_session ts
set reporter_id = (ts.dataset_spec -> 'reporterIds' ->> 0)::uuid
where ts.reporter_id is null
  and jsonb_typeof(ts.dataset_spec -> 'reporterIds') = 'array'
  and jsonb_array_length(ts.dataset_spec -> 'reporterIds') = 1;

update training_session ts
set reporter_name = r.display_name
from scrape_reporter r
where ts.reporter_id = r.id
  and ts.reporter_name is null;
