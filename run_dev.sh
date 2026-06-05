#!/usr/bin/env bash
# Boot the harticle dev stack:
#   1. Postgres + Kafka (docker compose in infra/dev) — ports 5433, 9092
#   2. Python engine (Flask :5000, stub mode by default)
#   3. Management API (port 8080, context /api) + Frontend v2 / Nuxt 4 (port 3000)
# Re-running is idempotent — infra stays up, app processes get restarted.
# Set HARTICLE_ENGINE_STUB=0 before ./run_dev.sh to use the full ML engine (slow first run).

set -uo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT"
LOG_DIR="$ROOT/logs"
mkdir -p "$LOG_DIR"
: >"$LOG_DIR/engine.log"
: >"$LOG_DIR/management.log"
: >"$LOG_DIR/frontend.log"

# Health checks must not go through HTTP_PROXY (common on dev VMs).
export no_proxy="localhost,127.0.0.1,${no_proxy:-}"
export NO_PROXY="$no_proxy"
curl_local() {
  curl -sf --noproxy '*' --max-time "${CURL_TIMEOUT:-3}" "$@"
}

COMPOSE_FILE="$ROOT/infra/dev/docker-compose.yml"
COMPOSE_PROJECT=harticle-dev

DB_USER=harticle
DB_PASS=harticle
DB_NAME=harticle
DB_PORT=5433

MGMT_PORT="${MGMT_PORT:-8080}"
ENGINE_PORT=5000
FE_PORT=3000
FE_DIR="$ROOT/harticle-frontend-v2"
ENGINE_DIR="$ROOT/harticle-engine"
HARTICLE_ENGINE_STUB="${HARTICLE_ENGINE_STUB:-1}"
API_BASE="http://localhost:${MGMT_PORT}/api"
SITE_URL="http://localhost:${FE_PORT}"

MGMT_PID=""
FE_PID=""
ENGINE_PID=""

if ! command -v docker >/dev/null 2>&1; then
  echo "[run_dev] docker not found in PATH — install docker or run Postgres manually." >&2
  exit 1
fi

HAARTICLE_PG_CONTAINER="${COMPOSE_PROJECT}-postgres-1"

# KLS and other local stacks also bind host :5433 — stop them so harticle can start.
stop_foreign_postgres_on_port() {
  local port="$1"
  local keep="$2"
  local name

  while IFS= read -r name; do
    [ -z "$name" ] && continue
    [ "$name" = "$keep" ] && continue
    echo "[run_dev] freeing port ${port} — stopping container ${name}"
    docker stop "$name" >/dev/null 2>&1 || true
  done < <(
    {
      docker ps --format '{{.Names}}' --filter "publish=${port}" 2>/dev/null
      docker ps --format '{{.Names}}\t{{.Ports}}' 2>/dev/null \
        | awk -v p=":${port}->" '$2 ~ p { print $1 }'
    } | sort -u
  )

  # Known dev containers (may not match publish filter on all Docker versions)
  for name in kls-postgres backend-postgres-1; do
    [ "$name" = "$keep" ] && continue
    if docker ps --format '{{.Names}}' | grep -qx "$name"; then
      echo "[run_dev] freeing port ${port} — stopping container ${name}"
      docker stop "$name" >/dev/null 2>&1 || true
    fi
  done
}

stop_foreign_postgres_on_port "$DB_PORT" "$HAARTICLE_PG_CONTAINER"

echo "[run_dev] starting infra (compose project: $COMPOSE_PROJECT)"
docker compose -p "$COMPOSE_PROJECT" -f "$COMPOSE_FILE" up -d

echo -n "[run_dev] waiting for postgres"
for _ in $(seq 1 30); do
  if docker compose -p "$COMPOSE_PROJECT" -f "$COMPOSE_FILE" exec -T postgres \
    pg_isready -U "$DB_USER" -d "$DB_NAME" >/dev/null 2>&1; then
    echo " ok"
    break
  fi
  echo -n "."
  sleep 1
done

echo -n "[run_dev] waiting for kafka"
for _ in $(seq 1 30); do
  if docker compose -p "$COMPOSE_PROJECT" -f "$COMPOSE_FILE" exec -T kafka \
    rpk cluster health 2>/dev/null | grep -q 'Healthy:.*true'; then
    echo " ok"
    break
  fi
  if command -v nc >/dev/null 2>&1 && nc -z localhost 9092 2>/dev/null; then
    echo " ok"
    break
  fi
  echo -n "."
  sleep 1
done

port_listening() {
  ss -tln 2>/dev/null | grep -q ":${1} "
}

# daemon-server (root) often holds :8080 — normal kill does not work.
port_holds_foreign_service() {
  curl_local "http://127.0.0.1:${1}/" 2>/dev/null | grep -q 'daemon-example'
}

# True when Spring can bind: port free, or already running harticle management.
mgmt_port_usable() {
  local port="$1"
  if port_holds_foreign_service "$port"; then
    return 1
  fi
  if curl_local "http://127.0.0.1:${port}/api/article" >/dev/null 2>&1; then
    return 0
  fi
  if port_listening "$port"; then
    return 1
  fi
  return 0
}

kill_port() {
  local port="$1"
  port_listening "$port" || return 0

  local pids
  pids="$(lsof -t -nP -iTCP:"$port" -sTCP:LISTEN 2>/dev/null | sort -u | tr '\n' ' ')"
  if [ -z "$pids" ]; then
    pids="$(fuser -n tcp "$port" 2>/dev/null | tr -s ' ' '\n' | grep -E '^[0-9]+$' | tr '\n' ' ' || true)"
  fi
  if [ -n "$pids" ]; then
    echo "[run_dev] freeing port $port (kill: ${pids})"
    # shellcheck disable=SC2086
    kill -9 $pids 2>/dev/null || true
    sleep 0.4
  fi

  if port_listening "$port"; then
    echo "[run_dev] freeing port $port (fuser -k)"
    fuser -k "${port}/tcp" >/dev/null 2>&1 || true
    sleep 0.4
  fi

  if port_listening "$port"; then
    echo "[run_dev] freeing port $port (sudo — listener may be root-owned, e.g. daemon-server)"
    if sudo -n fuser -k "${port}/tcp" >/dev/null 2>&1; then
      sleep 0.4
    elif sudo fuser -k "${port}/tcp" >/dev/null 2>&1; then
      sleep 0.4
    else
      echo "[run_dev] warning: could not free port $port (try: sudo fuser -k ${port}/tcp)" >&2
    fi
  fi
}

ensure_mgmt_port() {
  local requested="${MGMT_PORT}"
  local candidates=("$requested")
  # daemon-server (root) permanently occupies 8080 on some dev VMs — try 18080 first.
  if [ "$requested" = "8080" ]; then
    candidates=(18080 8080)
  fi

  local port
  for port in "${candidates[@]}"; do
    kill_port "$port"
    if ! mgmt_port_usable "$port"; then
      if port_holds_foreign_service "$port"; then
        echo "[run_dev] port ${port} held by daemon-server"
      else
        echo "[run_dev] port ${port} in use by another process"
      fi
      continue
    fi
    if [ "$port" != "$requested" ]; then
      echo "[run_dev] using management port ${port} (${requested} unavailable)"
      MGMT_PORT="$port"
      API_BASE="http://localhost:${MGMT_PORT}/api"
    fi
    echo "[run_dev] management will listen on :${MGMT_PORT}"
    return 0
  done

  echo "[run_dev] cannot bind management API." >&2
  echo "[run_dev]   sudo fuser -k 8080/tcp   # free daemon-server" >&2
  echo "[run_dev]   MGMT_PORT=18080 ./run_dev.sh" >&2
  exit 1
}

kill_port "$FE_PORT"
kill_port "$ENGINE_PORT"
ensure_mgmt_port
kill_port "$MGMT_PORT"

ensure_engine_protos() {
  if [ -f "$ENGINE_DIR/harticle/generated_protos/datakubeservice_pb2.py" ]; then
    return 0
  fi
  if ! command -v protoc >/dev/null 2>&1; then
    echo "[run_dev] protoc not found — install protobuf-compiler to run the engine." >&2
    exit 1
  fi
  echo "[run_dev] generating engine protobuf stubs"
  (cd "$ENGINE_DIR" && bash generate_proto.sh)
}

ensure_engine_venv() {
  local req_file=requirements-dev.txt
  if [ "$HARTICLE_ENGINE_STUB" = "0" ]; then
    # Real ML on the local CPU: the prod requirements.txt pins torch 1.12 /
    # numpy 1.23 for python:3.8 and won't install on a modern dev venv. Use the
    # slimmer infer set (CPU-only torch + transformers) instead.
    req_file=requirements-infer.txt
  fi
  if [ ! -d "$ENGINE_DIR/.venv" ]; then
    echo "[run_dev] creating engine venv ($req_file)"
    python3 -m venv "$ENGINE_DIR/.venv"
  fi
  # shellcheck disable=SC1091
  . "$ENGINE_DIR/.venv/bin/activate"
  (
    cd "$ENGINE_DIR"
    pip install -q -U pip
    pip install -q -e .
    pip install -q -r "$req_file"
  )
}

start_engine() {
  ensure_engine_protos
  ensure_engine_venv
  # shellcheck disable=SC1091
  . "$ENGINE_DIR/.venv/bin/activate"
  export KAFKA_BOOTSTRAP_SERVERS=localhost:9092
  export KAFKA_TOPIC=data_kube_job_service
  export HARTICLE_ENGINE_STUB
  export PYTHONUNBUFFERED=1
  (
    cd "$ENGINE_DIR"
    exec python harticle/create_article.py >>"$LOG_DIR/engine.log" 2>&1
  ) &
  ENGINE_PID=$!
  echo "[run_dev] waiting for engine :${ENGINE_PORT} (log: logs/engine.log)"
  for _ in $(seq 1 45); do
    if curl_local "http://127.0.0.1:${ENGINE_PORT}/engine" >/dev/null 2>&1; then
      echo "[run_dev] engine ok (pid=$ENGINE_PID, stub=${HARTICLE_ENGINE_STUB})"
      return 0
    fi
    if ! kill -0 "$ENGINE_PID" 2>/dev/null; then
      echo "[run_dev] engine failed — process exited. Last log lines:" >&2
      tail -20 "$LOG_DIR/engine.log" >&2 || true
      return 1
    fi
    sleep 1
  done
  echo "[run_dev] engine timeout — last log lines:" >&2
  tail -20 "$LOG_DIR/engine.log" >&2 || true
  return 1
}

export POSTGRES_URL="jdbc:postgresql://localhost:${DB_PORT}/${DB_NAME}"
export POSTGRES_USER="$DB_USER"
export POSTGRES_PASSWORD="$DB_PASS"
export SPRING_PROFILES_ACTIVE=dev

if ! command -v java >/dev/null 2>&1; then
  echo "[run_dev] java not found in PATH." >&2
  exit 1
fi
if ! java -version 2>&1 | grep -qE 'version "21\.'; then
  echo "[run_dev] Java 21 required (got: $(java -version 2>&1 | head -1))" >&2
  exit 1
fi
if [ -z "${JAVA_HOME:-}" ] && [ -d /usr/lib/jvm/java-21-openjdk-amd64 ]; then
  export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
  export PATH="$JAVA_HOME/bin:$PATH"
fi

export NVM_DIR="${NVM_DIR:-$HOME/.nvm}"
# shellcheck disable=SC1091
[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"

if ! command -v nvm >/dev/null 2>&1; then
  echo "[run_dev] nvm not found — install nvm and Node 22." >&2
  exit 1
fi
nvm use 22

if ! node -v 2>/dev/null | grep -qE '^v22\.'; then
  echo "[run_dev] Node 22 required (got: $(node -v 2>/dev/null || echo none))" >&2
  exit 1
fi

if ! command -v npm >/dev/null 2>&1; then
  echo "[run_dev] npm not found in PATH." >&2
  exit 1
fi

if ! command -v mvn >/dev/null 2>&1; then
  echo "[run_dev] mvn not found in PATH." >&2
  exit 1
fi

echo "[run_dev] $(java -version 2>&1 | head -1)"
echo "[run_dev] node $(node -v)  npm $(npm -v)"

if ! command -v python3 >/dev/null 2>&1; then
  echo "[run_dev] python3 not found in PATH." >&2
  exit 1
fi
if ! start_engine; then
  exit 1
fi

cleanup() {
  echo
  echo "[run_dev] shutting down management/frontend/engine..."
  [ -n "${MGMT_PID:-}" ] && kill "$MGMT_PID" 2>/dev/null || true
  [ -n "${FE_PID:-}" ] && kill "$FE_PID" 2>/dev/null || true
  [ -n "${ENGINE_PID:-}" ] && kill "$ENGINE_PID" 2>/dev/null || true
  wait "$MGMT_PID" "$FE_PID" "$ENGINE_PID" 2>/dev/null || true
  exit 0
}
trap cleanup INT TERM

# Drop stale JDBC/Flyway sessions that hold the Postgres advisory migration lock.
release_stale_db_sessions() {
  docker compose -p "$COMPOSE_PROJECT" -f "$COMPOSE_FILE" exec -T postgres \
    psql -U "$DB_USER" -d "$DB_NAME" -v ON_ERROR_STOP=0 -c \
    "SELECT pg_terminate_backend(pid) FROM pg_stat_activity
     WHERE datname = current_database() AND pid <> pg_backend_pid();" \
    >/dev/null 2>&1 || true
}

pkill -f 'HarticleManagementApplication' 2>/dev/null || true
pkill -f 'spring-boot:run.*harticle-management' 2>/dev/null || true
sleep 0.5
release_stale_db_sessions

(
  mvn -pl harticle-management -am spring-boot:run \
    -DskipTests \
    -Dspring-boot.run.jvmArguments="-Dserver.port=${MGMT_PORT}" \
    >>"$LOG_DIR/management.log" 2>&1
) &
MGMT_PID=$!

echo "[run_dev] waiting for management :${MGMT_PORT} (log: logs/management.log)"
mgmt_ready=false
for _ in $(seq 1 120); do
  if curl_local -H "Origin: http://localhost:${FE_PORT}" \
    "http://127.0.0.1:${MGMT_PORT}/api/article" >/dev/null 2>&1; then
    mgmt_ready=true
    echo "[run_dev] management ok"
    break
  fi
  if ! kill -0 "$MGMT_PID" 2>/dev/null; then
    echo "[run_dev] management failed — process exited. Last log lines:" >&2
    tail -40 "$LOG_DIR/management.log" >&2 || true
    exit 1
  fi
  if port_holds_foreign_service "$MGMT_PORT"; then
    echo "[run_dev] management failed — port ${MGMT_PORT} has daemon-server (stale MGMT_PORT?)." >&2
    echo "[run_dev] re-run ./run_dev.sh or: MGMT_PORT=18080 ./run_dev.sh" >&2
    kill "$MGMT_PID" 2>/dev/null || true
    exit 1
  fi
  sleep 2
done
if [ "$mgmt_ready" != true ]; then
  echo "[run_dev] management timeout — last log lines:" >&2
  tail -40 "$LOG_DIR/management.log" >&2 || true
  exit 1
fi

(
  cd "$FE_DIR"
  export NUXT_PUBLIC_API_BASE="$API_BASE"
  export NUXT_PUBLIC_API_HOST="http://localhost:${MGMT_PORT}"
  export NUXT_PUBLIC_SITE_URL="$SITE_URL"
  npm run dev -- --port "$FE_PORT" >>"$LOG_DIR/frontend.log" 2>&1
) &
FE_PID=$!

echo "[run_dev] management pid=$MGMT_PID  frontend pid=$FE_PID  engine pid=$ENGINE_PID"
echo "[run_dev]   API    -> ${API_BASE}"
echo "[run_dev]   FE     -> ${SITE_URL}"
echo "[run_dev]   Engine -> http://127.0.0.1:${ENGINE_PORT}/engine (stub=${HARTICLE_ENGINE_STUB})"
echo "[run_dev] logs:"
echo "[run_dev]   tail -f logs/management.log"
echo "[run_dev]   tail -f logs/engine.log"
echo "[run_dev]   tail -f logs/frontend.log"
echo "[run_dev] press Ctrl-C to stop."

wait "$MGMT_PID" "$FE_PID"
