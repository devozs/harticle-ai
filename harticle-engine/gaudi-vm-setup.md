# Gaudi VM Setup and Verification

Steps followed to bring a fresh Intel Gaudi VM up to a working PyTorch + HPU
environment, verify it, and run a Hugging Face fine-tune. The harticle training
agent runs in this same environment (see [README](README.md) → *Intel Gaudi VM
(bare metal)*).

## 1. Install the driver + software

Docs: <https://docs.habana.ai/en/latest/Installation_Guide/Driver_Installation.html>

### 1.1 Install driver and base software

```bash
wget -nv https://vault.habana.ai/artifactory/gaudi-installer/1.24.0/habanalabs-installer.sh
chmod +x habanalabs-installer.sh
./habanalabs-installer.sh install --type base
```

### 1.2 Install the PyTorch stack (torch + habana_frameworks)

> The `base` type installs only the driver/firmware — it does **not** include
> PyTorch. Install the `pytorch` type to get `torch` + `habana_frameworks`.
> By default this creates a venv at `~/habanalabs-venv`.

> **Install OS dependencies first.** The `pytorch` install aborts if system libs
> are missing (`moreutils`, `libcairo2-dev`, `libglib2.0-dev`, `libselinux1-dev`,
> `libpcre2-dev`, `libatlas-base-dev`, `google-perftools`, …) — and when it aborts
> it does **not** create the venv. Run the `dependencies` type first:

```bash
sudo ./habanalabs-installer.sh install --type dependencies   # OS libs (needs sudo)
./habanalabs-installer.sh install --type pytorch --venv      # torch + habana_frameworks venv
```

### 1.3 Verify

```bash
hl-smi                                   # device + driver version
source ~/habanalabs-venv/bin/activate    # activate the Habana python env
python -c 'import torch, habana_frameworks.torch.core as h; print("torch", torch.__version__)'
```

## 2. Quick start on bare metal

Docs: <https://docs.habana.ai/en/latest/Quick_Start_Guides/Bare_Metal_Quick_Start.html>

### 2.1 Download PyTorch Model-References

```bash
git clone https://github.com/HabanaAI/Model-References.git
cd Model-References/PyTorch/examples/computer_vision/hello_world/
export GC_KERNEL_PATH=/usr/lib/habanalabs/libtpc_kernels.so
export PYTHONPATH=$PYTHONPATH:Model-References

# Use the Habana venv python (has torch + habana_frameworks), NOT /usr/bin/python3.x
export PYTHON=~/habanalabs-venv/bin/python
$PYTHON --version

# example.py saves a checkpoint to ./checkpoints after training — create it first
# or the run ends with "RuntimeError: Parent directory ./checkpoints does not exist".
mkdir -p ~/Model-References/PyTorch/examples/computer_vision/hello_world/checkpoints
$PYTHON example.py
```

### 2.2 Training example — single Gaudi card

```bash
PT_HPU_LAZY_MODE=0 $PYTHON mnist.py --batch-size=64 --epochs=1 --lr=1.0 --gamma=0.7 --hpu --autocast --use-torch-compile
```

## 3. Fine-tuning with Hugging Face Optimum for Intel Gaudi

### 3.1 Setup

> **Activate the venv and use `PYTHONNOUSERSITE=1`.** Installing/running outside
> the venv puts packages in `~/.local` (a generic CUDA torch) and makes
> `run_glue.py` crash in `check_synapse_version()` with
> `IndexError: list index out of range` — because `pip list | grep
> habana-torch-plugin` finds nothing when the venv isn't active. Keep everything
> inside the venv so it stays self-contained and uses the Habana torch.

> **Version match:** `optimum-habana 1.20.0` is the correct pairing for SynapseAI
> **1.24.0** (mapping: `optimum-habana 1.N ↔ SynapseAI 1.(N+4)`). It pulls a
> compatible `transformers` (4.55.4). DeepSpeed is only needed for 3.3 multi-card;
> skip it for single-card (its `@1.24.0` git tag may not exist).

```bash
source ~/habanalabs-venv/bin/activate

cd ~
git clone https://github.com/huggingface/optimum-habana.git   # skip if cloned
cd optimum-habana
git checkout v1.20.0

# install INTO the venv (compatible transformers; Habana torch left untouched)
PYTHONNOUSERSITE=1 pip install optimum-habana==1.20.0
cd examples/text-classification/
PYTHONNOUSERSITE=1 pip install -r requirements.txt

# DeepSpeed — only for 3.3 multi-card. Skip for single-card.
# PYTHONNOUSERSITE=1 pip install git+https://github.com/HabanaAI/DeepSpeed.git@<existing-tag>
```

### 3.2 Single-card training

> Run with the venv active (`source ~/habanalabs-venv/bin/activate`) and
> `PYTHONNOUSERSITE=1` — same reason as 3.1.

> **`export PT_HPU_LAZY_MODE=1` first.** In SynapseAI 1.24 the runtime default
> flipped to **eager** (`0`), and the env var — not the `--use_lazy_mode` flag —
> decides the real mode. In eager mode `--use_hpu_graphs_for_inference` still
> tries to build an `HPUGraph` at the eval step, and HPU graphs work in **lazy
> mode only**, so eval crashes with
> `RuntimeError: HPUGraph class is available in lazy mode only.` Explicitly set
> the var to `1` (just *unsetting* it is not enough — the default is now eager).
> Alternatively, stay eager and drop both `--use_lazy_mode` and
> `--use_hpu_graphs_for_inference` to avoid the graph build entirely.

```bash
export PT_HPU_LAZY_MODE=1   # force lazy mode — SynapseAI 1.24 defaults to eager, which breaks HPU graphs at eval
PYTHONNOUSERSITE=1 python run_glue.py \
  --model_name_or_path bert-large-uncased-whole-word-masking \
  --gaudi_config_name Habana/bert-large-uncased-whole-word-masking \
  --task_name mrpc \
  --do_train \
  --do_eval \
  --per_device_train_batch_size 32 \
  --learning_rate 3e-5 \
  --num_train_epochs 3 \
  --max_seq_length 128 \
  --output_dir ./output/mrpc/ \
  --use_habana \
  --use_lazy_mode \
  --bf16 \
  --use_hpu_graphs_for_inference \
  --throughput_warmup_steps 3
```

### 3.3 Multi-card training

```bash
python ../gaudi_spawn.py --world_size 8 --use_mpi run_glue.py \
  --model_name_or_path bert-large-uncased-whole-word-masking \
  --gaudi_config_name Habana/bert-large-uncased-whole-word-masking \
  --task_name mrpc \
  --do_train \
  --do_eval \
  --per_device_train_batch_size 32 \
  --per_device_eval_batch_size 8 \
  --learning_rate 3e-5 \
  --num_train_epochs 3 \
  --max_seq_length 128 \
  --output_dir ./output/mrpc/ \
  --use_habana \
  --use_lazy_mode \
  --bf16 \
  --use_hpu_graphs_for_inference \
  --throughput_warmup_steps 3
```
