title: [transfer] Multi-file queue and folder transfer (stretch)
milestone: 6 - Stretch goals
labels: module:transfer|module:ui|difficulty:hard|priority:stretch|help wanted
---
**Module:** `transfer` (M4), `ui` (M7) · **Milestone:** 6 - Stretch goals · **Difficulty:** hard · **Priority:** stretch  
**Suggested branch:** `feat/multi-file`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Send several files (or a folder) as a queue, reusing the single-file protocol per file. Do not change the wire protocol for the MVP.

## Tasks
- [ ] Design the queue (sequential vs limited parallel).
- [ ] UI: select multiple files / drag-and-drop.
- [ ] Preserve relative paths for folders safely (same sanitisation as file names).
- [ ] Tests for queue ordering and failure of one item.

## Files / classes
- `modules/*/src/main/java/com/fileshare/`

## Acceptance criteria
- [ ] A folder with nested files arrives intact; one failure doesn't abort the rest (or policy documented).

## Depends on
- #45 Integration tests: discovery-to-transfer workflow (automated, loopback)

## Definition of done
- [ ] PR merged into `main` with `Closes #56`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
