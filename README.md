# Local Network File Sharing

A Java 21 desktop app that lets devices on the same Wi-Fi network or hotspot **discover each other and transfer files
directly** - no cloud, no internet. UDP for discovery, TCP for transfer, SHA-256 for integrity, JavaFX for the UI.

Built as a modular Maven monorepo for a team working in parallel without stepping on each other: every module depends
only on `contracts`, and **every component already has a working mock backend**, so the whole app runs on day one.
You replace one mock at a time with the real implementation.

---

## Quickstart & Demos

### 1. Prerequisites
- JDK 21
- Maven 3.9+ (`mvn -v`)

### 2. Setup
```bash
make setup
# or without make: mvn -B -q install -DskipTests
```

### 3. Run the demos

#### Demo 1: Interactive CLI (all mock backends)
```bash
make demo
# or: mvn -q -pl apps/fileshare-app exec:java
```
```
> peers
  1. Laptop-B       192.168.1.20    port 37021  ONLINE
  2. Old-Desktop    192.168.1.22    port 37021  OFFLINE
  3. Phone-C        192.168.1.21    port 37021  ONLINE
> send Laptop-B C:\path\to\any-file.pdf
[TRANSFER dc112d3b] UPLOAD 'any-file.pdf' - CONNECTING 0%
[TRANSFER dc112d3b] UPLOAD 'any-file.pdf' - RUNNING 50%
[TRANSFER dc112d3b] UPLOAD 'any-file.pdf' - VERIFYING 100%
[TRANSFER dc112d3b] UPLOAD 'any-file.pdf' - COMPLETED 100%
```
Commands: `help`, `peers`, `send <peer#|name> <file>`, `transfers`, `cancel <n>`, `incoming`, `quit`.
`incoming` simulates a peer sending *you* a file, so you can try the accept/reject flow with one machine.

> The baseline moves **no real bytes** - peers are fake and the transfer is simulated (status and progress events are
> realistic). That is the point: UI, networking and file work can all start immediately against stable contracts.

#### Demo 2: JavaFX window
```bash
make gui
# or: mvn -q -pl apps/fileshare-app exec:java -Dexec.args="--gui"
```

### 4. Run tests
```bash
make check        # or: mvn -B verify
```

---

## Repository Map & Module Breakdown

```text
/modules
  /contracts       M1  shared records, enums, protocol types and PORT INTERFACES - the ONLY thing modules may import
  /discovery       M2  PeerDirectory + discovery backends:   mock (working)      | udp (STUB)
  /network         M3  transport + wire protocol backends:    loopback (working)  | tcp (STUB), ProtocolCodec (STUB)
  /transfer        M4  transfer engine backends:              simulated (working) | network (STUB), TransferStore
  /file            M5  file service backends:                 stub (working)      | local (STUB), hashing, validation
  /concurrency     M6  TaskExecutor - the ONLY place that creates threads
  /ui-cli          M7  working interactive CLI
  /ui-javafx       M7  JavaFX skeleton (UI issues build on it)
/apps
  /fileshare-app   M1/M8  PipelineFactory wires backends from config/pipeline.properties; Main entry point
/config            pipeline.properties - pick the backend of every component by name
/docs              architecture.md, contracts.md, protocol.md, adding-a-module.md, demo-checklist.md, ISSUES.md, decisions/ (ADRs)
/scripts           create-issues.sh + issues/  (maintainer: creates labels, milestones and all GitHub issues)
CODEOWNERS, Makefile, pom.xml (parent), CONTRIBUTING.md
```

---

## Core Architecture Principles

1. **Contracts first.** All shared types and interfaces live in `modules/contracts`. A module's `pom.xml` depends on
   `contracts` and nothing else; modules never import each other's classes (ADR-001).
2. **Mock first, real later.** Each component has a safe working default (`mock`, `loopback`, `stub`, `simulated`).
   The real backend (`udp`, `tcp`, `local`, `network`) starts as a stub that throws `TODO` (ADR-006).
3. **Pluggable registries.** Backends register in their module's registry and are selected by name in
   `config/pipeline.properties` (ADR-002).
4. **Threads in one place.** All background work goes through `TaskRunner` (`modules/concurrency`) (ADR-005).
5. **Never trust the network.** Validate every message, sanitize file names, write to `.part` files and rename only
   after SHA-256 verification (ADR-003, docs/protocol.md).
6. **Offline by default.** Unit tests need no network and no second device: they use the loopback transport.

---

## Switching a mock to the real implementation

When your issue is done, change one line in `config/pipeline.properties`:

```properties
backend.discovery=udp
```
and run `make demo` again. See [docs/adding-a-module.md](docs/adding-a-module.md) for a step-by-step example.

---

## Development & Contributing Documentation

- [Contributing Guidelines](CONTRIBUTING.md) - difficulty tiers, labels, branch standards, PR checklist
- [Issue overview](docs/ISSUES.md) - all issues by milestone and who should start where
- [Architecture Overview](docs/architecture.md) - request flow, module map, thread model
- [Contracts Reference](docs/contracts.md) - every shared type and port interface
- [Wire protocol (draft)](docs/protocol.md)
- [How to Add or Implement a Module](docs/adding-a-module.md)
- [Demo checklist](docs/demo-checklist.md)
- [Architecture Decision Records](docs/decisions/):
  - [ADR-001: Contracts module as the only shared dependency](docs/decisions/ADR-001-contracts-module.md)
  - [ADR-002: Registries and config-driven backends](docs/decisions/ADR-002-registries-and-pipeline-config.md)
  - [ADR-003: Application protocol: UDP discovery + TCP framed messages](docs/decisions/ADR-003-application-protocol.md)
  - [ADR-004: Streaming transfer with .part files and SHA-256](docs/decisions/ADR-004-streaming-and-integrity.md)
  - [ADR-005: Threading model and TaskRunner](docs/decisions/ADR-005-threading-model.md)
  - [ADR-006: Mock-first backends and contract tests](docs/decisions/ADR-006-mock-first-backends.md)

## Known limitations (v1)
- Some Wi-Fi networks isolate clients or block UDP broadcast; use manual peer entry there.
- OS firewalls may block UDP 37020 / TCP 37021 - allow the app on trusted networks only.
- SHA-256 checks integrity, **not** authenticity or confidentiality (no encryption or peer authentication).
- Out of scope for v1: resume (stretch), Android client, cloud relay, accounts/database.
