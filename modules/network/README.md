# network (M3)

Moves connections between devices and defines the wire protocol framing.

| Class | Status |
|---|---|
| `LoopbackTransport` (+ `LoopbackNetwork`, `LoopbackConnection`, `BytePipe`) | working - default backend `loopback`, in-memory, no sockets |
| `TcpTransport`, `TcpServer`, `TcpConnection` | STUBS - the real backend `tcp` |
| `ProtocolCodec` | STUB - length-prefixed framing, see `docs/protocol.md` |
| `TransportRegistry` | working - `loopback`, `default`, `tcp` |
| `AbstractTransportTest` | contract test every transport must pass (loopback passes; `TcpTransportTest` extends it) |

Switch backend: `backend.transport=tcp` in `config/pipeline.properties`.
Depends on: `contracts` only (tests must not use other modules either).
