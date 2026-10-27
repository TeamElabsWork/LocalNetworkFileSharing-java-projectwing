title: [transfer] Concurrent transfers: multiple simultaneous sessions
milestone: 3 - Concurrency & Reliability
labels: module:transfer|module:concurrency|difficulty:hard|priority:mvp|help wanted
---
**Module:** `transfer` (M4), `concurrency` (M6) · **Milestone:** 3 - Concurrency & Reliability · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `feat/concurrency-multi-transfer`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Verify and fix multi-transfer behaviour: several uploads and downloads at the same time, to the same and to different peers, with independent progress and status.

## Tasks
- [ ] Test 3-5 simultaneous transfers in one JVM (loopback) with different file sizes.
- [ ] Verify progress/speed/ETA are per session and don't interfere.
- [ ] Check the pool limit: extra transfers wait in QUEUED/PENDING and start when a slot frees.
- [ ] Fix any shared-state bugs found (`TransferStore`, `TransferProgress`, `FileService` temp-name collisions).
- [ ] Document the maximum concurrent transfers setting.

## Files / classes
- `modules/transfer/src/test/java/com/fileshare/transfer/`

## Acceptance criteria
- [ ] All files arrive intact with correct hashes.
- [ ] One failing transfer does not affect the others.

## Depends on
- #28 NetworkTransferEngine: send(), incoming request handling and approval
- #31 TransferProgress: moving-average speed and ETA
- #32 Cancellation: stop a transfer safely on both sides

## Definition of done
- [ ] PR merged into `main` with `Closes #35`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
