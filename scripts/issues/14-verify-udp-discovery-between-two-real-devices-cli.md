title: [discovery] Verify UDP discovery between two real devices (CLI)
milestone: 1 - Discovery & TCP
labels: module:discovery|module:ui|difficulty:easy|priority:mvp|good first issue|help wanted
---
**Module:** `discovery` (M2), `ui` (M7) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** easy · **Priority:** MVP  
**Suggested branch:** `feat/discovery-console-demo`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Module 2's deliverable: two devices discover each other. The CLI already lists peers; switch the discovery backend to `udp` and prove it works on two real laptops, then document limits (client isolation, firewall).

> **Enable it with:** `backend.discovery=udp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Set `backend.discovery=udp` in `config/pipeline.properties` on two laptops on the same hotspot/Wi-Fi.
- [ ] Run `make demo` on both; `peers` must list the other device (name, IP, port).
- [ ] Test: start/stop one device and watch it go OFFLINE/ONLINE on the other.
- [ ] Record results and limitations in `docs/network-notes.md` (OS, network type, firewall steps).

## Files / classes
- `config/pipeline.properties`
- `docs/network-notes.md`

## Acceptance criteria
- [ ] Two laptops list each other with name and IP within a few seconds.
- [ ] `docs/network-notes.md` explains how to reproduce it.

## Depends on
- #13 DiscoveryService lifecycle + stale peer sweeper

## Definition of done
- [ ] PR merged into `main` with `Closes #14`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
