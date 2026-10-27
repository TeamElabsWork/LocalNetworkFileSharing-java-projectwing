title: [file] FileChunkReader and FileChunkWriter
milestone: 2 - Transfer MVP
labels: module:file|difficulty:medium|priority:mvp|help wanted
---
**Module:** `file` (M5) · **Milestone:** 2 - Transfer MVP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/file-chunk-io`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Chunked file reading (sender) and writing (receiver) with bounded memory, based on `Files`/`FileChannel` or buffered streams.

> **Enable it with:** `backend.files=local` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] `FileChunkReader.read(byte[])` returns bytes read or -1 at EOF; supports `skip/position` for later resume.
- [ ] `FileChunkWriter` opens a `.part` file, `write(buf, off, len)`, tracks bytes written, `close()` flushes and is idempotent.
- [ ] Decide the open options (`CREATE_NEW` vs truncate) and document; never silently overwrite.
- [ ] Tests: exact copy of random files of sizes 0, 1, 64K-1, 64K, 64K+1, 10 MB.

## Files / classes
- `modules/file/src/main/java/com/fileshare/file/FileChunkReader.java`
- `modules/file/src/main/java/com/fileshare/file/FileChunkWriter.java`

## Acceptance criteria
- [ ] Copy test produces byte-identical files.
- [ ] Resources are closed on exceptions.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #24`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
