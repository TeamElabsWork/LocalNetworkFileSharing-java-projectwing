title: [network] Resume (stretch): protocol design and .part retention policy
milestone: 6 - Stretch goals
labels: module:network|module:docs|difficulty:hard|priority:stretch|help wanted
---
**Module:** `network` (M3), `docs` (M8) · **Milestone:** 6 - Stretch goals · **Difficulty:** hard · **Priority:** stretch  
**Suggested branch:** `feat/resume-design`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Design only - no code before the MVP is stable. Define how an interrupted transfer continues: the receiver keeps the `.part` file and reports how many bytes it has; the sender seeks to that offset; SHA-256 of the complete file still decides success.

## Tasks
- [ ] Specify RESUME_REQUEST fields (transferId, offset, maybe partial-hash) in `docs/protocol.md`.
- [ ] Decide how the receiver identifies a resumable transfer (name + size + hash + sender ID).
- [ ] Decide staleness rules (when is a `.part` file deleted?).
- [ ] Decide what is verified on resume (hash of the existing part vs. final hash only).
- [ ] Get the design approved in the issue comments.

## Files / classes
- `docs/protocol.md`

## Acceptance criteria
- [ ] Approved written design.
- [ ] No change to MVP behaviour.

## Depends on
- #45 Integration tests: discovery-to-transfer workflow (automated, loopback)

## Notes
Start only after the MVP checklist in the implementation plan is fully ticked.

## Definition of done
- [ ] PR merged into `main` with `Closes #53`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
