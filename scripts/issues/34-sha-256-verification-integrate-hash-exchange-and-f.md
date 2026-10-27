title: [transfer] SHA-256 verification: integrate hash exchange and failure policy
milestone: 3 - Concurrency & Reliability
labels: module:transfer|module:file|difficulty:medium|priority:mvp|help wanted
---
**Module:** `transfer` (M4), `file` (M5) · **Milestone:** 3 - Concurrency & Reliability · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/file-integrity`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Wire SHA-256 end to end: sender computes the expected hash before the request; the receiver verifies after the last byte; HASH_RESULT tells the sender the outcome; a mismatch is FAILED with 'integrity check failed' and the corrupt file is not kept (policy documented).

## Tasks
- [ ] Make sure hashing a huge file does not block the UI (it happens on a worker; show 'Preparing/Hashing' status or `CONNECTING`).
- [ ] Receiver compares in constant-looking time (`MessageDigest.isEqual`) and sends HASH_RESULT.
- [ ] Add a test hook to deliberately corrupt bytes in flight and assert FAILED.
- [ ] Document the policy for corrupt files (delete vs keep for diagnostics) in `docs/protocol.md`.
- [ ] Add the 'SHA-256 verified' result to the session so the UI can display it.

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/`
- `modules/file/src/main/java/com/fileshare/file/`

## Acceptance criteria
- [ ] Corrupted transfer is never marked COMPLETED.
- [ ] Valid transfer shows verified hash on both sides.

## Depends on
- #27 DownloadTask: receive, write .part, verify, finalize
- #26 UploadTask: send a file in chunks
- #23 FileHasher: streaming SHA-256

## Definition of done
- [ ] PR merged into `main` with `Closes #34`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
