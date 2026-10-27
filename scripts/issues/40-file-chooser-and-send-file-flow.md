title: [ui] File chooser and 'Send file' flow
milestone: 4 - JavaFX UI
labels: module:ui|difficulty:medium|priority:mvp|help wanted
---
**Module:** `ui` (M7) · **Milestone:** 4 - JavaFX UI · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/ui-send`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Let the user pick a peer, choose a file with `FileChooser` and start the transfer via `TransferEngine.send`. Large-file hashing and connecting happen in the background; the UI must stay responsive.

## Tasks
- [ ] 'Send file...' button enabled only when an ONLINE peer is selected.
- [ ] Show immediate feedback ('Preparing...') and add the new session to the transfers view.
- [ ] Show errors from `send()` (unreadable file, peer unreachable) in a dialog.
- [ ] Optional: remember the last used directory.

## Files / classes
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/`

## Acceptance criteria
- [ ] Starting a transfer never blocks the JavaFX thread (verify with a 5 GB file).
- [ ] Errors are shown, not swallowed.

## Depends on
- #39 Peer list bound to PeerDirectory (device controller)
- #28 NetworkTransferEngine: send(), incoming request handling and approval

## Definition of done
- [ ] PR merged into `main` with `Closes #40`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
