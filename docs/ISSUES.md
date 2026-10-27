# Issue overview

All issues are created by `scripts/create-issues.sh` and numbered in the order below (on a fresh repository
issue **#N** is row N). They are created **unassigned** - teammates comment *"I'll take this"* and the maintainer assigns them.

Total: **57 issues** in **7 milestones**. Labels: `module:*`, `difficulty:easy|medium|hard`, `priority:mvp|stretch`.

## Milestone 0 - Foundation
_Repo, workflow, shared contracts and the protocol spec agreed._

| # | Title | Module | Difficulty | Priority | Depends on |
|---|---|---|---|---|---|
| 1 | Set up repository: branch protection, labels, milestones and project board | app | easy | MVP | - |
| 2 | Review the baseline and agree on shared contracts (class signatures, ports, ownership) | contracts, docs | medium | MVP | #1 |
| 3 | Finalize the wire protocol specification (docs/protocol.md) | network, docs | medium | MVP | #2 |
| 4 | AppConfig: validation and optional settings file override | contracts | easy | important | #2 |
| 5 | Add logging utility and use it consistently | contracts | easy | important | #2 |
| 6 | Complete unit tests for shared models (Peer, TransferSession, TransferStatus) | contracts | easy | important | #2 |

## Milestone 1 - Discovery & TCP
_UDP discovery and TCP/protocol layers working independently (in parallel)._

| # | Title | Module | Difficulty | Priority | Depends on |
|---|---|---|---|---|---|
| 7 | TaskExecutor: harden thread pools and shutdown | concurrency | medium | MVP | #2 |
| 8 | NetworkUtils: find usable network interfaces, local IPs and broadcast addresses | discovery | medium | MVP | #2 |
| 9 | Discovery packet encoding/decoding (UDP payload) | discovery | medium | MVP | #3 |
| 10 | PeerDirectory: change notifications for the UI and last-seen handling | discovery, contracts | medium | MVP | #2 |
| 11 | DiscoveryBroadcaster: periodic UDP announcements | discovery | medium | MVP | #8, #9 |
| 12 | DiscoveryListener: receive packets, update registry, reply to DISCOVER | discovery | medium | MVP | #9, #10 |
| 13 | DiscoveryService lifecycle + stale peer sweeper | discovery | medium | MVP | #11, #12, #7 |
| 14 | Verify UDP discovery between two real devices (CLI) | discovery, ui | easy | MVP | #13 |
| 15 | Manual peer entry (fallback when broadcast is blocked) | contracts, discovery | easy | important | #10 |
| 16 | ProtocolMessage: typed factories and per-type field validation | contracts, network | medium | MVP | #3 |
| 17 | ProtocolCodec: length-prefixed encode/decode of control messages | network | hard | MVP | #3, #16 |
| 18 | TcpConnection: connect, send/receive messages, raw streams, close | network | medium | MVP | #17 |
| 19 | TcpServer: accept loop that hands connections to workers | network | medium | MVP | #18, #7 |
| 20 | Console demo: two devices exchange request/accept/reject messages | network | easy | MVP | #19, #16 |
| 21 | TCP robustness: disconnects, timeouts and malformed input | network | hard | MVP | #19, #17 |

## Milestone 2 - Transfer MVP
_File services and the transfer engine: a file moves between two devices from the terminal._

| # | Title | Module | Difficulty | Priority | Depends on |
|---|---|---|---|---|---|
| 22 | ValidationUtils: sanitize file names and validate untrusted input | file | medium | MVP | #2 |
| 23 | FileHasher: streaming SHA-256 | file | easy | MVP | #2 |
| 24 | FileChunkReader and FileChunkWriter | file | medium | MVP | #2 |
| 25 | FileService: describe files, safe destinations, .part handling, finalize | file | medium | MVP | #23, #22, #24 |
| 26 | UploadTask: send a file in chunks | transfer | hard | MVP | #16, #18, #24, #30 |
| 27 | DownloadTask: receive, write .part, verify, finalize | transfer | hard | MVP | #16, #18, #25, #23, #30 |
| 28 | NetworkTransferEngine: send(), incoming request handling and approval | transfer | hard | MVP | #26, #27, #19, #22 |
| 29 | CLI: `addpeer` command and the first real transfer between two devices | ui, transfer | medium | MVP | #28, #15 |
| 30 | InMemoryTransferStore: validated transitions, throttled events and cleanup | transfer, concurrency | medium | MVP | #2 |

## Milestone 3 - Concurrency & Reliability
_Thread pools, progress, cancellation, timeouts and SHA-256 verification wired in._

| # | Title | Module | Difficulty | Priority | Depends on |
|---|---|---|---|---|---|
| 31 | TransferProgress: moving-average speed and ETA | contracts, concurrency | medium | MVP | #2 |
| 32 | Cancellation: stop a transfer safely on both sides | transfer, concurrency | hard | MVP | #26, #27, #30 |
| 33 | Timeouts and failure handling for network and disk errors | transfer, concurrency | hard | MVP | #26, #27 |
| 34 | SHA-256 verification: integrate hash exchange and failure policy | transfer, file | medium | MVP | #27, #26, #23 |
| 35 | Concurrent transfers: multiple simultaneous sessions | transfer, concurrency | hard | MVP | #28, #31, #32 |
| 36 | Receiver limits: max file size and free disk space check | transfer | easy | important | #28 |

## Milestone 4 - JavaFX UI
_JavaFX interface connected to the tested services._

| # | Title | Module | Difficulty | Priority | Depends on |
|---|---|---|---|---|---|
| 37 | JavaFX: start and stop the services cleanly (`Main --gui`) | ui, app | medium | MVP | #13, #28 |
| 38 | Main window layout and dashboard | ui | medium | MVP | #37 |
| 39 | Peer list bound to PeerDirectory (device controller) | ui | medium | MVP | #38, #10, #15 |
| 40 | File chooser and 'Send file' flow | ui | medium | MVP | #39, #28 |
| 41 | Incoming request dialog (accept / reject, choose destination) | ui | medium | MVP | #38, #28 |
| 42 | Active transfers view: progress, speed, ETA, status, cancel | ui | medium | MVP | #38, #30, #31 |
| 43 | Settings screen (device name, ports, download directory) | ui | easy | important | #38, #4 |
| 44 | Error feedback and UI threading audit | ui | medium | important | #42, #40, #41 |

## Milestone 5 - Integration & Demo
_End-to-end testing between real devices, documentation, packaging and demo._

| # | Title | Module | Difficulty | Priority | Depends on |
|---|---|---|---|---|---|
| 45 | Integration tests: discovery-to-transfer workflow (automated, loopback) | app | hard | MVP | #28, #13, #32, #34 |
| 46 | Edge-case transfer tests (empty, large, duplicate names, disconnects) | app | medium | MVP | #45 |
| 47 | Security review: path traversal, validation and untrusted peers | app | hard | MVP | #28, #22, #21 |
| 48 | Real-device testing on Wi-Fi/hotspot; firewall and isolation notes | docs | medium | MVP | #29, #44 |
| 49 | Throughput benchmark: chunk sizes and concurrent transfers | app | medium | important | #28, #35 |
| 50 | Final documentation: README, architecture diagram, user guide | docs | easy | MVP | #44, #3 |
| 51 | Demo checklist and rehearsal for the expo | docs | easy | MVP | #48, #44 |
| 52 | Packaging: runnable distribution (fat jar or jpackage) | app | hard | important | #44 |

## Milestone 6 - Stretch goals
_Only after the MVP is stable: resume, history and other extras._

| # | Title | Module | Difficulty | Priority | Depends on |
|---|---|---|---|---|---|
| 53 | Resume (stretch): protocol design and .part retention policy | network, docs | hard | stretch | #45 |
| 54 | Resume (stretch): implement RESUME_REQUEST with FileChannel seek | transfer | hard | stretch | #53 |
| 55 | Transfer history (optional): local persistence of completed/failed transfers | transfer, ui | medium | stretch | #42 |
| 56 | Multi-file queue and folder transfer (stretch) | transfer, ui | hard | stretch | #45 |
| 57 | Research: PIN/QR pairing and optional encryption (future scope) | network, docs | hard | stretch | #47 |

## Suggested starting points per role

| Role (implementation plan) | Start with |
|---|---|
| Project lead / integration | #1, #2, #3, #4; later #45, #50 |
| Discovery (M2) | #8, #9, #10, #11, #12, #13 |
| TCP & protocol (M3) | #16, #17, #18, #19, #20, #21 |
| Transfer engine + file I/O (M4-M5) | #23, #24, #22, #25, #26, #27, #28 |
| Concurrency & reliability (M6) | #7, #30, #31, #32, #33, #35 |
| JavaFX UI (M7) | #37, #38, #39, #40, #41, #42, #43, #44 |
| Newcomers (`difficulty:easy`) | #1, #4, #5, #6, #14, #15, #20, #23, #36, #43, #50, #51 |

> The first real end-to-end milestone is the **terminal transfer** (issue #29), as recommended in the plan; the UI is built on top of tested services.
