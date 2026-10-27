title: [docs] Demo checklist and rehearsal for the expo
milestone: 5 - Integration & Demo
labels: module:docs|difficulty:easy|priority:mvp|good first issue|help wanted
---
**Module:** `docs` (M8) · **Milestone:** 5 - Integration & Demo · **Difficulty:** easy · **Priority:** MVP  
**Suggested branch:** `docs/demo`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Finalize `docs/demo-checklist.md` into a repeatable, rehearsed demo plan including fallbacks.

## Tasks
- [ ] Refine the checklist from the baseline using the real UI.
- [ ] Prepare demo files (large + small) and a backup plan (manual peer, CLI).
- [ ] Rehearse twice on the real devices; note timings and problems.
- [ ] Prepare a short presentation outline (problem, architecture, live demo, lessons).

## Files / classes
- `docs/demo-checklist.md`

## Acceptance criteria
- [ ] Two full rehearsals completed without manual fixes.
- [ ] Backup plan tested.

## Depends on
- #48 Real-device testing on Wi-Fi/hotspot; firewall and isolation notes
- #44 Error feedback and UI threading audit

## Definition of done
- [ ] PR merged into `main` with `Closes #51`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
