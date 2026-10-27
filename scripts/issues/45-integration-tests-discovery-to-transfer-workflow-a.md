title: [app] Integration tests: discovery-to-transfer workflow (automated, loopback)
milestone: 5 - Integration & Demo
labels: module:app|difficulty:hard|priority:mvp|help wanted
---
**Module:** `app` (M1/M8) · **Milestone:** 5 - Integration & Demo · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `test/integration`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Automated end-to-end tests that run two complete apps in one JVM on loopback with different ports: discovery finds the peer, a file is sent, accepted, verified and saved.

## Tasks
- [ ] Add `PipelineFactory.create(AppConfig, Properties)` so tests can give each instance its own ports, device ID and download directory.
- [ ] Test harness that starts two contexts with free ports and temp directories.
- [ ] Tests: successful transfer; reject; cancel; corrupted transfer; two concurrent transfers.
- [ ] Make tests deterministic (poll with timeout, no fixed sleeps) and fast (< 30 s total).
- [ ] Run them in CI (`mvn verify`).

## Files / classes
- `apps/fileshare-app/src/main/java/com/fileshare/app/PipelineFactory.java`
- `apps/fileshare-app/src/test/java/com/fileshare/app/`

## Acceptance criteria
- [ ] `mvn verify` runs the integration tests and they pass 20 times in a row.
- [ ] Failures print useful logs.

## Depends on
- #28 NetworkTransferEngine: send(), incoming request handling and approval
- #13 DiscoveryService lifecycle + stale peer sweeper
- #32 Cancellation: stop a transfer safely on both sides
- #34 SHA-256 verification: integrate hash exchange and failure policy

## Definition of done
- [ ] PR merged into `main` with `Closes #45`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
