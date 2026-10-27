title: [ui] JavaFX: start and stop the services cleanly (`Main --gui`)
milestone: 4 - JavaFX UI
labels: module:ui|module:app|difficulty:medium|priority:mvp|help wanted
---
**Module:** `ui` (M7), `app` (M1/M8) · **Milestone:** 4 - JavaFX UI · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/ui-wiring`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
`Main --gui` already builds the context, starts it and launches the window through `FxLauncher`. Make this robust: startup failures (port in use, etc.) must be shown to the user, and closing the window must stop everything and end the process.

## Tasks
- [ ] Show startup failures from `context.start()` in a readable error dialog/window instead of a stack trace.
- [ ] Close the context when the window closes; the JVM must exit (no stray non-daemon threads).
- [ ] Controllers receive `UiServices` only - no controller creates its own services or imports other modules.
- [ ] Smoke test: start and close the app 10 times with the real backends; no leaked ports.

## Files / classes
- `apps/fileshare-app/src/main/java/com/fileshare/app/Main.java`
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/FxLauncher.java`
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/FileShareFxApp.java`

## Acceptance criteria
- [ ] `make gui` opens the window with discovery and the engine running.
- [ ] Port-in-use produces a readable error, not a stack trace.
- [ ] Closing the window ends the process.

## Depends on
- #13 DiscoveryService lifecycle + stale peer sweeper
- #28 NetworkTransferEngine: send(), incoming request handling and approval

## Definition of done
- [ ] PR merged into `main` with `Closes #37`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
