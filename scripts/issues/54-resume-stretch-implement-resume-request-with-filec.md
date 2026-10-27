title: [transfer] Resume (stretch): implement RESUME_REQUEST with FileChannel seek
milestone: 6 - Stretch goals
labels: module:transfer|difficulty:hard|priority:stretch|help wanted
---
**Module:** `transfer` (M4) · **Milestone:** 6 - Stretch goals · **Difficulty:** hard · **Priority:** stretch  
**Suggested branch:** `feat/resume-impl`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Implement the approved resume design: receiver reports the offset of its `.part` file; sender positions the `FileChannel` and continues; final SHA-256 verification as usual.

## Tasks
- [ ] Receiver: keep `.part` on failure/disconnect (per the policy) and offer resume on retry.
- [ ] Sender: `FileChunkReader.position(offset)` and send from there; progress starts at the offset.
- [ ] Handle a changed source file (hash mismatch) by restarting from zero.
- [ ] Tests: kill the connection at 30/60/90 %, resume, verify the hash.

## Files / classes
- `modules/transfer/src/main/java/com/fileshare/transfer/`
- `modules/file/src/main/java/com/fileshare/file/`

## Acceptance criteria
- [ ] An interrupted transfer completes from the partial file with a matching SHA-256.

## Depends on
- #53 Resume (stretch): protocol design and .part retention policy

## Definition of done
- [ ] PR merged into `main` with `Closes #54`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
