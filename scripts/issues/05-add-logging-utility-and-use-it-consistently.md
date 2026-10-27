title: [contracts] Add logging utility and use it consistently
milestone: 0 - Foundation
labels: module:contracts|difficulty:easy|good first issue|help wanted
---
**Module:** `contracts` (M1) · **Milestone:** 0 - Foundation · **Difficulty:** easy · **Priority:** important  
**Suggested branch:** `chore/logging`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
The proposal lists a `Logger` utility. Provide a small wrapper over `java.util.logging` (no extra dependency) so everyone logs the same way: failures include transfer ID, peer and operation.

## Tasks
- [ ] Create `util/AppLog` (or similar) returning a configured `java.util.logging.Logger` per class.
- [ ] Console format with timestamp, level, thread name and message.
- [ ] Document the logging rule in `CONTRIBUTING.md`: log errors with context, never log file contents.
- [ ] Replace any `System.out`/`printStackTrace` in baseline code.

## Files / classes
- `modules/contracts/src/main/java/com/fileshare/contracts/util/`

## Acceptance criteria
- [ ] Logging works from any class with one line.
- [ ] Thread name is visible in output.
- [ ] CONTRIBUTING updated.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #5`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
