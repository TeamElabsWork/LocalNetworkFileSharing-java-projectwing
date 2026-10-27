# ADR-004: Streaming Transfer with .part Files and SHA-256

## Status
Accepted

## Decision
- Files are streamed in fixed-size chunks (default 64 KiB); a file is never loaded fully into memory.
- The receiver writes `<name>.part`, verifies SHA-256 after the last byte, and only then renames to the final name.
- Existing files are never overwritten; a failed/cancelled transfer deletes its `.part` file.
- SHA-256 proves integrity only - not who sent the file and not confidentiality.

## Consequences
- Memory use is independent of file size.
- A corrupted or incomplete file can never appear under its final name.
- Resume (stretch) can later reuse the `.part` file.
