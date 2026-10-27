title: [discovery] Discovery packet encoding/decoding (UDP payload)
milestone: 1 - Discovery & TCP
labels: module:discovery|difficulty:medium|priority:mvp|help wanted
---
**Module:** `discovery` (M2) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `feat/discovery-message`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Implement a small `DiscoveryMessage` class with `encode()` and `decode(byte[], int length)` for the UDP payload defined in `docs/protocol.md` section 2 (magic, type, deviceId, deviceName, tcpPort). Decoding must be defensive: any malformed packet yields an error/empty result, never an uncaught exception.

> **Enable it with:** `backend.discovery=udp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] Create `discovery/DiscoveryMessage` (record or class) + `DiscoveryMessage.Type` enum.
- [ ] Encode with `DataOutputStream`, decode with `DataInputStream`; verify magic, field lengths and port range.
- [ ] Reject packets larger than the agreed max size.
- [ ] Unit tests: round trip, wrong magic, truncated packet, oversized name, garbage bytes.

## Files / classes
- `modules/discovery/src/main/java/com/fileshare/discovery/`

## Acceptance criteria
- [ ] Round-trip test passes.
- [ ] Fuzz-style test with random bytes never throws an unexpected exception.

## Depends on
- #3 Finalize the wire protocol specification (docs/protocol.md)

## Definition of done
- [ ] PR merged into `main` with `Closes #9`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
