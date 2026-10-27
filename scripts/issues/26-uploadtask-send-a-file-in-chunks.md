title: [transfer] UploadTask: send a file in chunks
milestone: 2 - Transfer MVP
labels: module:transfer|difficulty:hard|priority:mvp|help wanted
---
**Module:** `transfer` (M4) · **Milestone:** 2 - Transfer MVP · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `feat/transfer-upload`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Sender side after the receiver sent TRANSFER_ACCEPT: stream the file through `FileChunkReader` into `PeerConnection.rawOutput()`, update `TransferProgress`, send TRANSFER_COMPLETE, wait for HASH_RESULT, set the final status.

> **Enable it with:** `backend.engine=network` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Move the session through CONNECTING -> RUNNING -> VERIFYING -> COMPLETED/FAILED.
- [ ] Read exactly `size` bytes, never more; handle a file that shrinks/changes while sending (fail with a clear message).
- [ ] Check `Thread.interrupted()` / a cancel flag between chunks so cancellation (later issue) can stop it.
- [ ] Always close the reader and connection (try-with-resources).
- [ ] Update `TransferProgress` after each chunk (cheap, no locks).

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/UploadTask.java`

## Acceptance criteria
- [ ] Sending a 1 GB file uses a small, constant amount of memory.
- [ ] Status is always terminal after `run()` returns.
- [ ] Works against `DownloadTask` in a loopback test.

## Depends on
- #16 ProtocolMessage: typed factories and per-type field validation
- #18 TcpConnection: connect, send/receive messages, raw streams, close
- #24 FileChunkReader and FileChunkWriter
- #30 InMemoryTransferStore: validated transitions, throttled events and cleanup

## Definition of done
- [ ] PR merged into `main` with `Closes #26`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
