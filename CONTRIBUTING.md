# Contributing Guidelines

Welcome! This repository is a modular monorepo so the team can build discovery, networking, transfer, files and UI
**in parallel without merge conflicts**. Every module depends only on `contracts`, has a working mock backend, and is
replaced by the real implementation one issue at a time.

---

## 1. Issue Management & Difficulty Tiers

### Difficulty labels
| Label | Description | Examples |
| :--- | :--- | :--- |
| `difficulty:easy` | Onboarding, quick wins, docs, tests, small utilities. | `FileHasher`, `FormatUtils` tests, demo checklist, logging helper. |
| `difficulty:medium` | Implementing one class/subsystem behind an existing interface. | `DiscoveryListener`, `TcpServer`, `FileService`, peer list UI. |
| `difficulty:hard` | Concurrency, protocol/security-critical or cross-module work. | `ProtocolCodec`, `UploadTask`/`DownloadTask`, cancellation, integration tests. |

### Module labels
* `module:contracts` - shared models, protocol types, port interfaces
* `module:discovery` - UDP discovery, peer directory
* `module:network` - transport (TCP) and wire protocol
* `module:file` - file I/O, validation, SHA-256
* `module:transfer` - transfer engine and transfer store
* `module:concurrency` - thread pools, progress, cancellation, reliability
* `module:ui` - CLI and JavaFX user interfaces
* `module:app` - wiring, config, packaging
* `module:docs` - documentation, demo, testing campaigns

Other labels: `priority:mvp` (needed for the MVP), `priority:stretch` (only after the MVP works),
`good first issue`, `help wanted`.

> [!IMPORTANT]
> **Claiming an issue**: comment **"I'll take this"** on the issue. The maintainer assigns it to you. One person per
> issue; check its *Depends on* list first. Stuck for more than a day? Say so on the issue.

---

## 2. Development Setup

### Prerequisites
* JDK 21
* Maven 3.9+
* Git

### Steps
```bash
git clone <repo-url>
cd <repo-folder>
make setup        # or: mvn -B -q install -DskipTests
make demo         # or: mvn -q -pl apps/fileshare-app exec:java
```
Re-run `make setup` after pulling changes in modules you depend on (`contracts` especially).

---

## 3. Branching & Workflow

1. Create a branch from `main`:
   * Feature: `feat/<module>-<short-description>` (e.g. `feat/discovery-listener`)
   * Bug fix: `fix/<issue-id>-<short-description>`
   * Docs/tests: `docs/<description>`, `test/<description>`
2. Work **inside your module** (e.g. `modules/discovery/`).
3. Follow the **Extensibility Rules**:
   * **Only depend on `contracts`.** Never import another module's classes. Talk to other modules through the
     interfaces in `contracts/port`. (Maven enforces this: a module's `pom.xml` lists only `contracts`.)
   * **Register, don't hard-wire.** A real implementation is a new backend in your module's registry
     (`DiscoveryRegistry`, `TransportRegistry`, `FileServiceRegistry`, `TransferEngineRegistry`) and is selected in
     `config/pipeline.properties`.
   * **Threads only via `TaskRunner`.** Never `new Thread(...)` or create your own executor.
   * **Never trust the network.** Validate every incoming message; never use a received file name as a path
     (use `ValidationUtils`).
   * **Close resources** (sockets, streams, files) on success *and* failure paths (try-with-resources).
   * **No Java serialization** over the network.
   * Tests must run offline and without a second device - use the mock/loopback backends.

---

## 4. Quality Checks Before Opening a PR

```bash
make check        # or: mvn -B verify
```
It must pass for the whole repository. Add or update unit tests for the logic you write.

---

## 5. Submitting a Pull Request

### PR title convention (Conventional Commits)
* `feat(discovery): implement UDP listener`
* `fix(network): close socket on malformed frame`
* `docs(protocol): finalize TRANSFER_REQUEST fields`
* `test(file): add path traversal cases`

### PR description checklist
- [ ] Link to the issue it resolves (`Closes #<issue-number>`).
- [ ] Summary of changes and files modified.
- [ ] Mention any change to `config/pipeline.properties`.
- [ ] Confirmation that `mvn -B verify` passes locally.

> [!WARNING]
> ### The Contracts Stability Rule
> If your PR modifies `modules/contracts/`:
> 1. Explain why the change is necessary.
> 2. It needs sign-off from **all affected module owners** listed in `CODEOWNERS`.
> 3. Breaking changes (removing/renaming a field or method) are not allowed without agreeing a migration first.
> Prefer *adding* methods/fields over changing existing ones.

---

## 6. Code Reviews & Ownership (`CODEOWNERS`)

1. **One-time setup**: open [`CODEOWNERS`](CODEOWNERS) and replace the placeholder handle of your module
   (e.g. `@owner-discovery`) with your real GitHub username.
2. **Automatic review routing**: GitHub requests your review whenever a PR touches your module, and requests all
   dependent owners when `modules/contracts/` changes.
3. **Merging**: a PR can be merged when CI is green and the module owner(s) approve.
