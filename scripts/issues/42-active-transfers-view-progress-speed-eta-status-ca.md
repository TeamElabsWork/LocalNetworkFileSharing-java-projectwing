title: [ui] Active transfers view: progress, speed, ETA, status, cancel
milestone: 4 - JavaFX UI
labels: module:ui|difficulty:medium|priority:mvp|help wanted
---
**Module:** `ui` (M7) · **Milestone:** 4 - JavaFX UI · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/ui-transfers`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Implement `TransferController`: one row/card per transfer with file name, peer, direction, `ProgressBar`, percentage, bytes, speed, ETA, status text and a Cancel button. Updates are driven by `TransferStore` listeners / snapshots throttled to a few updates per second.

## Tasks
- [ ] Create a reusable transfer row component.
- [ ] Update via `Platform.runLater` or JavaFX properties; throttle (e.g. 5-10 Hz).
- [ ] Use `FormatUtils` for sizes, speed and ETA.
- [ ] Cancel button calls `TransferEngine.cancel` and is disabled for terminal states.
- [ ] Colour/label for COMPLETED (verified), FAILED (reason), CANCELLED, REJECTED.

## Files / classes
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/TransferController.java`

## Acceptance criteria
- [ ] Two simultaneous transfers show independent progress.
- [ ] UI stays responsive at 100+ MB/s loopback speeds (no per-chunk UI updates).

## Depends on
- #38 Main window layout and dashboard
- #30 InMemoryTransferStore: validated transitions, throttled events and cleanup
- #31 TransferProgress: moving-average speed and ETA

## Definition of done
- [ ] PR merged into `main` with `Closes #42`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
