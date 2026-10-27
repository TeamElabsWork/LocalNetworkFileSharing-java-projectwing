title: [ui] Error feedback and UI threading audit
milestone: 4 - JavaFX UI
labels: module:ui|difficulty:medium|help wanted
---
**Module:** `ui` (M7) · **Milestone:** 4 - JavaFX UI · **Difficulty:** medium · **Priority:** important  
**Suggested branch:** `feat/ui-feedback`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Final UI hardening: consistent user-visible errors and a review that no network/disk work or blocking call runs on the JavaFX Application Thread.

## Tasks
- [ ] Central helper for error dialogs / toast messages (human-readable text, details optional).
- [ ] Audit all `ui` and `app` code for blocking calls; add a debug assertion helper (`assertNotFxThread()`) used in services.
- [ ] Verify all UI updates from workers use `Platform.runLater` or properties.
- [ ] Test with a throttled/slow network and a very large file.

## Files / classes
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/`

## Acceptance criteria
- [ ] Audit findings documented in the PR.
- [ ] UI never freezes during discovery, connecting, hashing or transfer.

## Depends on
- #42 Active transfers view: progress, speed, ETA, status, cancel
- #40 File chooser and 'Send file' flow
- #41 Incoming request dialog (accept / reject, choose destination)

## Definition of done
- [ ] PR merged into `main` with `Closes #44`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
