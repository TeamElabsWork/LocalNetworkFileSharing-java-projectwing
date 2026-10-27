title: [network] TcpServer: accept loop that hands connections to workers
milestone: 1 - Discovery & TCP
labels: module:network|difficulty:medium|priority:mvp|help wanted
---
**Module:** `network` (M3) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/tcp-server`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Bind the configured TCP port and run an accept loop in the background. The loop only accepts; each connection is handed to the handler through `TaskRunner` so one slow peer cannot block others.

> **Enable it with:** `backend.transport=tcp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Implement `start(handler)`, `stop()`, `boundPort()` (supports port 0 for tests).
- [ ] Accept loop ends cleanly when the server socket is closed.
- [ ] Handler exceptions are caught and logged; the loop keeps running.
- [ ] Clear error if the port is already in use.
- [ ] Tests: 20 simultaneous clients, stop while accepting.

## Files / classes
- `modules/network/src/main/java/com/fileshare/network/TcpServer.java`

## Acceptance criteria
- [ ] Server accepts many connections concurrently.
- [ ] `stop()` releases the port immediately (can restart in the same JVM).

## Depends on
- #18 TcpConnection: connect, send/receive messages, raw streams, close
- #7 TaskExecutor: harden thread pools and shutdown

## Definition of done
- [ ] PR merged into `main` with `Closes #19`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
