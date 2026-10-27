title: [file] ValidationUtils: sanitize file names and validate untrusted input
milestone: 2 - Transfer MVP
labels: module:file|difficulty:medium|priority:mvp|help wanted
---
**Module:** `file` (M5) · **Milestone:** 2 - Transfer MVP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/file-validation`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
SECURITY-CRITICAL. A file name received from the network must never become a path directly. Implement `sanitizeFileName` and further validation helpers; they are used by the receiver before accepting any transfer.

> **Enable it with:** `backend.files=local` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Strip directory components (`/`, `\`), reject `..`, drive letters (`C:`), control characters, NUL, trailing dots/spaces (Windows) and reserved names (CON, NUL, COM1...).
- [ ] Limit length (e.g. 255 bytes in UTF-8); never return an empty name (throw `IllegalArgumentException`).
- [ ] Add `validateSize(long size, long maxBytes)` and `requireInside(Path dir, Path candidate)` (normalised path must stay inside the directory).
- [ ] Extensive unit tests incl. attack strings: `../../etc/passwd`, `..\\..\\x`, `C:\\Windows\\a`, `a\u0000b`, very long names, Unicode tricks.

## Files / classes
- `modules/file/src/main/java/com/fileshare/file/ValidationUtils.java`
- `modules/file/src/test/java/com/fileshare/file/ValidationUtilsTest.java`

## Acceptance criteria
- [ ] All attack-string tests pass.
- [ ] `requireInside` rejects symlink/`..` escapes.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Definition of done
- [ ] PR merged into `main` with `Closes #22`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
