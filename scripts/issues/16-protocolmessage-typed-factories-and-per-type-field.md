title: [contracts] ProtocolMessage: typed factories and per-type field validation
milestone: 1 - Discovery & TCP
labels: module:contracts|module:network|difficulty:medium|priority:mvp|help wanted
---
**Module:** `contracts` (M1), `network` (M3) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/tcp-protocol-message`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
`ProtocolMessage` is a generic type+fields record. Add static factories and accessors for each message so nobody builds raw maps (`request(FileMetadata)`, `accept(id)`, `reject(id, reason)`, `complete(id)`, `hashResult(id, ok, calculated)`, `cancel(id, reason)`), plus a `validate()` that checks the required fields for each type per `docs/protocol.md`.

## Tasks
- [ ] Implement the factories and typed getters (e.g. `toFileMetadata()` for TRANSFER_REQUEST).
- [ ] Implement `validate()` throwing `ProtocolException` for missing/invalid fields (sha256 format, negative size).
- [ ] Unit tests for every message type (valid + invalid).

## Files / classes
- `modules/contracts/src/main/java/com/fileshare/contracts/protocol/ProtocolMessage.java`

## Acceptance criteria
- [ ] No other class builds `ProtocolMessage` field maps by hand.
- [ ] All tests pass.

## Depends on
- #3 Finalize the wire protocol specification (docs/protocol.md)

## Definition of done
- [ ] PR merged into `main` with `Closes #16`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
