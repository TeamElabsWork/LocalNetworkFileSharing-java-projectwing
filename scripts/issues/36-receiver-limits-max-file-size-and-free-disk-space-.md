title: [transfer] Receiver limits: max file size and free disk space check
milestone: 3 - Concurrency & Reliability
labels: module:transfer|difficulty:easy|good first issue|help wanted
---
**Module:** `transfer` (M4) · **Milestone:** 3 - Concurrency & Reliability · **Difficulty:** easy · **Priority:** important  
**Suggested branch:** `feat/transfer-limits`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Protect the receiver from accidental resource exhaustion: reject requests above `maxFileSizeBytes` and requests that don't fit into the free disk space of the destination.

## Tasks
- [ ] Check `maxFileSizeBytes` (0 = unlimited) and `FileStore.getUsableSpace()` during request validation.
- [ ] Reply TRANSFER_REJECT with a clear reason ('file too large', 'not enough disk space').
- [ ] Unit tests with injected limits/free-space supplier.

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/NetworkTransferEngine.java`

## Acceptance criteria
- [ ] Oversized request is rejected before any byte is sent.
- [ ] Reason string is shown to the sender.

## Depends on
- #28 NetworkTransferEngine: send(), incoming request handling and approval

## Definition of done
- [ ] PR merged into `main` with `Closes #36`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
