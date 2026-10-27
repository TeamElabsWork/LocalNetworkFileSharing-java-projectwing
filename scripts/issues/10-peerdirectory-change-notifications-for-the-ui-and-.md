title: [discovery] PeerDirectory: change notifications for the UI and last-seen handling
milestone: 1 - Discovery & TCP
labels: module:discovery|module:contracts|difficulty:medium|priority:mvp|help wanted
---
**Module:** `discovery` (M2), `contracts` (M1) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/discovery-registry`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
`InMemoryPeerDirectory` stores peers but nobody is told when something changes. Add change notifications so other components (especially the JavaFX UI) can react without polling. NOTE: adding `addListener` to the `PeerDirectory` port is a **contracts change** - get sign-off from the discovery, UI and transfer owners first (add a new method, don't change existing ones).

## Tasks
- [ ] Add `addListener(PeerDirectoryListener)` / `removeListener` with events PEER_ADDED, PEER_UPDATED, PEER_OFFLINE, PEER_REMOVED.
- [ ] Listeners must be invoked outside internal locks and must not break the registry if they throw.
- [ ] `upsert` must not fire an event when nothing but `lastSeen` changed (avoid UI flicker) - but must still refresh `lastSeen`.
- [ ] Optional: expose an unmodifiable snapshot suitable for building a JavaFX `ObservableList` (the UI issue does the binding).
- [ ] Unit tests incl. multi-threaded upsert from several threads.

## Files / classes
- `modules/contracts/src/main/java/com/fileshare/contracts/port/PeerDirectory.java`
- `modules/discovery/src/main/java/com/fileshare/discovery/InMemoryPeerDirectory.java`
- `modules/discovery/src/test/java/com/fileshare/discovery/InMemoryPeerDirectoryTest.java`

## Acceptance criteria
- [ ] Events fire exactly once per real change.
- [ ] Concurrency test passes repeatedly (run it 100x).
- [ ] Existing `InMemoryPeerDirectoryTest` still passes.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #10`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
