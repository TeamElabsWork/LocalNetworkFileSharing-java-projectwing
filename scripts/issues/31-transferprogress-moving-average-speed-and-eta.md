title: [contracts] TransferProgress: moving-average speed and ETA
milestone: 3 - Concurrency & Reliability
labels: module:contracts|module:concurrency|difficulty:medium|priority:mvp|help wanted
---
**Module:** `contracts` (M1), `concurrency` (M6) · **Milestone:** 3 - Concurrency & Reliability · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/concurrency-progress`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
The baseline computes percentage and an overall average speed. Replace speed with a moving average over a recent window and add ETA = remaining bytes / smoothed speed. Must stay cheap on the hot path (called per chunk) and thread-safe. Optionally expose JavaFX-friendly values (or provide a polling snapshot object).

## Tasks
- [ ] Add `etaSeconds()` (return -1 / empty when unknown or speed is zero).
- [ ] Moving average over e.g. the last 3-5 seconds, sampled on `add()` or by a periodic sampler.
- [ ] Add an immutable `ProgressSnapshot` (percentage, bytes, total, speed, eta) to hand to the UI.
- [ ] Handle 0-byte files, huge files (long arithmetic) and a stalled transfer (speed -> 0, ETA unknown).
- [ ] Unit tests using an injectable clock (`LongSupplier`/`Clock`) - no `Thread.sleep` in tests.

## Files / classes
- `modules/contracts/src/main/java/com/fileshare/contracts/model/TransferProgress.java`
- `modules/contracts/src/test/java/com/fileshare/contracts/TransferProgressTest.java`

## Acceptance criteria
- [ ] Speed doesn't jump wildly between updates.
- [ ] ETA decreases monotonically for a constant-speed test.
- [ ] No locks in `add()`.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Notes
TransferProgress is a SHARED class - extend the existing one; do not create a second implementation.

## Definition of done
- [ ] PR merged into `main` with `Closes #31`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
