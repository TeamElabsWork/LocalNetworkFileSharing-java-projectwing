title: [transfer] InMemoryTransferStore: validated transitions, throttled events and cleanup
milestone: 2 - Transfer MVP
labels: module:transfer|module:concurrency|difficulty:medium|priority:mvp|help wanted
---
**Module:** `transfer` (M4), `concurrency` (M6) · **Milestone:** 2 - Transfer MVP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/concurrency-transfer-manager`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
`InMemoryTransferStore` already stores sessions and notifies listeners. Make it the reliable single source of truth: reject illegal status transitions, throttle progress events so UIs are not flooded, and clean up old finished sessions. Keep exactly ONE `TransferStore` implementation.

## Tasks
- [ ] Add a central `transition(session, newStatus)` that rejects illegal transitions (e.g. COMPLETED -> RUNNING) and logs them.
- [ ] Throttle progress events (max ~10 per second per session) while always delivering status changes.
- [ ] Add `removeFinished()` / an auto-prune policy (new method on the `TransferStore` port = contracts change, needs owner sign-off).
- [ ] Make sure listener exceptions never break the engine (already covered - keep the test).
- [ ] Thread-safety tests: many sessions updated from many threads.

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/InMemoryTransferStore.java`

## Acceptance criteria
- [ ] Illegal transitions are rejected and logged.
- [ ] A UI listener receives each status change exactly once.
- [ ] Exactly one `TransferStore` implementation exists.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #30`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
