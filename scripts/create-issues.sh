#!/usr/bin/env bash
# Creates labels, milestones and ALL project issues in the current GitHub repository (unassigned).
#
# Prerequisites: GitHub CLI (https://cli.github.com), `gh auth login` done, run from a clone of the repo
# (Windows: use Git Bash).
# Usage:
#   scripts/create-issues.sh --dry-run   # show what would be created
#   scripts/create-issues.sh             # create labels, milestones, issues
#   scripts/create-issues.sh --from 23   # resume: create only issues 23..57 (after an interruption)
#
# Issues are created in numeric order, so on a FRESH repository issue #N is scripts/issues/N-*.md - the
# "Depends on" links in the bodies rely on that. Teammates comment "I'll take this" and you assign them.
set -euo pipefail

DRY_RUN=false
FROM=1
while [[ $# -gt 0 ]]; do
  case "$1" in
    --dry-run) DRY_RUN=true ;;
    --from) FROM="${2:?--from needs an issue number}"; shift ;;
    *) echo "Unknown argument: $1"; exit 2 ;;
  esac
  shift
done

cd "$(dirname "$0")"
ISSUE_DIR="issues"

LABELS=(
  "module:contracts|1d76db|Shared models, protocol types and port interfaces"
  "module:discovery|0e8a16|UDP discovery and peer directory"
  "module:network|5319e7|Transport (TCP) and wire protocol"
  "module:file|fbca04|File I/O, validation, SHA-256"
  "module:transfer|d93f0b|Transfer engine and transfer store"
  "module:concurrency|006b75|Thread pools, progress, cancellation, reliability"
  "module:ui|c2e0c6|CLI and JavaFX user interfaces"
  "module:app|bfd4f2|Wiring, config, packaging, integration"
  "module:docs|0075ca|Documentation, demo, testing campaigns"
  "difficulty:easy|7057ff|Onboarding, docs, tests, small utilities"
  "difficulty:medium|cfd3d7|One class/subsystem behind an existing interface"
  "difficulty:hard|5b2c6f|Concurrency, protocol/security-critical or cross-module work"
  "priority:mvp|b60205|Required for the MVP"
  "priority:stretch|c5def5|Only after the MVP is stable"
  "good first issue|7057ff|Small, well-scoped - good for newcomers"
  "help wanted|008672|Open for any teammate: comment to claim"
)

MILESTONES=(
  "0 - Foundation|Repo, workflow, shared contracts and the protocol spec agreed."
  "1 - Discovery & TCP|UDP discovery and TCP/protocol layers working independently (in parallel)."
  "2 - Transfer MVP|File services and the transfer engine: a file moves between two devices from the terminal."
  "3 - Concurrency & Reliability|Thread pools, progress, cancellation, timeouts and SHA-256 verification wired in."
  "4 - JavaFX UI|JavaFX interface connected to the tested services."
  "5 - Integration & Demo|End-to-end testing between real devices, documentation, packaging and demo."
  "6 - Stretch goals|Only after the MVP is stable: resume, history and other extras."
)

run() { if $DRY_RUN; then echo "[dry-run] $*"; else "$@"; fi; }

if ! $DRY_RUN; then
  command -v gh >/dev/null || { echo "gh CLI not found"; exit 1; }
  gh auth status >/dev/null 2>&1 || { echo "Run 'gh auth login' first"; exit 1; }
  existing=0
  [[ "$FROM" == "1" ]] && existing=$(gh issue list --state all --limit 1 --json number --jq 'length')
  if [[ "$existing" != "0" ]]; then
    echo "WARNING: this repository already has issues/PRs. Issue numbers in 'Depends on' links assume a fresh repo."
    read -r -p "Continue anyway? [y/N] " ans
    [[ "$ans" == "y" || "$ans" == "Y" ]] || exit 1
  fi
fi

echo "== Labels"
for entry in "${LABELS[@]}"; do
  IFS='|' read -r name color desc <<<"$entry"
  run gh label create "$name" --color "$color" --description "$desc" --force
done

echo "== Milestones"
if $DRY_RUN; then
  existing_ms=""
else
  existing_ms=$(gh api "repos/{owner}/{repo}/milestones?state=all&per_page=100" --jq '.[].title')
fi
for entry in "${MILESTONES[@]}"; do
  IFS='|' read -r title desc <<<"$entry"
  if grep -qxF "$title" <<<"$existing_ms"; then
    echo "milestone exists: $title"
  else
    run gh api "repos/{owner}/{repo}/milestones" -f title="$title" -f description="$desc" --silent
  fi
done

echo "== Issues"
for f in "$ISSUE_DIR"/*.md; do
  n=$(basename "$f" | cut -d- -f1)
  (( 10#$n < FROM )) && continue
  title=$(sed -n '1s/^title: //p' "$f")
  milestone=$(sed -n '2s/^milestone: //p' "$f")
  labels=$(sed -n '3s/^labels: //p' "$f")
  body_file=$(mktemp)
  tail -n +5 "$f" > "$body_file"
  args=(--title "$title" --body-file "$body_file" --milestone "$milestone")
  IFS='|' read -ra lab_arr <<<"$labels"
  for l in "${lab_arr[@]}"; do args+=(--label "$l"); done
  if $DRY_RUN; then
    echo "[dry-run] $(basename "$f"): $title  (milestone: $milestone; labels: $labels)"
  else
    gh issue create "${args[@]}"
    sleep 1   # stay below GitHub's secondary rate limits
  fi
  rm -f "$body_file"
done
echo "Done."
