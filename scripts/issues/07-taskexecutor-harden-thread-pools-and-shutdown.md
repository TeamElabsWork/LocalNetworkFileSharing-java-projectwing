title: [concurrency] TaskExecutor: harden thread pools and shutdown
milestone: 1 - Discovery & TCP
labels: module:concurrency|difficulty:medium|priority:mvp|help wanted
---
**Module:** `concurrency` (M6) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/concurrency-executor`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
The baseline `TaskExecutor` is a minimal wrapper. Make it production-ready. NOTE: Milestone 1 issues (discovery service, TCP server) depend on it, so do this early.

## Tasks
- [ ] Bounded/configurable transfer pool size (e.g. max concurrent transfers) with a queue; document the policy.
- [ ] Named daemon threads; uncaught exceptions are logged with thread name.
- [ ] `submit` returns a `Future` that callers can cancel; `Future.cancel(true)` interrupts the task.
- [ ] `shutdown()` waits briefly for tasks, then forces; idempotent; rejects new tasks after shutdown with a clear exception.
- [ ] Unit tests: cancellation, exception logging, shutdown, pool limit.

## Files / classes
- `modules/concurrency/src/main/java/com/fileshare/concurrency/TaskExecutor.java`

## Acceptance criteria
- [ ] Submitting 100 tasks never creates more threads than configured.
- [ ] Shutdown leaves no running non-daemon threads.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #7`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
