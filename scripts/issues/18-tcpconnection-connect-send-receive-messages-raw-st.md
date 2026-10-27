title: [network] TcpConnection: connect, send/receive messages, raw streams, close
milestone: 1 - Discovery & TCP
labels: module:network|difficulty:medium|priority:mvp|help wanted
---
**Module:** `network` (M3) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/tcp-connection`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Wrap a `Socket`. Provide `connect(address, port, timeoutMs)`, an accepting constructor for the server side, `send/receive` of `ProtocolMessage`, access to the raw streams for file bytes, socket timeouts and an idempotent `close()`.

> **Enable it with:** `backend.transport=tcp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Implement connect with a connect timeout and configurable read timeout (`SO_TIMEOUT`).
- [ ] Add constructor/factory for an accepted `Socket`.
- [ ] `send` flushes; `receive` throws a clear exception on EOF/timeout.
- [ ] Expose `rawInput()`/`rawOutput()` buffered streams that share the same underlying socket (no double buffering bugs).
- [ ] Idempotent `close()`; `isClosed()`.
- [ ] Tests with a local `ServerSocket` on port 0.

## Files / classes
- `modules/network/src/main/java/com/fileshare/network/TcpConnection.java`

## Acceptance criteria
- [ ] Loopback test: two connections exchange 1000 messages.
- [ ] Closing from another thread unblocks a pending `receive()`.
- [ ] No leaked sockets (verified in test).

## Depends on
- #17 ProtocolCodec: length-prefixed encode/decode of control messages

## Definition of done
- [ ] PR merged into `main` with `Closes #18`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
