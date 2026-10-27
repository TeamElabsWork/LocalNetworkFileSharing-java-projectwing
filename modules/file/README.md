# file (M5)

File access, destination safety and SHA-256 integrity.

| Class | Status |
|---|---|
| `StubFileService` | working - default backend `stub` (name + size, placeholder hash, writes nothing) |
| `LocalFileService` | STUB - the real backend `local` |
| `FileHasher`, `FileChunkReader`, `FileChunkWriter`, `ValidationUtils` | STUBS used by `LocalFileService` |
| `FileServiceRegistry` | working - `stub`, `default`, `local` |

Switch backend: `backend.files=local` in `config/pipeline.properties`.
Depends on: `contracts` only. Security rule: never use a received file name as a path - always `ValidationUtils`.
