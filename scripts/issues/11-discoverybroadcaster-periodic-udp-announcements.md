title: [discovery] DiscoveryBroadcaster: periodic UDP announcements
milestone: 1 - Discovery & TCP
labels: module:discovery|difficulty:medium|priority:mvp|help wanted
---
**Module:** `discovery` (M2) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/discovery-broadcaster`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Send the DISCOVER message every `discoveryIntervalMs` to all broadcast addresses from `NetworkUtils`. The task is scheduled by `TaskRunner` (do not create your own threads).

> **Enable it with:** `backend.discovery=udp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Open a `DatagramSocket` with broadcast enabled.
- [ ] Build the packet using `DiscoveryMessage` (deviceId, deviceName, tcpPort from `AppConfig`).
- [ ] Send to every broadcast address; log (don't crash) if one interface fails.
- [ ] Handle network changes (re-query addresses periodically).
- [ ] Close the socket on shutdown.

## Files / classes
- `modules/discovery/src/main/java/com/fileshare/discovery/DiscoveryBroadcaster.java`

## Acceptance criteria
- [ ] Packets are visible with `nc -ul 37020` / Wireshark on the LAN.
- [ ] Disconnecting Wi-Fi does not kill the broadcaster.
- [ ] No thread is created outside `TaskRunner`.

## Depends on
- #8 NetworkUtils: find usable network interfaces, local IPs and broadcast addresses
- #9 Discovery packet encoding/decoding (UDP payload)

## Definition of done
- [ ] PR merged into `main` with `Closes #11`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
