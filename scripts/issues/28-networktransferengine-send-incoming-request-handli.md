title: [transfer] NetworkTransferEngine: send(), incoming request handling and approval
milestone: 2 - Transfer MVP
labels: module:transfer|difficulty:hard|priority:mvp|help wanted
---
**Module:** `transfer` (M4) · **Milestone:** 2 - Transfer MVP · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `feat/transfer-service`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
The real `TransferEngine` (backend `network`). `send(peer, file)` validates the file, builds `FileMetadata` through the `FileService`, registers a `TransferSession` in the `TransferStore`, connects through the `TransferTransport`, negotiates metadata and submits an `UploadTask`. For incoming connections it decodes the request, validates it, asks the `IncomingRequestHandler`, replies ACCEPT/REJECT and submits a `DownloadTask`.

> **Enable it with:** `backend.engine=network` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Implement `start()` (start the transport with an incoming-connection handler) and `stop()`.
- [ ] Implement `send(Peer, Path)` - returns immediately; all blocking work happens on worker threads from `TaskRunner`.
- [ ] Receiver validation exactly as in `docs/protocol.md` section 6 before asking the user.
- [ ] Use the `ApprovalDecision` returned by the handler (accept + destination dir, or reject + reason).
- [ ] A request nobody answers must time out (configurable) and be rejected.
- [ ] All errors end as FAILED sessions with a message, never as an uncaught exception.
- [ ] Develop and test against `loopback` transport + `local` file service; test one end-to-end transfer in one JVM.

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/NetworkTransferEngine.java`

## Acceptance criteria
- [ ] Loopback test: sender and receiver engines in one JVM transfer a file end to end.
- [ ] Rejection ends in REJECTED on the sender.
- [ ] UIs only use the `TransferEngine` interface + `TransferStore`.
- [ ] `SimulatedTransferEngine` still works.

## Depends on
- #26 UploadTask: send a file in chunks
- #27 DownloadTask: receive, write .part, verify, finalize
- #19 TcpServer: accept loop that hands connections to workers
- #22 ValidationUtils: sanitize file names and validate untrusted input

## Definition of done
- [ ] PR merged into `main` with `Closes #28`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
