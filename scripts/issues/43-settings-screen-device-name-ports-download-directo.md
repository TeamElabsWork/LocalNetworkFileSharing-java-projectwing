title: [ui] Settings screen (device name, ports, download directory)
milestone: 4 - JavaFX UI
labels: module:ui|difficulty:easy|good first issue|help wanted
---
**Module:** `ui` (M7) · **Milestone:** 4 - JavaFX UI · **Difficulty:** easy · **Priority:** important  
**Suggested branch:** `feat/ui-settings`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Let the user change device name, discovery/TCP ports and download directory; persist with `AppConfig.save` and apply after restart (or restart the services).

## Tasks
- [ ] Settings dialog with validation (ports, writable directory).
- [ ] Directory chooser for the download folder.
- [ ] Persist with the config issue's save/load; show 'restart required' where needed.

## Files / classes
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/`

## Acceptance criteria
- [ ] Changes survive an app restart.
- [ ] Invalid input is rejected inline.

## Depends on
- #38 Main window layout and dashboard
- #4 AppConfig: validation and optional settings file override

## Definition of done
- [ ] PR merged into `main` with `Closes #43`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
