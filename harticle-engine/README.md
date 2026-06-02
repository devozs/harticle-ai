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

**Intel Gaudi VM** — `habana_frameworks` is not pip-installable standalone, so run the agent inside
an image built on Habana's base (see `Dockerfile.agent`).

Published images (built by `.github/workflows/harticleAgentDockerBuildPush.yaml` →
`ghcr.io/devozs/harticle-agent:{cuda,gaudi}`, an org-scoped GHCR package — no repo of that name is
required):

```bash
docker run --rm --runtime=habana -e HABANA_VISIBLE_DEVICES=all \
  -e ENROLL_CODE=HRT-xxxx -e MGMT_URL=http://<mgmt-host>:8080/api -e AGENT_TYPE=HPU \
  ghcr.io/devozs/harticle-agent:gaudi
```

> **Match the SynapseAI version.** The `:gaudi` image is built from a Habana base image that must
> match the driver on your VM. The CI builds `gaudi` only via **workflow_dispatch**, where you pass
> the correct `gaudi_base_image` input. If your VM's driver differs from the published image, build
> locally on the VM instead:
>
> ```bash
> docker build -f Dockerfile.agent \
>   --build-arg BASE_IMAGE=vault.habana.ai/gaudi-docker/<ver>/ubuntu22.04/habanalabs/pytorch-installer-<ver>:latest \
>   --build-arg EXTRAS=training,gaudi -t harticle-agent:gaudi .
> ```

> **Host prerequisite:** `--runtime=habana` requires the Habana container runtime on the VM
> (`habanalabs-container-runtime` registered in `/etc/docker/daemon.json`, then `systemctl restart
> docker`). Verify with `docker info | grep -i runtime`.

(The same `Dockerfile.agent` builds the CUDA image with
`--build-arg BASE_IMAGE=pytorch/pytorch:2.3.0-cuda12.1-cudnn8-runtime --build-arg EXTRAS=training,cuda`;
CI publishes it as `:cuda` on every push to main.)

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