# Admin GPU Training Orchestration for harticle

## Context

Harticle currently scrapes Israeli sports news into a PostgreSQL `scraped_article` table and runs
inference with a fixed fine-tuned model (`devozs/hebrew-gpt_neo-small-soccer-news`) via a Python
engine. There is **no way for an admin to train a new model** on the freshly scraped data, and the
only compute path assumes Kubernetes-cluster nodes.

The admin wants to harness GPU machines that are **not k8s cluster nodes** — a local laptop (CUDA)
and a remote Intel Gaudi VM (HPU) — to fine-tune an admin-selected HuggingFace base model on the
scraped data, then later use that custom model for inference.

**Outcome:** an admin can (1) register a GPU/HPU compute resource, (2) trigger a background training
session picking a HF base model + hyperparams, and (3) watch live progress with start/stop/resume —
with the trained model landing in shared storage for later inference.

### Locked decisions (from clarification)
1. **Connectivity = agent pulls (HTTP polling).** Each box runs a lightweight Python agent that
   registers, heartbeats, polls for a job, and POSTs progress/logs/results — all OUTBOUND (works
   behind NAT / the corporate proxy on this laptop). **Do not reuse the Kafka path** for this.
2. **Java orchestrates, Python executes.** All orchestration is new Java in `harticle-management`.
   The training executor is the refactored Python `harticle-engine` (Python is required for Gaudi).
3. **Full Gaudi support now.** Python executor supports BOTH backends: CUDA (transformers `Trainer`)
   and Gaudi/HPU (`optimum-habana` `GaudiTrainer` + `habana_frameworks`), selected by resource type.
4. **Pluggable shared storage**: local-fs (disk/PVC) AND S3-compatible (Nebius). Holds datasets,
   checkpoints (for resume), final models. Agent pulls big artifacts directly (presigned S3) to keep
   data off the Java app and off expensive PVCs.
5. **Agent enrollment = admin-issued one-time code.** No open registration. Everything after uses a
   per-agent bearer token.
6. **Model output = object storage (always) + optional HF Hub push** (per-session toggle).

---

## Architecture

```
Browser (admin, passphrase-gated)
   │ HTTPS
   ▼
harticle-management (Java/Spring Boot)  ── PostgreSQL (compute_resource, training_session, training_log)
   training module (NEW)                ── StorageService → local-fs | S3 (Nebius)
   ▲ HTTPS REST polling, per-agent bearer token (ALL inbound to mgmt)
   │ enroll / heartbeat / claim / progress / log / checkpoint / complete / error / dataset
   ▼
harticle-engine agent (Python)  — laptop (CUDA) & Gaudi VM (HPU)
   backends: CudaBackend | HpuBackend | StubBackend
```

Mirror the **scraper module** (gold-standard pattern) and reuse `common` conventions; agent comms is
a brand-new REST namespace, fully separate from the Kafka `data_kube_job_service` flow (untouched).

---

## 1. Data model (new JPA entities + Flyway)

Dedicated entities — **do not reuse `AsyncTask`** (it is protobuf/Kafka-coupled via `stepType`, lacks
epoch/step/loss, resource assignment, checkpoint URI, and STOP/RESUME states). Reuse the *conventions*
(`extend BaseEntity`, `@Enumerated(STRING)`, Lombok `@SuperBuilder`) and the `common.domain.ErrorType`
enum (extend it if it lacks TIMEOUT/OOM/GENERAL).

Package `com.devozs.components.harticle.training.entity`:
- **`ComputeResource`** → `compute_resource`: `name`, `type {CUDA,HPU}`, `status {OFFLINE,IDLE,BUSY,ERROR}`,
  `agentTokenHash` (unique, hash only), `enrollmentCodeHash` + `enrolled` (bool), `lastHeartbeat`,
  `capabilities` (jsonb: gpu/hpu count, VRAM, driver/agent versions, hostname), `currentSessionId` (UUID nullable),
  `enabled`.
- **`TrainingSession`** → `training_session`: `name`, `baseModel` (HF repo id), `datasetSpec` (jsonb),
  `datasetUri` (nullable), `hyperparams` (jsonb: epochs, batchSize, lr, warmup, weightDecay, saveSteps,
  contextLength), `status {PENDING,ASSIGNED,RUNNING,STOP_REQUESTED,STOPPED,RESUMING,COMPLETED,FAILED}`,
  `progressPercent`, `currentEpoch`/`totalEpochs`/`currentStep`/`totalSteps`/`lastLoss`, `errorMessage`/`errorType`,
  `requiredType {CUDA,HPU}`, `assignedResourceId` (UUID, loose-coupled like `ScrapedArticle.reporterId`),
  `checkpointUri` (nullable, drives resume), `pushToHub` (bool), `outputModelRef` (nullable),
  `lastAgentSeenAt` (stall detection).
- **`TrainingLog`** → `training_log`: `sessionId`, `level`, `message` (text), `loggedAt`, `seq`.

Domain enums (`...training.domain`): `ComputeResourceType`, `ComputeResourceStatus`, `TrainingStatus`,
`TrainingBackend {CUDA,HPU,STUB}`.

**Flyway**: `harticle-management/src/main/resources/db/migration/V0_0_6__training.sql` (follow the
`V0_0_N__name.sql` style of the existing `V0_0_4__walla_ynet.sql`; verify the highest existing version
first and bump accordingly). `id uuid pk`, `version bigint`, `created_at`, `updated_at` to match
`BaseEntity`; jsonb columns; `idx_training_log_session_seq(session_id, seq)`; partial index
`on training_session(status) where status in ('PENDING','RESUMING')`.

## 2. Agent ↔ management REST protocol

Two namespaces via constants classes (mirror `ScraperURLS`):
`TrainingURLS.URL="training"` (admin/FE) and `TrainingAgentURLS.URL="training/agent"` (agent).

**Enrollment & auth (locked: admin-issued one-time code):**
- Admin creates a `ComputeResource` in the UI → management generates a one-time **enrollment code**
  (store only `enrollmentCodeHash`), shown once.
- Agent calls `POST /training/agent/enroll {code, capabilities}` → validates code, marks `enrolled`,
  issues a per-agent **bearer token** (store `agentTokenHash`), returns token once.
- All other agent calls send `Authorization: Bearer <token>`; a `TrainingAgentAuthFilter` resolves
  token→`ComputeResource`. Whitelist `/training/**` in `SecurityConfig.java` (permitAll at Spring
  level; our filter enforces the agent token — same posture as scraper's permitAll).

**Agent endpoints:** `POST /enroll`; `POST /heartbeat {status}`→`{ack, assignedSessionId?}`;
`POST /claim`→`204`|`AgentJobDto`; `POST /sessions/{id}/progress {epoch,totalEpochs,step,totalSteps,loss,percent}`
→`{stopRequested}`; `POST /sessions/{id}/log {level,message,seq}`; `POST /sessions/{id}/checkpoint {checkpointUri}`;
`POST /sessions/{id}/complete {outputModelRef, finalCheckpointUri}`; `POST /sessions/{id}/error {errorType,message}`;
`GET /sessions/{id}/dataset` (streamed JSONL fallback when not using direct storage).

**Claim is atomic** — native query with `FOR UPDATE SKIP LOCKED` selecting one `PENDING`/`RESUMING`
session whose `requiredType` matches the resource, flipping it to `ASSIGNED` + setting
`assignedResourceId`, and the resource to `BUSY` + `currentSessionId`. Two boxes never grab the same job.

**State machine:**
`PENDING → ASSIGNED → RUNNING → COMPLETED|FAILED`; plus `RUNNING → STOP_REQUESTED → STOPPED`;
`STOPPED|FAILED → RESUMING → ASSIGNED…` on admin resume.
- **STOP** (cooperative cancel, the `ScrapeProgressTracker.requestCancel()` idiom but DB-persisted so it
  crosses the network + is visible across boxes): admin `POST /training/sessions/{id}/stop` →
  `STOP_REQUESTED`. Agent learns via the `stopRequested:true` in its next `progress` response, saves a
  checkpoint, sets `control.should_training_stop`, reports `STOPPED`.
- **RESUME**: admin `POST /training/sessions/{id}/resume` (requires non-null `checkpointUri`) → `RESUMING`;
  next `claim` picks it up; backend loads checkpoint and `trainer.train(resume_from_checkpoint=...)`.
- **Stall reaper** (`@Scheduled`): `RUNNING` with stale `lastAgentSeenAt` → `FAILED(TIMEOUT)`, free resource.

## 3. Java backend (new `training` module, mirroring scraper)

Root: `harticle-management/src/main/java/com/devozs/components/harticle/training/`
- `controller/`: `TrainingController` (FE: resources CRUD + enrollment-code issue, session CRUD,
  trigger/stop/resume, status, logs), `TrainingAgentController` (the §2 protocol), `TrainingURLS`,
  `TrainingAgentURLS`.
- `entity/`, `domain/`, `dto/` (`ComputeResourceDto`, `TrainingSessionDto`, `EnrollRequest/Response`,
  `AgentJobDto`, `ProgressReport`, `ProgressAck`, `LogLine`, `CompleteRequest`, `ErrorReport`,
  `TrainingSessionSummary`), `repository/` (`ComputeResourceRepository`, `TrainingSessionRepository`
  with the native locked-claim query, `TrainingLogRepository`).
- `service/`: `ComputeResourceService` (create+enrollment code, enroll/redeem, token hash, heartbeat,
  list, enable/disable); `TrainingSessionService` (create→PENDING, stop, resume, list, `snapshot(id)`
  for FE polling; kicks dataset export on create); `TrainingAgentService` (locked `claim`, `recordProgress`
  returning stop flag + bumping `lastAgentSeenAt`, `log`, `complete`→IDLE, `error`→IDLE);
  `TrainingProgressService` (DB-backed analogue of `ScrapeProgressTracker.snapshot()`);
  `DatasetExportService` (reuses `ScrapedArticleRepository`, **streams** rows → JSONL via `StorageService`,
  add a `Stream<ScrapedArticle>` repo method to avoid heap blowup); `TrainingReaperService` (`@Scheduled`);
  `AgentTokenService` (token + enrollment-code generation/hash/verify).
- `config/`: `TrainingProperties` (`@ConfigurationProperties("harticle.training")`: stub flag, reaper timeout,
  poll hints).

## 4. Storage abstraction

Package `...training.storage`. Interface `StorageService`:
`write(key, InputStream, len)`, `read(key)`, `exists`, `delete`, `presignGet(key, ttl)`, `resolve(key)`.
- `LocalFsStorage` (root dir = PVC/laptop dir; `resolve` → mgmt-served HTTPS path for the agent).
- `S3Storage` (AWS SDK v2 `S3Client` with Nebius `endpointOverride` + path-style; `presignGet` lets the
  agent stream datasets/checkpoints **directly**, bypassing the Java app — the efficiency win).
- `@Bean` factory selects impl by `harticle.storage.type` (`local`|`s3`). Add AWS SDK v2 `s3` to
  `harticle-management/pom.xml`.

Keys: `datasets/{sessionId}.jsonl`, `checkpoints/{sessionId}/checkpoint-{step}/`, `models/{sessionId}/`.

```yaml
harticle:
  storage:
    type: local
    local: { root: /data/harticle-training }
    s3:    { endpoint: https://storage.eu-north1.nebius.cloud, bucket: harticle-training, region: eu-north1, path-style: true }
```

## 5. Python engine refactor (`harticle-engine`)

New package `harticle/training/` (refactor `huggingface_test/train/pytourch_finetune.py`; keep a stub
like the existing `HARTICLE_ENGINE_STUB`):
- `__main__.py` (`python -m harticle.training` → `agent.run()`), `agent.py` (poll loop),
  `management_client.py` (HTTP client + bearer token + enroll), `runner.py` (job → backend+dataset+train/resume),
  `dataset.py` (JSONL→HF Dataset, model-aware tokenization/special-tokens/context-length from hyperparams),
  `progress_callback.py` (`TrainerCallback`: report step/epoch/loss, check `stopRequested`, save+upload checkpoint),
  `storage_client.py` (boto3 for S3 / fs for local/mount), `backends/{base,cuda_backend,hpu_backend,stub_backend}.py`.
- Backends by `AgentJobDto.backend`: `CudaBackend` (transformers `Trainer`, parameterized base model +
  hyperparams), `HpuBackend` (`optimum.habana` `GaudiTrainer`/`GaudiTrainingArguments` + `import
  habana_frameworks.torch.core`, `use_habana/use_lazy_mode`), `StubBackend` (fake steps, no GPU).
- Deps via `setup.py` extras: base `requests,transformers,datasets,torch,boto3`; `[gaudi]`
  `optimum-habana,habana_frameworks` (Gaudi VM runs inside Habana base image); `[cuda]` CUDA torch.

Agent loop: `enroll-if-no-token → heartbeat → claim (204→sleep) → build backend → load dataset
(presigned/mount) → trainer.train(resume_from_checkpoint?) → publish (storage always, Hub if pushToHub)
→ complete`; cooperative `StopRequested` → report STOPPED; exceptions → `error`; transient mgmt outage →
keep polling.

## 6. Frontend (harticle-frontend-v2)

Mirror scraper's store+composable+pages, reuse `useAdminAuth.ts` + `middleware/admin.ts` + the 2s polling
idiom in `stores/scraper.ts`:
- `stores/training.ts`, `composables/useTrainingApi.ts`, `types/training.ts`.
- `pages/admin/training/resources.vue` (list + online/heartbeat badge; "register resource" → shows
  one-time enrollment code + agent run instructions), `pages/admin/training/sessions.vue` (create: pick
  HF model, dataset scope, hyperparams, `pushToHub` toggle; trigger), `pages/admin/training/monitor.vue`
  (live epoch/step/loss/% + log tail via `/logs?afterSeq=` + Start/Stop/Resume).
- `components/training/SessionForm.vue`, `components/training/ResourceCard.vue`.

## 7. Inference uses the trained model

On `complete`, `outputModelRef` = storage URI `models/{sessionId}/` (always) and the HF repo id if
`pushToHub`. Management exposes the "active" model ref; the engine's generate path
(`create_article.py`, today hard-coded to `devozs/hebrew-gpt_neo-small-soccer-news`) reads a `model_ref`
— from HF Hub, or download from storage via `storage_client.py` then `from_pretrained(localDir)`. Kafka
generate flow itself is unchanged.

---

## Critical files
- Pattern to mirror: `harticle-management/src/main/java/com/devozs/components/harticle/scraper/controller/ScraperController.java`
  + the `scraper/` `entity/dto/repository/service/domain/config` layout; `scraper/service/ScrapeProgressTracker.java`.
- Reuse baseline: `common/.../entity/flow/AsyncTask.java`, `common/.../domain/TaskStatus.java`,
  `common/.../domain/ErrorType.java`, `common/.../entity/BaseEntity.java`.
- Migration style: `harticle-management/src/main/resources/db/migration/V0_0_4__walla_ynet.sql`.
- Security whitelist: `harticle-management/.../config/SecurityConfig.java`.
- Data source: `harticle-management/.../scraper/repository/ScrapedArticleRepository.java`,
  `scraper/entity/ScrapedArticle.java`.
- Python to refactor: `harticle-engine/harticle/huggingface_test/train/pytourch_finetune.py`,
  `harticle-engine/harticle/create_article.py` (stub-mode + inference reuse).
- FE pattern: `harticle-frontend-v2/stores/scraper.ts`, `composables/useScraperApi.ts`,
  `composables/useAdminAuth.ts`, `middleware/admin.ts`.
- Config: `harticle-management/src/main/resources/application-dev.yaml`, `infra/dev/docker-compose.yml`,
  `run_dev.sh`.

## Verification (end-to-end locally, no Gaudi)
1. `run_dev.sh` → postgres(5433)+redpanda; Flyway applies `V0_0_6__training.sql`.
2. `harticle.storage.type=local`, `root=/tmp/harticle-training` in `application-dev.yaml`.
3. In FE, register a CUDA resource → get enrollment code. Run stub agent on laptop:
   `HARTICLE_TRAINING_STUB=1 ENROLL_CODE=... MGMT_URL=http://localhost:8080 python -m harticle.training`.
   Confirm `enroll` → `compute_resource` row, status → IDLE on heartbeat.
4. Create+trigger a session in `sessions.vue`; verify `DatasetExportService` writes
   `datasets/{id}.jsonl` from seeded `scraped_article` rows.
5. Watch `claim → RUNNING → COMPLETED`; `monitor.vue` shows %/step/loss + streaming logs (validates DB
   persistence + snapshot).
6. **STOP**: mid-run → `STOP_REQUESTED`; next progress ack carries `stopRequested`; checkpoint appears
   under `checkpoints/{id}/`; session → STOPPED; resource → IDLE.
7. **RESUME**: → `RESUMING`; agent re-claims, resumes from checkpoint, → COMPLETED; `outputModelRef` set
   (storage URI; Hub if toggled).
8. **Real CUDA smoke**: drop stub, install `[cuda]`, run 1 epoch on a tiny dataset of
   `Norod78/hebrew-gpt_neo-small` to confirm `CudaBackend` matches legacy behavior.
9. **Reaper**: kill agent mid-RUNNING → session → FAILED(TIMEOUT), resource freed.
10. Gaudi `HpuBackend` is import-guarded; never selected on the laptop. Validated separately on the VM
    (inside the Habana base image).

## Risks / open questions (track during build)
- **Gaudi env**: `optimum-habana`/`habana_frameworks` need the Habana base Docker image + matched
  SynapseAI driver on the VM — not standalone pip-installable. Agent on the Gaudi VM runs inside that image.
- **Nebius presigned URLs + path-style**: confirm endpoint/region and presigned multipart support.
- **HF push token**: only needed when `pushToHub` is on; scope/store an HF write token on the agent then.
- **Dataset/tokenizer coupling**: different base models imply different special tokens/context length;
  consider constraining the admin's model choices initially.
- **`@Async` pool**: give dataset export a dedicated `TaskExecutor` so large exports don't starve the pool.
- **Single active session per resource** (multi-GPU concurrency out of scope unless requested).
