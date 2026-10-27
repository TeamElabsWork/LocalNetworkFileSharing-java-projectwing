title: [docs] Real-device testing on Wi-Fi/hotspot; firewall and isolation notes
milestone: 5 - Integration & Demo
labels: module:docs|difficulty:medium|priority:mvp|help wanted
---
**Module:** `docs` (M8) · **Milestone:** 5 - Integration & Demo · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `docs/network-notes`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Loopback tests don't prove LAN behaviour. Test on real devices/networks and document what works.

## Tasks
- [ ] Test on: phone hotspot, home Wi-Fi, campus Wi-Fi (note if client isolation blocks it).
- [ ] Test on Windows, Linux and/or macOS where team members have them.
- [ ] Document the firewall steps per OS (allow UDP 37020 / TCP 37021).
- [ ] Verify the manual-peer fallback works when discovery is blocked.
- [ ] Write results into `docs/network-notes.md`.

## Files / classes
- `docs/network-notes.md`

## Acceptance criteria
- [ ] At least 2 network types and 2 operating systems tested and documented.

## Depends on
- #29 CLI: `addpeer` command and the first real transfer between two devices
- #44 Error feedback and UI threading audit

## Definition of done
- [ ] PR merged into `main` with `Closes #48`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
