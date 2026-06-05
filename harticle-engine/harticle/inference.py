"""Shared inference: load a trained model and generate against a prompt.

Used by two callers:
* the engine's ``POST /engine/infer`` route (LOCAL CPU path, called by management);
* the training agent's runner when it claims an INFER job (GPU/HPU path).

A model is loaded once per process and cached (single entry) so repeated requests
don't re-download / re-load. ``model_ref`` already encodes the location — an HF
Hub repo id, or a ``file://`` / ``s3://`` storage URI produced by training — so it
alone is enough to resolve the weights; ``storage_kind`` / ``model_key_prefix`` are
accepted for parity with the agent job but only used as a fallback hint.

The generation block mirrors the proven path in ``create_article.py`` (GPT-Neo
family: ``<|endoftext|>`` stop token, ``add_special_tokens=False``, CPU fallback).
"""
import logging
import os
import tempfile
from urllib.parse import urlparse

LOGGER = logging.getLogger(__name__)

STOP_TOKEN = "<|endoftext|>"

# Single-entry cache: {model_ref: (tokenizer, model, device)}. Bounded to one so a
# long-lived process testing several models doesn't accumulate them in memory.
_CACHE: dict = {}


def _is_stub() -> bool:
    return os.getenv("HARTICLE_ENGINE_STUB", "").strip() == "1"


def _looks_like_hub_id(model_ref: str) -> bool:
    """An HF repo id has no URI scheme and no filesystem path separators of a local dir."""
    return "://" not in model_ref and not model_ref.startswith("/")


def _resolve_local_dir(model_ref: str) -> str:
    """Return a local directory the weights live in, downloading from storage if needed."""
    if model_ref.startswith("file://"):
        return urlparse(model_ref).path
    if model_ref.startswith("s3://"):
        import boto3  # lazy: only s3 setups need it
        from botocore.config import Config

        p = urlparse(model_ref)
        bucket, prefix = p.netloc, p.path.lstrip("/")
        dest = tempfile.mkdtemp(prefix="harticle-model-")
        client = boto3.client(
            "s3",
            endpoint_url=os.getenv("AWS_ENDPOINT_URL"),
            config=Config(s3={"addressing_style": "path"}),
        )
        paginator = client.get_paginator("list_objects_v2")
        for page in paginator.paginate(Bucket=bucket, Prefix=prefix):
            for obj in page.get("Contents", []):
                key = obj["Key"]
                rel = os.path.relpath(key, prefix)
                target = os.path.join(dest, rel)
                os.makedirs(os.path.dirname(target), exist_ok=True)
                client.download_file(bucket, key, target)
        return dest
    # Already a local path.
    return model_ref


def load_model(model_ref: str, storage_kind: str = None, model_key_prefix: str = None):
    """Load (tokenizer, model, device), caching by model_ref. Stub mode returns None."""
    if _is_stub():
        return None
    if model_ref in _CACHE:
        return _CACHE[model_ref]

    import torch
    from transformers import AutoModelForCausalLM, AutoTokenizer

    if _looks_like_hub_id(model_ref):
        source = model_ref
        LOGGER.info("loading inference model from HF Hub: %s", source)
    else:
        source = _resolve_local_dir(model_ref)
        LOGGER.info("loading inference model from %s", source)

    tokenizer = AutoTokenizer.from_pretrained(source)
    model = AutoModelForCausalLM.from_pretrained(source, pad_token_id=tokenizer.eos_token_id)

    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    model.to(device)
    model.eval()
    LOGGER.info("model on device: %s", device)

    # Evict any prior entry (single-entry cache) before storing this one.
    _CACHE.clear()
    _CACHE[model_ref] = (tokenizer, model, device)
    return _CACHE[model_ref]


def generate(loaded, prompt: str, params: dict) -> list:
    """Generate samples for prompt. params: {temperature, maxLength, numReturnSequences}."""
    temperature = (params.get("temperature") or 50) / 100
    if temperature < 0.1:
        temperature = 0.5
    max_length = params.get("maxLength") or 512
    num_return = params.get("numReturnSequences") or 3

    if _is_stub() or loaded is None:
        return [f"[stub] generated sample {i + 1} for: {prompt}" for i in range(num_return)]

    import torch

    tokenizer, model, device = loaded
    encoded = tokenizer.encode(prompt, add_special_tokens=False, return_tensors="pt").to(device)
    input_ids = encoded if encoded.size()[-1] > 0 else None

    eff_max = max_length
    if input_ids is not None:
        eff_max = min(max_length + len(encoded[0]), 2048)

    with torch.no_grad():
        outputs = model.generate(
            input_ids,
            do_sample=True,
            max_length=eff_max,
            temperature=temperature,
            num_return_sequences=num_return,
        )

    samples = []
    for out in outputs:
        text = tokenizer.decode(out, skip_special_tokens=True)
        # Trim at the stop token if the tokenizer left it in.
        cut = text.find(STOP_TOKEN)
        if cut != -1:
            text = text[:cut]
        samples.append(text)
    return samples


def run_inference(model_ref: str, prompt: str, params: dict,
                  storage_kind: str = None, model_key_prefix: str = None) -> list:
    """Convenience: load (cached) + generate. Returns the list of sample strings."""
    loaded = load_model(model_ref, storage_kind, model_key_prefix)
    return generate(loaded, prompt, params)
