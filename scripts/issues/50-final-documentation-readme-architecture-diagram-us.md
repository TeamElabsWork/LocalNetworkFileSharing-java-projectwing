title: [docs] Final documentation: README, architecture diagram, user guide
milestone: 5 - Integration & Demo
labels: module:docs|difficulty:easy|priority:mvp|good first issue|help wanted
---
**Module:** `docs` (M8) · **Milestone:** 5 - Integration & Demo · **Difficulty:** easy · **Priority:** MVP  
**Suggested branch:** `docs/final`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Make the repository understandable to someone who has never seen it: README with screenshots, architecture diagram, how to build/run/test, protocol overview and limitations.

## Tasks
- [ ] Update the README status section; add screenshots/GIF of the UI.
- [ ] Create a clean architecture diagram (image) and link it in `docs/architecture.md`.
- [ ] Add a short user guide (`docs/user-guide.md`): start, send, receive, settings, troubleshooting.
- [ ] Make sure `docs/protocol.md` matches the implementation.

## Files / classes
- `README.md`
- `docs/`

## Acceptance criteria
- [ ] A new team member can build and run the app following only the README.
- [ ] Docs match the code.

## Depends on
- #44 Error feedback and UI threading audit
- #3 Finalize the wire protocol specification (docs/protocol.md)

## Definition of done
- [ ] PR merged into `main` with `Closes #50`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
