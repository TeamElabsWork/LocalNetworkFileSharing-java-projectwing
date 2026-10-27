title: [ui] Incoming request dialog (accept / reject, choose destination)
milestone: 4 - JavaFX UI
labels: module:ui|difficulty:medium|priority:mvp|help wanted
---
**Module:** `ui` (M7) · **Milestone:** 4 - JavaFX UI · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/ui-incoming-request`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Implement the `IncomingRequestHandler` for the UI: show sender, file name, size (human readable) and let the user accept (choose or confirm destination folder) or reject. The handler is called on a worker thread and must block until the user answers.

## Tasks
- [ ] Use `Platform.runLater` + `CompletableFuture<ApprovalDecision>`; the worker waits with a timeout.
- [ ] Show file name, size, sender IP/name; default destination from settings.
- [ ] Queue multiple simultaneous requests (don't stack 5 dialogs on top of each other).
- [ ] If the window is closed/timeouts -> reject with a reason.

## Files / classes
- `modules/ui-javafx/src/main/java/com/fileshare/ui/fx/`

## Acceptance criteria
- [ ] Accept starts the download; Reject notifies the sender.
- [ ] Two simultaneous requests are handled one after another.

## Depends on
- #38 Main window layout and dashboard
- #28 NetworkTransferEngine: send(), incoming request handling and approval

## Definition of done
- [ ] PR merged into `main` with `Closes #41`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
