title: [network] Research: PIN/QR pairing and optional encryption (future scope)
milestone: 6 - Stretch goals
labels: module:network|module:docs|difficulty:hard|priority:stretch|help wanted
---
**Module:** `network` (M3), `docs` (M8) · **Milestone:** 6 - Stretch goals · **Difficulty:** hard · **Priority:** stretch  
**Suggested branch:** `docs/research-pairing`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
SHA-256 only checks integrity. Investigate adding authentication/confidentiality later: PIN-protected sessions, QR pairing, TLS or AES-GCM payload encryption. Output is a short document - no implementation.

## Tasks
- [ ] Compare 2-3 options (TLS with self-signed certs + pinning, PIN-based key agreement, application-level AES-GCM).
- [ ] List the protocol changes each option needs.
- [ ] Recommend one with effort estimate.

## Files / classes
- `docs/research-pairing.md`

## Acceptance criteria
- [ ] Written recommendation committed as `docs/research-pairing.md`.

## Depends on
- #47 Security review: path traversal, validation and untrusted peers

## Definition of done
- [ ] PR merged into `main` with `Closes #57`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
