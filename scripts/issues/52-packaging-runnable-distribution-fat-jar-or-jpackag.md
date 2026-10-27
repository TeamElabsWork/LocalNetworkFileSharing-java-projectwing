title: [app] Packaging: runnable distribution (fat jar or jpackage)
milestone: 5 - Integration & Demo
labels: module:app|difficulty:hard|help wanted
---
**Module:** `app` (M1/M8) · **Milestone:** 5 - Integration & Demo · **Difficulty:** hard · **Priority:** important  
**Suggested branch:** `chore/packaging`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Provide an easy way to run the app without an IDE: a documented `mvn package` result and, if time permits, a platform installer/app image via `jpackage`/`jlink`. JavaFX needs special handling (platform-specific natives, launcher class).

## Tasks
- [ ] Create a runnable artifact (shade/assembly or jlink image) that starts through `Launcher`.
- [ ] Test on a machine without Maven/JavaFX installed (JDK only or bundled runtime).
- [ ] Add the build steps to the README and optionally to CI (upload artifact).
- [ ] Document per-OS notes.

## Files / classes
- `apps/fileshare-app/pom.xml`
- `pom.xml`
- `.github/workflows/ci.yml`

## Acceptance criteria
- [ ] Double-click/one-command start on a clean machine.
- [ ] CI uploads the artifact (optional).

## Depends on
- #44 Error feedback and UI threading audit

## Definition of done
- [ ] PR merged into `main` with `Closes #52`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
