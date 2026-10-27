title: [ui] Main window layout and dashboard
milestone: 4 - JavaFX UI
labels: module:ui|difficulty:medium|priority:mvp|help wanted
---
**Module:** `ui` (M7) · **Milestone:** 4 - JavaFX UI · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/ui-layout`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Design and implement the main window: header with local device name and IP, peer list area, active transfers area, status bar. Agree on a simple layout/wireframe first (post a screenshot or sketch in the issue).

## Tasks
- [ ] Wireframe posted in the issue and approved by the lead.
- [ ] Implement `MainController.createView()` with the agreed layout (FXML or code - decide and stay consistent).
- [ ] Show local device name, IP and the ports in use.
- [ ] Basic CSS/theme in `src/main/resources`.
- [ ] Window resizes sensibly.

## Files / classes
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/MainController.java`
- `modules/ui-javafx/src/main/resources/`

## Acceptance criteria
- [ ] App shows the dashboard with correct local info.
- [ ] Layout works at 900x600 and when maximised.

## Depends on
- #37 JavaFX: start and stop the services cleanly (`Main --gui`)

## Definition of done
- [ ] PR merged into `main` with `Closes #38`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
