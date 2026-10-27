title: [app] Edge-case transfer tests (empty, large, duplicate names, disconnects)
milestone: 5 - Integration & Demo
labels: module:app|difficulty:medium|priority:mvp|help wanted
---
**Module:** `app` (M1/M8) · **Milestone:** 5 - Integration & Demo · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `test/edge-cases`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Systematically test the cases listed in the plan and proposal.

## Tasks
- [ ] Empty file; 1-byte file; file of exactly one chunk and chunk +/- 1.
- [ ] Large file (>= 2 GB, manual or opt-in test) - check memory use.
- [ ] Duplicate file name on the receiver; file name with spaces/Unicode.
- [ ] Receiver rejects; receiver unreachable; sender disappears mid-transfer; receiver disk full.
- [ ] Cancel at early / middle / late stage.
- [ ] Record results in a table in `docs/test-report.md`.

## Files / classes
- `modules/*/src/test/java/`
- `docs/test-report.md`

## Acceptance criteria
- [ ] Every case has a passing test or a documented known limitation.

## Depends on
- #45 Integration tests: discovery-to-transfer workflow (automated, loopback)

## Definition of done
- [ ] PR merged into `main` with `Closes #46`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
