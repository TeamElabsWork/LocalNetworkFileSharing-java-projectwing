title: [app] Throughput benchmark: chunk sizes and concurrent transfers
milestone: 5 - Integration & Demo
labels: module:app|difficulty:medium|help wanted
---
**Module:** `app` (M1/M8) · **Milestone:** 5 - Integration & Demo · **Difficulty:** medium · **Priority:** important  
**Suggested branch:** `test/benchmark`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Measure instead of guessing: compare chunk sizes (8 KiB to 1 MiB), buffered streams vs `FileChannel`, hashing overhead and 1-4 concurrent transfers.

## Tasks
- [ ] Create a small benchmark harness (not part of the normal test run).
- [ ] Record throughput on loopback and on real Wi-Fi.
- [ ] Pick the default buffer size based on the data; update `AppConfig` if needed.
- [ ] Write the table and conclusion to `docs/benchmark.md`.

## Files / classes
- `docs/benchmark.md`

## Acceptance criteria
- [ ] Documented numbers and a justified default chunk size.

## Depends on
- #28 NetworkTransferEngine: send(), incoming request handling and approval
- #35 Concurrent transfers: multiple simultaneous sessions

## Definition of done
- [ ] PR merged into `main` with `Closes #49`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
