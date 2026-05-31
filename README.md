# harticle-ai

AI-assisted Hebrew sports article generation: Nuxt frontend, Spring Boot management, Python engine (Hugging Face), Postgres, and Kafka.

## Local development

```bash
./run_dev.sh
```

- Frontend: http://localhost:3000
- Management API: http://localhost:8080/api (or port shown in script output)
- Engine: http://localhost:5000

Infrastructure (Postgres, Kafka): `infra/dev/docker-compose.yml`

Set `HARTICLE_ENGINE_STUB=0` to run the real LLM instead of the dev stub.
