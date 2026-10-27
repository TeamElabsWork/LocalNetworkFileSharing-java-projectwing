title: [network] TCP robustness: disconnects, timeouts and malformed input
milestone: 1 - Discovery & TCP
labels: module:network|difficulty:hard|priority:mvp|help wanted
---
**Module:** `network` (M3) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `test/tcp-robustness`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Prove the network layer survives bad conditions: peer disconnects mid-message, silent peer (timeout), garbage bytes, oversized length, connection refused.

> **Enable it with:** `backend.transport=tcp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Write tests that simulate each failure with a misbehaving fake peer (plain `Socket`).
- [ ] Fix issues found in `TcpConnection` / `TcpServer` / `ProtocolCodec`.
- [ ] Verify sockets and threads are released after each failure (no leaks).
- [ ] Document timeouts in `docs/protocol.md` section 7 if values change.

## Files / classes
- `modules/network/src/test/java/com/fileshare/network/`

## Acceptance criteria
- [ ] No test hangs (use JUnit `@Timeout`).
- [ ] Server keeps serving other clients after a bad one.

## Depends on
- #19 TcpServer: accept loop that hands connections to workers
- #17 ProtocolCodec: length-prefixed encode/decode of control messages

## Definition of done
- [ ] PR merged into `main` with `Closes #21`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
