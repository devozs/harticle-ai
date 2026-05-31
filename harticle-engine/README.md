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