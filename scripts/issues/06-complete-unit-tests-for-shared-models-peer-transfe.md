title: [contracts] Complete unit tests for shared models (Peer, TransferSession, TransferStatus)
milestone: 0 - Foundation
labels: module:contracts|difficulty:easy|good first issue|help wanted
---
**Module:** `contracts` (M1) · **Milestone:** 0 - Foundation · **Difficulty:** easy · **Priority:** important  
**Suggested branch:** `test/models`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
The baseline has `ModelTest`, `FormatUtilsTest` and `ComponentRegistryTest` in `modules/contracts`. Add the missing tests for the rest of the shared model so later contract changes cannot silently break it.

## Tasks
- [ ] `Peer`: more validation cases (null fields), `withLastSeen` does not mutate the original.
- [ ] `TransferSession`: thread-safe status visibility (simple concurrency test), `reject()` behaviour.
- [ ] `TransferStatus.isTerminal()` for every value.
- [ ] `ProtocolMessage`: null fields map handled, `of(...)` factory.
- [ ] `BackendContext.intOption` with missing/blank/invalid values.

## Files / classes
- `modules/contracts/src/test/java/com/fileshare/contracts/`

## Acceptance criteria
- [ ] `mvn test` passes.
- [ ] Every public method of the model package is exercised.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #6`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
