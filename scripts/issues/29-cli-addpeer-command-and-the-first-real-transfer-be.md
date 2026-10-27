title: [ui] CLI: `addpeer` command and the first real transfer between two devices
milestone: 2 - Transfer MVP
labels: module:ui|module:transfer|difficulty:medium|priority:mvp|help wanted
---
**Module:** `ui` (M7), `transfer` (M4) · **Milestone:** 2 - Transfer MVP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/transfer-cli`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
The plan recommends getting a terminal-based transfer working first. The CLI already exists; add an `addpeer <ip> <port> [name]` command (uses `Peer.manual`) and run it with the real backends on two devices - no UI and no discovery needed.

> **Enable it with:** `backend.engine=network (+ transport=tcp, files=local, discovery=udp)` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Add the `addpeer` command to `CliRepl` (+ unit test in `CliReplTest`).
- [ ] Add a `listen`-style hint/command that prints this device's IP and TCP port so the other side can `addpeer` it.
- [ ] Print progress with speed and ETA (`FormatUtils`) while a transfer runs.
- [ ] Switch `config/pipeline.properties` to the real backends (engine/transport/files) on two devices and send a file both ways.
- [ ] Record the result (OS, network, file size, hash check) as a comment on this issue and in `docs/network-notes.md`.

## Files / classes
- `modules/ui-cli/src/main/java/com/fileshare/ui/cli/CliRepl.java`
- `modules/ui-cli/src/test/java/com/fileshare/ui/cli/CliReplTest.java`
- `docs/network-notes.md`

## Acceptance criteria
- [ ] A file sent from device A is saved on device B with matching SHA-256.
- [ ] Works without UDP discovery and without the JavaFX UI.
- [ ] Mock configuration still works (`make demo` with defaults).

## Depends on
- #28 NetworkTransferEngine: send(), incoming request handling and approval
- #15 Manual peer entry (fallback when broadcast is blocked)

## Notes
This is the 'Transfer MVP' milestone goal.

## Definition of done
- [ ] PR merged into `main` with `Closes #29`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
