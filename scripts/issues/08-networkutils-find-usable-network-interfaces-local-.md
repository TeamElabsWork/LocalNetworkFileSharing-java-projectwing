title: [discovery] NetworkUtils: find usable network interfaces, local IPs and broadcast addresses
milestone: 1 - Discovery & TCP
labels: module:discovery|difficulty:medium|priority:mvp|help wanted
---
**Module:** `discovery` (M2) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/discovery-netutils`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Discovery needs to know which local addresses are usable and where to broadcast. Skip loopback, down, point-to-point and virtual interfaces (Docker, VPN) where possible. Wi-Fi hotspots often have unusual subnets, so do not hard-code `192.168.x.x`.

> **Enable it with:** `backend.discovery=udp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Implement `localIpv4Addresses()` and `broadcastAddresses()` using `NetworkInterface` / `InterfaceAddress`.
- [ ] Include `255.255.255.255` as a fallback broadcast target.
- [ ] Provide a helper to detect whether an address is one of ours (to ignore our own broadcasts).
- [ ] Make the logic testable: separate pure filtering from `NetworkInterface.getNetworkInterfaces()`.
- [ ] Unit tests for the pure parts; document manual verification on a real machine.

## Files / classes
- `modules/discovery/src/main/java/com/fileshare/discovery/NetworkUtils.java`

## Acceptance criteria
- [ ] Returns at least one address on a normal Wi-Fi connection.
- [ ] Never returns loopback or IPv6 link-local.
- [ ] No exception if no network is available (returns empty list).

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #8`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
