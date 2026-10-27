title: [file] FileService: describe files, safe destinations, .part handling, finalize
milestone: 2 - Transfer MVP
labels: module:file|difficulty:medium|priority:mvp|help wanted
---
**Module:** `file` (M5) · **Milestone:** 2 - Transfer MVP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/file-service`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Everything file-system related that the transfer engine needs: build `FileMetadata` for a source file, compute a safe `.part` path inside the destination directory, and atomically move a verified file to its final name without overwriting an existing file (duplicate names get ` (1)`, ` (2)` ...).

> **Enable it with:** `backend.files=local` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] `describe(Path, transferId)`: exists, readable, regular file, size via `Files.size`, SHA-256 via `FileHasher`.
- [ ] `partFileFor(dir, name)`: uses `ValidationUtils`; the part file name must be unique per transfer to avoid collisions.
- [ ] `finalizeDownload(...)`: `Files.move` with `ATOMIC_MOVE` where supported; duplicate-name policy; returns the final path.
- [ ] Create the destination directory if missing; check it is writable.
- [ ] `deletePartFile(Path)` that never throws.
- [ ] Unit tests with `@TempDir`: duplicates, missing dir, name attacks, unreadable source.

## Files / classes
- `modules/file/src/main/java/com/fileshare/file/LocalFileService.java`

## Acceptance criteria
- [ ] A verified file ends up under its sanitized name; existing files are never overwritten.
- [ ] No file outside the destination directory is ever touched.

## Depends on
- #23 FileHasher: streaming SHA-256
- #22 ValidationUtils: sanitize file names and validate untrusted input
- #24 FileChunkReader and FileChunkWriter

## Definition of done
- [ ] PR merged into `main` with `Closes #25`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
