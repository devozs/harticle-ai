"""Readiness preflight: prove a box can actually train before it claims work.

Two stages:
1. Identify the accelerator by shelling out to the vendor tool — ``nvidia-smi``
   for CUDA, ``hl-smi`` for Gaudi — and parse a short device summary.
2. Run a tiny REAL LLM workload: load a small HF causal-LM, move it to the
   device, and ``generate()`` a few tokens. This exercises the full
   transformers + CUDA/Habana path, not just "is a driver present".

Returns a ``PreflightResult`` the agent posts to management. The stub backend
short-circuits to a pass so local dev needs no GPU.
"""
import json
import logging
import os
import shutil
import subprocess
from dataclasses import dataclass, field
from typing import Optional

LOGGER = logging.getLogger(__name__)

# Small, fast model that's enough to exercise the generate path.
DEFAULT_PROBE_MODEL = os.getenv("PREFLIGHT_MODEL", "sshleifer/tiny-gpt2")


@dataclass
class PreflightResult:
    ok: bool
    detail: str
    capabilities: dict = field(default_factory=dict)

    def capabilities_json(self) -> str:
        return json.dumps(self.capabilities)


def _run(cmd: list, timeout: int = 30) -> Optional[str]:
    if shutil.which(cmd[0]) is None:
        return None
    try:
        out = subprocess.run(cmd, capture_output=True, text=True, timeout=timeout, check=True)
        return out.stdout.strip()
    except (subprocess.CalledProcessError, subprocess.TimeoutExpired) as e:
        LOGGER.warning("%s failed: %s", cmd[0], e)
        return None


def identify_nvidia() -> dict:
    out = _run(["nvidia-smi", "--query-gpu=name,driver_version,memory.total",
                "--format=csv,noheader"])
    caps = {"tool": "nvidia-smi"}
    if not out:
        caps["detected"] = False
        return caps
    lines = [l.strip() for l in out.splitlines() if l.strip()]
    devices = []
    for line in lines:
        parts = [p.strip() for p in line.split(",")]
        if len(parts) >= 3:
            devices.append({"name": parts[0], "driver": parts[1], "memory": parts[2]})
    caps["detected"] = bool(devices)
    caps["deviceCount"] = len(devices)
    caps["devices"] = devices
    return caps


def identify_gaudi() -> dict:
    # hl-smi output format varies by SynapseAI version; capture a short summary.
    out = _run(["hl-smi", "-Q", "name,driver_version,memory.total", "-f", "csv,noheader"])
    if out is None:
        out = _run(["hl-smi"])  # fall back to the default table
    caps = {"tool": "hl-smi"}
    if not out:
        caps["detected"] = False
        return caps
    caps["detected"] = True
    caps["raw"] = out[:2000]
    return caps


def _sample_workload(device_kind: str) -> dict:
    """Load a tiny causal-LM, move to the device, and generate a few tokens."""
    from transformers import AutoModelForCausalLM, AutoTokenizer
    import torch

    model_id = DEFAULT_PROBE_MODEL
    tokenizer = AutoTokenizer.from_pretrained(model_id)
    model = AutoModelForCausalLM.from_pretrained(model_id)

    if device_kind == "CUDA":
        device = "cuda" if torch.cuda.is_available() else "cpu"
    elif device_kind == "HPU":
        import habana_frameworks.torch.core as htcore  # noqa: F401
        device = "hpu"
    else:
        device = "cpu"

    model.to(device)
    if tokenizer.pad_token is None:
        tokenizer.pad_token = tokenizer.eos_token
    inputs = tokenizer("hello", return_tensors="pt").to(device)
    out = model.generate(**inputs, max_new_tokens=8)
    text = tokenizer.decode(out[0], skip_special_tokens=True)
    return {"probeModel": model_id, "device": device, "sample": text[:80]}


def run_preflight(device_kind: str, stub: bool = False) -> PreflightResult:
    """device_kind: CUDA | HPU | STUB."""
    if stub or device_kind == "STUB":
        return PreflightResult(ok=True, detail="stub preflight: no accelerator required",
                               capabilities={"backend": "STUB"})
    try:
        caps = identify_nvidia() if device_kind == "CUDA" else identify_gaudi()
        if not caps.get("detected"):
            return PreflightResult(
                ok=False,
                detail=f"{caps.get('tool')} did not detect a {device_kind} device",
                capabilities=caps,
            )
        workload = _sample_workload(device_kind)
        caps["workload"] = workload
        return PreflightResult(
            ok=True,
            detail=f"{device_kind} ready: {caps.get('tool')} ok; sample generate on {workload['device']} ok",
            capabilities=caps,
        )
    except Exception as e:  # any failure → not ready, with the reason
        LOGGER.exception("preflight failed")
        return PreflightResult(ok=False, detail=f"preflight error: {e}",
                               capabilities={"backend": device_kind, "error": str(e)})
