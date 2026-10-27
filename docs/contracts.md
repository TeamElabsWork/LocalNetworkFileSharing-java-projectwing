# Contracts Reference (v0.1.0)

Everything in `modules/contracts/src/main/java/com/fileshare/contracts/`. Modules may import these types - and nothing
from each other. Changes follow the **Contracts Stability Rule** in CONTRIBUTING.md.

## Models (`contracts.model`)
| Type | Purpose | Producer -> consumers |
|---|---|---|
| `Peer` (record) | A discovered device: `peerId`, `deviceName`, `address`, `tcpPort`, `lastSeen`, `status`. Immutable; use `withLastSeen` / `withStatus`. | discovery -> UIs, transfer |
| `PeerStatus` | `ONLINE`, `OFFLINE` | discovery |
| `FileMetadata` (record) | `transferId`, `fileName` (name only!), `sizeBytes`, `sha256` (64 lowercase hex). Validated in the constructor. | file -> transfer, network |
| `TransferSession` | State of one transfer: metadata, peer, `TransferDirection`, `TransferProgress`, `TransferStatus`, optional message. | transfer -> UIs |
| `TransferStatus` | `PENDING, CONNECTING, RUNNING, VERIFYING, COMPLETED, FAILED, CANCELLED, REJECTED` (+ `isTerminal()`) | transfer |
| `TransferDirection` | `UPLOAD`, `DOWNLOAD` | transfer |
| `TransferProgress` | Thread-safe counters: bytes, percentage, speed. ONE implementation only. | transfer -> UIs |

## Protocol (`contracts.protocol`)
| Type | Purpose |
|---|---|
| `MessageType` | `DISCOVER, DISCOVER_RESPONSE, TRANSFER_REQUEST, TRANSFER_ACCEPT, TRANSFER_REJECT, TRANSFER_COMPLETE, HASH_RESULT, CANCEL, RESUME_REQUEST` |
| `ProtocolMessage` (record) | `type`, `transferId`, immutable `fields` map. |
| `ProtocolException` | Malformed / oversized / invalid frame. |

## Ports - the interfaces modules implement (`contracts.port`)
| Port | Implemented by (backends) | Used by |
|---|---|---|
| `PeerDirectory` | `discovery`: `InMemoryPeerDirectory` | discovery, UIs, engine |
| `DiscoveryService` | `discovery`: `mock`, `udp` | app |
| `TransferTransport` / `PeerConnection` | `network`: `loopback`, `tcp` | transfer |
| `FileService` | `file`: `stub`, `local` | transfer |
| `TransferEngine` | `transfer`: `simulated`, `network` | UIs |
| `TransferStore` / `TransferListener` | `transfer`: `InMemoryTransferStore` | engine, UIs |
| `TaskRunner` | `concurrency`: `TaskExecutor` | everyone who needs a thread |
| `IncomingRequestHandler` / `ApprovalDecision` | UIs implement the handler | engine |
| `UiServices` (record) | built by the app | UIs |

## Config & registry
| Type | Purpose |
|---|---|
| `AppConfig` (record) | Ports, intervals, buffer size, download dir, limits. `AppConfig.defaults()`. |
| `BackendContext` (record) | What a backend factory receives (config, executor, peers, store, transport, files, options). |
| `ComponentRegistry<T>` | name -> factory registry used by every module's `XxxRegistry`. |

## Util (`contracts.util`)
`FormatUtils` - `formatBytes`, `formatDuration`, `formatSpeed` for every UI.
