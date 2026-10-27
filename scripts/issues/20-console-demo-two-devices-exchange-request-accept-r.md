title: [network] Console demo: two devices exchange request/accept/reject messages
milestone: 1 - Discovery & TCP
labels: module:network|difficulty:easy|priority:mvp|good first issue|help wanted
---
**Module:** `network` (M3) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** easy · **Priority:** MVP  
**Suggested branch:** `feat/tcp-handshake-demo`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Module 3's deliverable: two devices connect and exchange protocol messages before file transfer exists. Create a small demo `main` (e.g. `HandshakeDemo`): one side listens, the other connects and sends TRANSFER_REQUEST; the listener asks y/n on the console and answers ACCEPT or REJECT.

> **Enable it with:** `backend.transport=tcp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] `HandshakeDemo listen` / `HandshakeDemo send <ip> <port>` commands.
- [ ] Print every message sent and received.
- [ ] Document usage in the README.

## Files / classes
- `modules/network/src/main/java/com/fileshare/network/HandshakeDemo.java`

## Acceptance criteria
- [ ] Works between two laptops with manually entered IP.
- [ ] Rejection and acceptance are both demonstrated.

## Depends on
- #19 TcpServer: accept loop that hands connections to workers
- #16 ProtocolMessage: typed factories and per-type field validation

## Definition of done
- [ ] PR merged into `main` with `Closes #20`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
