title: [transfer] Timeouts and failure handling for network and disk errors
milestone: 3 - Concurrency & Reliability
labels: module:transfer|module:concurrency|difficulty:hard|priority:mvp|help wanted
---
**Module:** `transfer` (M4), `concurrency` (M6) · **Milestone:** 3 - Concurrency & Reliability · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `feat/concurrency-failures`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Make failure a normal, handled outcome: peer disappears, Wi-Fi drops, disk full, file deleted during send, unreadable source, permission denied.

## Tasks
- [ ] Set socket timeouts (connect/read) from `AppConfig` and fail the transfer with a readable message.
- [ ] Map exceptions (`SocketTimeoutException`, `SocketException`, `IOException`, `SecurityException`, `ProtocolException`) to FAILED + message.
- [ ] Guarantee cleanup in `finally`: connection, streams, `.part` file, executor slot.
- [ ] Add a 'no progress for N seconds' watchdog for stalled transfers.
- [ ] Tests with a fake peer that stalls / disconnects / sends garbage mid-transfer.

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/`

## Acceptance criteria
- [ ] No failure leaves a session in RUNNING forever.
- [ ] Disk-full simulation (small tmpfs) fails cleanly.

## Depends on
- #26 UploadTask: send a file in chunks
- #27 DownloadTask: receive, write .part, verify, finalize

## Definition of done
- [ ] PR merged into `main` with `Closes #33`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
