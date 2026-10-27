title: [ui] Peer list bound to PeerDirectory (device controller)
milestone: 4 - JavaFX UI
labels: module:ui|difficulty:medium|priority:mvp|help wanted
---
**Module:** `ui` (M7) · **Milestone:** 4 - JavaFX UI · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/ui-peer-list`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Show discovered devices (name, IP, port, ONLINE/OFFLINE) in a `ListView`/`TableView` backed by an `ObservableList` that is updated from `PeerDirectory` events on the JavaFX thread.

## Tasks
- [ ] Implement `DeviceController` that subscribes to the directory listener.
- [ ] Update the list only via `Platform.runLater` (never from the discovery thread).
- [ ] Visual difference for OFFLINE peers; selection keeps working while the list updates.
- [ ] 'Add peer manually' dialog (IP + port) using the manual-peer feature.
- [ ] Test with two real devices.

## Files / classes
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/DeviceController.java`

## Acceptance criteria
- [ ] Peers appear/disappear within a few seconds without freezing the UI.
- [ ] Selection is not lost on updates.

## Depends on
- #38 Main window layout and dashboard
- #10 PeerDirectory: change notifications for the UI and last-seen handling
- #15 Manual peer entry (fallback when broadcast is blocked)

## Definition of done
- [ ] PR merged into `main` with `Closes #39`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
