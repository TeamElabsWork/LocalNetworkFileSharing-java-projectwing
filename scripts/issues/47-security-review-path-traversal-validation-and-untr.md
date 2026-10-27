title: [app] Security review: path traversal, validation and untrusted peers
milestone: 5 - Integration & Demo
labels: module:app|difficulty:hard|priority:mvp|help wanted
---
**Module:** `app` (M1/M8) · **Milestone:** 5 - Integration & Demo · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `test/security-review`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Review and attack our own code the way a malicious peer on the LAN would.

## Tasks
- [ ] Send crafted TRANSFER_REQUESTs: `../` names, absolute paths, NUL bytes, huge size, negative size, bad hash.
- [ ] Send oversized/garbage frames and slow-loris style silent connections.
- [ ] Verify nothing is written outside the destination directory and no unconfirmed file is written at all.
- [ ] Check that sockets/threads are bounded under connection floods.
- [ ] Document residual risks (no encryption, no authentication) in `README.md` and `docs/security.md`.

## Files / classes
- `docs/security.md`
- `modules/*/src/test/java/`

## Acceptance criteria
- [ ] All attack tests are automated and pass.
- [ ] `docs/security.md` lists mitigations and known limitations.

## Depends on
- #28 NetworkTransferEngine: send(), incoming request handling and approval
- #22 ValidationUtils: sanitize file names and validate untrusted input
- #21 TCP robustness: disconnects, timeouts and malformed input

## Definition of done
- [ ] PR merged into `main` with `Closes #47`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
