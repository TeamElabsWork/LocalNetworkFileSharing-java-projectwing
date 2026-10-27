title: [transfer] DownloadTask: receive, write .part, verify, finalize
milestone: 2 - Transfer MVP
labels: module:transfer|difficulty:hard|priority:mvp|help wanted
---
**Module:** `transfer` (M4) · **Milestone:** 2 - Transfer MVP · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `feat/transfer-download`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Receiver side after accepting: read exactly `size` bytes from the connection into a `.part` file (hash while receiving), wait for TRANSFER_COMPLETE, compare SHA-256 with the expected value and only then finalize. On mismatch or failure the file must never appear under its final name.

> **Enable it with:** `backend.engine=network` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Loop: read up to `bufferSize`, never beyond the remaining byte count; stop on premature EOF -> FAILED.
- [ ] Use the incremental SHA-256 accumulator.
- [ ] On match: `FileService.finalizeDownload`, status COMPLETED, send HASH_RESULT ok=true.
- [ ] On mismatch: status FAILED (reason 'integrity check failed'), delete `.part`, send HASH_RESULT ok=false.
- [ ] Delete the `.part` file on any failure/cancel.
- [ ] Update `TransferProgress` after every chunk.

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/DownloadTask.java`

## Acceptance criteria
- [ ] Corrupting one byte in a test makes the transfer fail and leaves no final file.
- [ ] Empty file transfers correctly.
- [ ] No leaked file handles.

## Depends on
- #16 ProtocolMessage: typed factories and per-type field validation
- #18 TcpConnection: connect, send/receive messages, raw streams, close
- #25 FileService: describe files, safe destinations, .part handling, finalize
- #23 FileHasher: streaming SHA-256
- #30 InMemoryTransferStore: validated transitions, throttled events and cleanup

## Definition of done
- [ ] PR merged into `main` with `Closes #27`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
