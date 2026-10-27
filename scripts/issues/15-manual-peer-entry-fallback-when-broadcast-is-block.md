title: [contracts] Manual peer entry (fallback when broadcast is blocked)
milestone: 1 - Discovery & TCP
labels: module:contracts|module:discovery|difficulty:easy|good first issue|help wanted
---
**Module:** `contracts` (M1), `discovery` (M2) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** easy · **Priority:** important  
**Suggested branch:** `feat/discovery-manual-peer`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Many campus/public networks block broadcast. Provide a way to add a peer by IP address and TCP port so transfers still work (the plan's early tests also use manually entered IPs). The CLI/UI `addpeer` commands come from other issues - this one provides the building blocks.

## Tasks
- [ ] Add a static factory `Peer.manual(String host, int port, String displayName)` in `contracts` (generated ID with a `manual-` prefix, ONLINE, `lastSeen = now`); validate host/port with a clear exception.
- [ ] Make `InMemoryPeerDirectory.markStaleOffline` NOT expire manual peers (document how they are recognised).
- [ ] Unit tests: invalid IPs/ports rejected, manual peer survives the stale sweep, normal peers still expire.

## Files / classes
- `modules/contracts/src/main/java/com/fileshare/contracts/model/Peer.java`
- `modules/discovery/src/main/java/com/fileshare/discovery/InMemoryPeerDirectory.java`

## Acceptance criteria
- [ ] A manually added peer stays ONLINE until removed.
- [ ] Invalid IPs/ports are rejected.
- [ ] No breaking change to `Peer`.

## Depends on
- #10 PeerDirectory: change notifications for the UI and last-seen handling

## Definition of done
- [ ] PR merged into `main` with `Closes #15`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
