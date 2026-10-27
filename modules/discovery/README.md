# discovery (M2)

Finds other devices on the network and keeps the `PeerDirectory` up to date.

| Class | Status |
|---|---|
| `InMemoryPeerDirectory` | working baseline (listeners = issue) |
| `MockDiscoveryService` | working - default backend `mock`, publishes 3 fake peers |
| `UdpDiscoveryService` | STUB - the real backend `udp` |
| `DiscoveryBroadcaster`, `DiscoveryListener`, `NetworkUtils` | STUBS used by `UdpDiscoveryService` |
| `DiscoveryRegistry` | working - `mock`, `default`, `udp` |

Switch backend: `backend.discovery=udp` in `config/pipeline.properties`.
Depends on: `contracts` only. Protocol: `docs/protocol.md` section 2.
