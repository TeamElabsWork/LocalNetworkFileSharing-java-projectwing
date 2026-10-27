title: [discovery] DiscoveryListener: receive packets, update registry, reply to DISCOVER
milestone: 1 - Discovery & TCP
labels: module:discovery|difficulty:medium|priority:mvp|help wanted
---
**Module:** `discovery` (M2) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/discovery-listener`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Listen on the discovery UDP port, validate each packet, ignore our own announcements (same deviceId), update `PeerDirectory` (address taken from the packet's source address, not from its payload) and answer DISCOVER with a unicast DISCOVER_RESPONSE.

> **Enable it with:** `backend.discovery=udp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Bind `DatagramSocket` with `SO_REUSEADDR`; handle 'port already in use' with a clear error.
- [ ] Receive loop that ends cleanly when the socket is closed.
- [ ] Decode with `DiscoveryMessage`; drop invalid packets silently (debug log only).
- [ ] Create/refresh `Peer` (ONLINE, `lastSeen = now`).
- [ ] Send DISCOVER_RESPONSE to the sender's address/port.
- [ ] Test locally by running two instances (different deviceId, different TCP ports).

## Files / classes
- `modules/discovery/src/main/java/com/fileshare/discovery/DiscoveryListener.java`

## Acceptance criteria
- [ ] Two instances on one machine see each other (not themselves).
- [ ] Garbage packets don't stop the loop.
- [ ] `stop()` terminates the loop immediately.

## Depends on
- #9 Discovery packet encoding/decoding (UDP payload)
- #10 PeerDirectory: change notifications for the UI and last-seen handling

## Notes
Running two instances on one host: both need `SO_REUSEADDR` on the same UDP port.

## Definition of done
- [ ] PR merged into `main` with `Closes #12`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
