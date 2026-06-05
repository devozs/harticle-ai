"""Run one claimed job end to end.

Wires together: backend selection, dataset download + tokenization, checkpoint
download (resume), training with the progress callback, model publish (storage +
optional HF Hub), and terminal reporting (complete / stopped / error).

The stub backend short-circuits the model/dataset steps so the full protocol can
be exercised with no accelerator.
"""
import json
import logging
import os

from .backends import make_backend
from .backends.stub_backend import StubBackend
from .dataset import build_dataset
from .progress_callback import ManagementProgressCallback
from .storage_client import StorageClient

LOGGER = logging.getLogger(__name__)


def run_job(job, client, work_dir):
    # Inference jobs are one-shot generate calls, not a training loop.
    if getattr(job, "kind", "TRAIN") == "INFER":
        return run_inference(job, client)

    session_dir = os.path.join(work_dir, job.session_id)
    output_dir = os.path.join(session_dir, "output")
    os.makedirs(output_dir, exist_ok=True)

    storage = StorageClient(job, client)
    backend = make_backend(job.backend)
    is_stub = isinstance(backend, StubBackend)

    client.report_log(job.session_id, "INFO", f"starting job on {backend.name} backend")
    callback = ManagementProgressCallback(client, storage, job.session_id)

    tokenizer = None
    if is_stub:
        trainer = backend.build_trainer(job, None, None, None, None, output_dir, [callback])
    else:
        hp = json.loads(job.hyperparams or "{}")
        context_length = hp.get("contextLength", 128)

        tokenizer, model = backend.load_tokenizer_and_model(job.base_model)

        dataset_path = os.path.join(session_dir, "dataset.jsonl")
        storage.download_dataset(dataset_path)
        train_ds = build_dataset(dataset_path, tokenizer, context_length)
        eval_ds = None

        trainer = backend.build_trainer(job, tokenizer, model, train_ds, eval_ds, output_dir, [callback])
        callback.bind_trainer(trainer)

    # Resume from a downloaded checkpoint when the job is a RESUMING pick.
    resume_path = None
    if job.resume and job.checkpoint_uri:
        resume_path = os.path.join(session_dir, "resume-checkpoint")
        storage.download_checkpoint(job.checkpoint_uri, resume_path)
        client.report_log(job.session_id, "INFO", f"resuming from {job.checkpoint_uri}")

    # Train. The callback flips state.harticle_stopped on a cooperative stop.
    if is_stub:
        trainer.train()
    else:
        trainer.train(resume_from_checkpoint=resume_path)

    if getattr(trainer.state, "harticle_stopped", False):
        client.report_stopped(job.session_id)
        client.report_log(job.session_id, "INFO", "stopped cooperatively; checkpoint retained")
        return

    # Persist the final model and report completion.
    output_ref = _publish_model(job, trainer, storage, output_dir, is_stub, client, tokenizer)
    client.complete(job.session_id, output_ref, None)


def _publish_model(job, trainer, storage, output_dir, is_stub, client, tokenizer=None):
    trainer.save_model(output_dir)
    # Save the tokenizer next to the weights so the published model dir is
    # self-contained — inference loads AutoTokenizer.from_pretrained(model_dir)
    # and would fail (vocab_file=None) on a weights-only directory.
    if tokenizer is not None:
        tokenizer.save_pretrained(output_dir)
    storage_ref = storage.upload_model(output_dir)
    client.report_log(job.session_id, "INFO", f"model uploaded to {storage_ref}")

    hub_ref = None
    if job.push_to_hub and not is_stub:
        try:
            repo_id = f"devozs/{os.path.basename(job.model_key_prefix)}"
            trainer.model.push_to_hub(repo_id)
            hub_ref = repo_id
            client.report_log(job.session_id, "INFO", f"model pushed to HF Hub: {repo_id}")
        except Exception as e:
            client.report_log(job.session_id, "WARN", f"HF Hub push failed: {e}")

    return hub_ref or storage_ref


def run_inference(job, client):
    """Run a claimed INFER job: load the trained model, generate, report the result.

    Handles its own errors and reports them via the inference result endpoint, so
    the agent loop's training-oriented error path is never used for an inference
    job. Returns normally either way (success or reported failure).
    """
    from harticle import inference

    client.report_log(job.session_id, "INFO",
                      f"running inference on {job.backend} backend (model={job.model_ref})")
    try:
        params = json.loads(job.inference_params or "{}")
        outputs = inference.run_inference(
            job.model_ref,
            job.prompt or "",
            params,
            storage_kind=job.storage_kind,
            model_key_prefix=job.model_key_prefix,
            base_model=job.base_model,
        )
        client.report_inference_result(job.session_id, outputs=outputs)
        client.report_log(job.session_id, "INFO", f"inference produced {len(outputs)} sample(s)")
    except Exception as e:
        LOGGER.exception("inference run %s failed", job.session_id)
        try:
            client.report_inference_result(job.session_id, error_type="INTERNAL", message=str(e))
        except Exception:
            LOGGER.warning("could not report inference error", exc_info=True)
