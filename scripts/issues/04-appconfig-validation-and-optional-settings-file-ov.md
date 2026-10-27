title: [contracts] AppConfig: validation and optional settings file override
milestone: 0 - Foundation
labels: module:contracts|difficulty:easy|good first issue|help wanted
---
**Module:** `contracts` (M1) · **Milestone:** 0 - Foundation · **Difficulty:** easy · **Priority:** important  
**Suggested branch:** `feat/app-config`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
`AppConfig.defaults()` exists. Add validation (ports 1-65535, buffer size > 0, timeouts > 0, discovery interval < peer timeout) and optionally load overrides from a properties file so settings survive restarts. (Backend selection stays in `config/pipeline.properties`.)

## Tasks
- [ ] Add a validating factory / compact constructor to `AppConfig`.
- [ ] Add `AppConfig.load(Path)` that reads a properties file and falls back to defaults for missing keys.
- [ ] Add `AppConfig.save(Path)` (used later by the Settings screen).
- [ ] Make the download directory default platform-safe (create it if missing - but not in the constructor).
- [ ] Unit tests: invalid values rejected, defaults, load/save round trip.

## Files / classes
- `modules/contracts/src/main/java/com/fileshare/contracts/config/AppConfig.java`

## Acceptance criteria
- [ ] Invalid values throw `IllegalArgumentException` with a clear message.
- [ ] Round-trip test passes.
- [ ] No signature break of existing `AppConfig` accessors.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #4`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
