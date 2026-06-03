# Harticle python engine

## Local dev (via repo `run_dev.sh`)

From the harticle repo root:

```bash
./run_dev.sh
```

This starts Kafka (`localhost:9092`), the Flask engine (`localhost:5000`), management (`8080`), and the Nuxt UI (`3000`).

By default the engine runs in **stub mode** (`HARTICLE_ENGINE_STUB=1`): it publishes Kafka progress/metadata without loading ML models. For the full Hugging Face pipeline:

```bash
HARTICLE_ENGINE_STUB=0 ./run_dev.sh
```

Flow: **UI → management REST → engine `/engine/generate` → Kafka → management listener → article updated in DB/UI**.

## Training agent (GPU/HPU boxes)

The training agent (`harticle.training`) runs on a registered GPU/HPU machine. It connects
**outbound** to management (enroll → heartbeat → claim → report), so the box needs no inbound access
and works behind NAT / a corporate proxy. An admin registers the resource in the UI
(*Compute Resources → New resource*) and gets a one-time enrollment code; the agent redeems it for a
cached bearer token on first run.

### Install + run

**CUDA box (laptop / GPU host)** — pip-install the package from the repo, then run:

```bash
pip install 'harticle[training,cuda] @ git+ssh://git@github.com/devozs/harticle-ai.git#subdirectory=harticle-engine'

ENROLL_CODE=HRT-xxxx \
MGMT_URL=http://<mgmt-host>:8080/api \
AGENT_TYPE=CUDA \
python -m harticle.training
```

**Intel Gaudi VM (bare metal — recommended).** `habana_frameworks` ships with the SynapseAI
install on the box and is version-matched to the kernel driver. Run the agent directly in that
environment — the same one that runs Habana's own `mnist.py` quick-start — so the userspace stack
matches the driver exactly. (A container is only safe if its base-image SynapseAI version matches
`hl-smi` → *Driver Version*; a mismatch fails at device init with
`RuntimeError: synStatus=26 [Generic failure] Device acquire failed.` — see the Docker note below.)

```bash
git clone git@github.com:devozs/harticle-ai.git        # skip if already cloned
cd harticle-ai/harticle-engine

# Use the SAME python that runs Habana's quick-start (has habana_frameworks + a
# matched torch). Install ONLY the agent's extra deps WITHOUT touching the
# Habana torch/transformers already on the box:
$PYTHON -m pip install --no-deps -e .          # requests + the harticle package
$PYTHON -m pip install 'datasets>=2.18' 'boto3>=1.34' optimum-habana   # only needed for a real job, not preflight

# Connect + run the readiness preflight (enroll once, then heartbeat/claim).
# localhost:18080 works if you SSH-tunnel to the mgmt host; else use its URL.
ENROLL_CODE=HRT-xxxx \
MGMT_URL=http://localhost:18080/api \
AGENT_TYPE=HPU \
$PYTHON -m harticle.training
```

Preflight needs only `requests` + `transformers` + `habana_frameworks` (all present on a Habana
box); `datasets`/`boto3`/`optimum-habana` are imported lazily and only when a job actually runs, so
the box can reach **READY** before they're installed. If preflight's `model.to("hpu")` errors in lazy
mode, run with `PT_HPU_LAZY_MODE=0` (the mode the quick-start `mnist.py` uses).

**Intel Gaudi VM (container — only if a published image matches your driver).** Run the agent inside
an image built on Habana's base (`Dockerfile.agent`). Build it **on the VM** so the base image matches
the local SynapseAI driver — `Dockerfile.agent` lives in `harticle-engine/`, so clone and `cd` first
(this is the usual cause of `open Dockerfile.agent: no such file or directory`):

```bash
git clone git@github.com:devozs/harticle-ai.git        # skip if already cloned
cd harticle-ai/harticle-engine

# 1) find your SynapseAI version
hl-smi          # read the Driver Version, e.g. 1.24.1
# 2) pick the matching base tag from https://vault.habana.ai/ui/native/gaudi-docker
#    (each <synapse> dir contains a paired pytorch-installer-<pytorch>)
docker build -f Dockerfile.agent \
  --build-arg BASE_IMAGE=vault.habana.ai/gaudi-docker/<synapse>/ubuntu22.04/habanalabs/pytorch-installer-<pytorch>:latest \
  --build-arg EXTRAS=training,gaudi -t harticle-agent:gaudi .

docker run --rm --runtime=habana \
  -e HABANA_VISIBLE_DEVICES=all -e OMPI_MCA_btl_vader_single_copy_mechanism=none \
  -e ENROLL_CODE=HRT-xxxx -e MGMT_URL=http://<mgmt-host>:8080/api -e AGENT_TYPE=HPU \
  harticle-agent:gaudi
```

> **Match the driver.** The container's SynapseAI version should match the VM's driver
> (`hl-smi` → *Driver Version*); a wide gap commonly fails at device init. Browse real tags at
> <https://vault.habana.ai/ui/native/gaudi-docker> — don't guess a `pytorch-installer-<ver>` that
> may not exist.

> **Host prerequisite:** `--runtime=habana` requires the Habana container runtime on the VM
> (`habanalabs-container-runtime` registered in `/etc/docker/daemon.json`, then `systemctl restart
> docker`). Verify with `docker info | grep -i runtime`.

The CI (`.github/workflows/harticleAgentDockerBuildPush.yaml`) can also publish
`ghcr.io/devozs/harticle-agent:gaudi` via **workflow_dispatch** (pass the `gaudi_base_image` matching
your driver) — use that once your fleet standardizes on one SynapseAI version. The CUDA image
(`:cuda`, base `pytorch/pytorch:2.3.0-cuda12.1-cudnn8-runtime`, `EXTRAS=training,cuda`) is published
automatically on every push to main.

### setup.py extras
- `training` — base agent: `transformers`, `datasets`, `boto3`.
- `cuda` — adds a CUDA-capable `torch` (use the build matching your CUDA version).
- `gaudi` — `optimum-habana` (and `habana_frameworks` from the Habana base image).

### Key env vars
| var | meaning |
|-----|---------|
| `MGMT_URL` | management base URL incl. `/api`, e.g. `http://host:8080/api` |
| `ENROLL_CODE` | one-time enrollment code (only needed until a token is cached) |
| `AGENT_TYPE` | `CUDA` or `HPU` |
| `HARTICLE_TRAINING_STUB=1` | no-ML dev mode: fake training + a pass-through preflight |
| `PREFLIGHT_MODEL` | override the tiny readiness-probe model (default `sshleifer/tiny-gpt2`) |
| `AGENT_TOKEN_FILE` | where the bearer token is cached (default `~/.harticle/agent-token.json`) |
| `HF_TOKEN` | HuggingFace token, required only when a session has *push to Hub* on |

### Local dev (no GPU)
Stub mode exercises the whole flow without an accelerator:

```bash
HARTICLE_TRAINING_STUB=1 ENROLL_CODE=HRT-xxxx MGMT_URL=http://localhost:8080/api \
python -m harticle.training
```