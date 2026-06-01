#!/usr/bin/env bash
#
# scrape-cli.sh - drive and test the reporter-article scraper API without the UI.
#
# The point of this tool is fast feedback on extraction rules (especially the
# regex): preview a live page, see field-by-field what matched, tweak a rule
# inline, re-run - all without persisting anything.
#
# Usage:
#   ./scrape-cli.sh sites                       # list configured sites (+ ids)
#   ./scrape-cli.sh reporters [SITE_ID]         # list reporters (optionally one site)
#   ./scrape-cli.sh articles [REPORTER_ID]      # list scraped articles in the DB
#
#   ./scrape-cli.sh preview-listing SITE_ID URL # dry-run: how many article links found
#   ./scrape-cli.sh preview-article SITE_ID URL # dry-run: per-field extraction + verdict
#       Add inline rule overrides as KEY=VALUE pairs to iterate on regex:
#       ./scrape-cli.sh preview-article SITE_ID URL titleRule='(?s)(?<=<h1>)(.+?)(?=</h1>)'
#       Override keys: baseUrl parserStrategy articleLinkRule articleLinkFilter
#                      titleRule subtitleRule contentRule dateRule reporterRule
#
#   ./scrape-cli.sh add-reporters SITE_ID FILE  # bulk-load reporters from a JSON array file
#       FILE is a JSON array: [{"reporterKey":"x","displayName":"X","pathTemplate":"/..{}"}, ...]
#
#   ./scrape-cli.sh run-sync REPORTER_ID        # scrape one reporter NOW, print summary
#   ./scrape-cli.sh run-reporter REPORTER_ID    # async scrape one reporter (202)
#   ./scrape-cli.sh run-site-sync SITE_ID       # scrape all enabled reporters of a site NOW
#   ./scrape-cli.sh run-site SITE_ID            # async scrape whole site (202)
#   ./scrape-cli.sh run-all                     # async scrape all enabled reporters (202)
#
# Env:
#   MGMT_PORT   override port (default: auto-probe 18080 then 8080)
#   API_BASE    override full base url (default: http://localhost:<port>/api)
#
set -euo pipefail

SCRIPT_NAME="$(basename "$0")"

# --- locate the API ----------------------------------------------------------
detect_base() {
  if [[ -n "${API_BASE:-}" ]]; then
    echo "$API_BASE"; return
  fi
  if [[ -n "${MGMT_PORT:-}" ]]; then
    echo "http://localhost:${MGMT_PORT}/api"; return
  fi
  # run_dev.sh prefers 18080 (root daemon often holds 8080); probe it first.
  local p
  for p in 18080 8080; do
    if curl -fsS -o /dev/null --max-time 2 "http://localhost:${p}/api/scraper/sites" 2>/dev/null; then
      echo "http://localhost:${p}/api"; return
    fi
  done
  echo "http://localhost:18080/api"  # fall back to the dev default
}

BASE="$(detect_base)"

# jq is optional but makes output readable; degrade gracefully if missing.
have_jq() { command -v jq >/dev/null 2>&1; }
pretty() { if have_jq; then jq .; else cat; fi; }

req() {
  local method="$1" path="$2"; shift 2
  curl -fsS -X "$method" "${BASE}${path}" \
    -H 'Content-Type: application/json' "$@"
}

# Build a JSON object body from KEY=VALUE override args (used by preview-*).
# First two positional args (siteId, url) are added explicitly by the caller.
overrides_to_json() {
  local site_id="$1" url="$2"; shift 2
  if have_jq; then
    local args=(-n --arg siteId "$site_id" --arg url "$url")
    local filter='{siteId:$siteId, url:$url}'
    local kv key val
    for kv in "$@"; do
      key="${kv%%=*}"; val="${kv#*=}"
      args+=(--arg "$key" "$val")
      filter="${filter%\}}, ${key}:\$${key}}"
    done
    jq "${args[@]}" "$filter"
  else
    # Minimal fallback: no overrides supported without jq.
    printf '{"siteId":"%s","url":"%s"}' "$site_id" "$url"
  fi
}

usage() { sed -n '2,32p' "$0"; }

cmd="${1:-}"; shift || true
case "$cmd" in
  sites)
    req GET /scraper/sites | pretty
    ;;
  reporters)
    if [[ -n "${1:-}" ]]; then
      req GET "/scraper/reporters?siteId=$1" | pretty
    else
      req GET /scraper/reporters | pretty
    fi
    ;;
  articles)
    if [[ -n "${1:-}" ]]; then
      req GET "/scraper/articles?reporterId=$1" | pretty
    else
      req GET /scraper/articles | pretty
    fi
    ;;
  preview-listing)
    [[ $# -ge 2 ]] || { echo "usage: $SCRIPT_NAME preview-listing SITE_ID URL" >&2; exit 2; }
    body="$(overrides_to_json "$1" "$2" "${@:3}")"
    req POST /scraper/preview/listing -d "$body" | pretty
    ;;
  preview-article)
    [[ $# -ge 2 ]] || { echo "usage: $SCRIPT_NAME preview-article SITE_ID URL [key=val ...]" >&2; exit 2; }
    body="$(overrides_to_json "$1" "$2" "${@:3}")"
    req POST /scraper/preview/article -d "$body" | pretty
    ;;
  add-reporters)
    [[ $# -ge 2 ]] || { echo "usage: $SCRIPT_NAME add-reporters SITE_ID FILE.json" >&2; exit 2; }
    [[ -f "$2" ]] || { echo "file not found: $2" >&2; exit 2; }
    req POST "/scraper/reporters/bulk/$1" --data-binary "@$2" | pretty
    ;;
  run-sync)
    [[ -n "${1:-}" ]] || { echo "usage: $SCRIPT_NAME run-sync REPORTER_ID" >&2; exit 2; }
    req POST "/scraper/run-sync/$1" | pretty
    ;;
  run-reporter)
    [[ -n "${1:-}" ]] || { echo "usage: $SCRIPT_NAME run-reporter REPORTER_ID" >&2; exit 2; }
    req POST "/scraper/run/$1"; echo
    ;;
  run-site-sync)
    [[ -n "${1:-}" ]] || { echo "usage: $SCRIPT_NAME run-site-sync SITE_ID" >&2; exit 2; }
    req POST "/scraper/run-sync/site/$1" | pretty
    ;;
  run-site)
    [[ -n "${1:-}" ]] || { echo "usage: $SCRIPT_NAME run-site SITE_ID" >&2; exit 2; }
    req POST "/scraper/run/site/$1"; echo
    ;;
  run-all)
    req POST /scraper/run; echo
    ;;
  ""|-h|--help|help)
    usage
    ;;
  *)
    echo "unknown command: $cmd" >&2
    usage >&2
    exit 2
    ;;
esac
