title: [file] FileHasher: streaming SHA-256
milestone: 2 - Transfer MVP
labels: module:file|difficulty:easy|priority:mvp|good first issue|help wanted
---
**Module:** `file` (M5) · **Milestone:** 2 - Transfer MVP · **Difficulty:** easy · **Priority:** MVP  
**Suggested branch:** `feat/file-hasher`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Compute SHA-256 of a file by streaming (bounded memory) and return lowercase hex. API: `sha256(Path)`; optionally an incremental `Sha256Accumulator` (update(byte[],off,len) / hexDigest()) so the receiver can hash while receiving.

> **Enable it with:** `backend.files=local` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Implement with `MessageDigest` and a reusable buffer (64 KiB).
- [ ] Add the incremental accumulator (used by `DownloadTask` to avoid a second pass).
- [ ] Unit tests with known vectors: empty file, `abc`, 1 MB of zeros, file larger than the buffer.
- [ ] Test that a 1 GB file hash does not grow the heap (manual/benchmark note).

## Files / classes
- `modules/file/src/main/java/com/fileshare/file/FileHasher.java`

## Acceptance criteria
- [ ] Known test vectors match.
- [ ] Works for an empty file.
- [ ] Memory use independent of file size.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #23`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
