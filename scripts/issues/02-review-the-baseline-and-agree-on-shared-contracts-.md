title: [contracts] Review the baseline and agree on shared contracts (class signatures, ports, ownership)
milestone: 0 - Foundation
labels: module:contracts|module:docs|difficulty:medium|priority:mvp|help wanted
---
**Module:** `contracts` (M1), `docs` (M8) · **Milestone:** 0 - Foundation · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `docs/shared-contracts`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Parallel work only works if everyone agrees on the shared contracts first. Everyone reads the baseline, runs `make demo`, then the team signs off the public signatures in `modules/contracts` (ports, models, `AppConfig`). Disagreements are resolved in the comments of this issue.

## Tasks
- [ ] Read `README.md`, `CONTRIBUTING.md`, `docs/architecture.md`, `docs/contracts.md`, `docs/protocol.md`, `docs/adding-a-module.md`.
- [ ] Run `make setup` and `make demo` and try `peers`, `send`, `incoming`.
- [ ] Review the ports and models in `modules/contracts` (esp. `PeerDirectory`, `TransferTransport`, `PeerConnection`, `FileService`, `TransferEngine`, `TransferStore`).
- [ ] Review the stub in your own module and comment if its signature is missing something.
- [ ] Confirm default ports (UDP 37020, TCP 37021), chunk size (64 KiB) and discovery/peer timeouts in `AppConfig`.
- [ ] Confirm who owns which module (update `CODEOWNERS` and the table in `docs/architecture.md`).
- [ ] Lead opens a PR with the agreed changes; after merge, contracts are frozen except via reviewed PR.

## Files / classes
- `docs/contracts.md`
- `modules/contracts/`
- `CODEOWNERS`

## Acceptance criteria
- [ ] Every team member commented 'reviewed' on this issue.
- [ ] Open questions are answered in the comments.
- [ ] Merged PR updates the baseline if anything changed.

## Depends on
- #1 Set up repository: branch protection, labels, milestones and project board

## Definition of done
- [ ] PR merged into `main` with `Closes #2`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
