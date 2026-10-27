title: [discovery] DiscoveryService lifecycle + stale peer sweeper
milestone: 1 - Discovery & TCP
labels: module:discovery|difficulty:medium|priority:mvp|help wanted
---
**Module:** `discovery` (M2) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/discovery-service`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Tie the pieces together: `start()` runs the listener, schedules the broadcaster and a sweeper that marks peers OFFLINE (and later removes them) when they were not heard from within `peerTimeoutMs`.

> **Enable it with:** `backend.discovery=udp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Implement `start()` / `stop()` (idempotent, non-blocking) using `TaskRunner`.
- [ ] Schedule the sweeper (e.g. every second) calling `PeerDirectory.markStaleOffline`.
- [ ] Send an immediate DISCOVER on start so peers appear quickly.
- [ ] Optionally send a 'goodbye' on stop (document the decision).
- [ ] Unit/integration test with two services on loopback and a short timeout.

## Files / classes
- `modules/discovery/src/main/java/com/fileshare/discovery/UdpDiscoveryService.java`

## Acceptance criteria
- [ ] Start/stop 50 times in a loop leaks no threads or sockets.
- [ ] Killing one instance marks it OFFLINE on the other within the timeout.

## Depends on
- #11 DiscoveryBroadcaster: periodic UDP announcements
- #12 DiscoveryListener: receive packets, update registry, reply to DISCOVER
- #7 TaskExecutor: harden thread pools and shutdown

## Definition of done
- [ ] PR merged into `main` with `Closes #13`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
