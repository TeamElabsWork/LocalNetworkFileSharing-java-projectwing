title: [transfer] Transfer history (optional): local persistence of completed/failed transfers
milestone: 6 - Stretch goals
labels: module:transfer|module:ui|difficulty:medium|priority:stretch|help wanted
---
**Module:** `transfer` (M4), `ui` (M7) · **Milestone:** 6 - Stretch goals · **Difficulty:** medium · **Priority:** stretch  
**Suggested branch:** `feat/history`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Optional feature: keep a local record of past transfers. Keep it simple (JSON/CSV file first). SQLite/JDBC only if the team explicitly decides.

## Tasks
- [ ] Define a `TransferHistoryRepository` interface and a file-based implementation.
- [ ] Record terminal sessions (file name, peer, size, direction, status, time).
- [ ] History tab in the UI with clear-history action.
- [ ] Tests with temp files.

## Files / classes
- `modules/*/src/main/java/com/fileshare/`

## Acceptance criteria
- [ ] History survives restart.
- [ ] Corrupt history file doesn't crash the app.

## Depends on
- #42 Active transfers view: progress, speed, ETA, status, cancel

## Definition of done
- [ ] PR merged into `main` with `Closes #55`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
