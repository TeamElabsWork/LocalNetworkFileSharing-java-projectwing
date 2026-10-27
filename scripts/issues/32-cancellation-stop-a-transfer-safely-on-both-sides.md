title: [transfer] Cancellation: stop a transfer safely on both sides
milestone: 3 - Concurrency & Reliability
labels: module:transfer|module:concurrency|difficulty:hard|priority:mvp|help wanted
---
**Module:** `transfer` (M4), `concurrency` (M6) · **Milestone:** 3 - Concurrency & Reliability · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `feat/concurrency-cancellation`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Cancel must not merely hide the UI item. The worker must stop, the socket must be closed, streams closed, the `.part` file removed on the receiver, the session must end as CANCELLED and the other side must be told (CANCEL message).

## Tasks
- [ ] `NetworkTransferEngine.cancel(id)` -> `Future.cancel(true)` + close the connection (unblocks blocking reads/writes).
- [ ] Send CANCEL to the peer when possible; handle receiving CANCEL on both sides.
- [ ] Receiver deletes the `.part` file; sender stops reading the file.
- [ ] Cancelling an already finished transfer is a no-op.
- [ ] Tests: cancel early, in the middle and just before the end; cancel from receiver side.

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/`

## Acceptance criteria
- [ ] No thread, socket or file handle remains after cancel (verify in tests).
- [ ] Both sides end CANCELLED.
- [ ] No `.part` file left behind.

## Depends on
- #26 UploadTask: send a file in chunks
- #27 DownloadTask: receive, write .part, verify, finalize
- #30 InMemoryTransferStore: validated transitions, throttled events and cleanup

## Definition of done
- [ ] PR merged into `main` with `Closes #32`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
