# transfer (M4)

The transfer layer: public engine API, transfer registry (store), upload/download workers.

| Class | Status |
|---|---|
| `InMemoryTransferStore` | working baseline (transitions/throttling/pruning = issue) |
| `SimulatedTransferEngine` | working - default backend `simulated`: realistic status + progress events, no bytes moved |
| `NetworkTransferEngine` | STUB - the real backend `network` |
| `UploadTask`, `DownloadTask` | STUBS used by `NetworkTransferEngine` |
| `TransferEngineRegistry` | working - `simulated`, `default`, `network` |

Switch backend: `backend.engine=network` in `config/pipeline.properties`.
`simulated.step_ms` sets the speed of the simulation (0 = instant, used by tests).
Depends on: `contracts` only (it gets transport and file service through the ports in `BackendContext`).
