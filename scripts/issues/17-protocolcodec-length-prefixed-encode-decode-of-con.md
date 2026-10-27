title: [network] ProtocolCodec: length-prefixed encode/decode of control messages
milestone: 1 - Discovery & TCP
labels: module:network|difficulty:hard|priority:mvp|help wanted
---
**Module:** `network` (M3) · **Milestone:** 1 - Discovery & TCP · **Difficulty:** hard · **Priority:** MVP  
**Suggested branch:** `feat/tcp-codec`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
Implement `ProtocolCodec.write/read` using `DataOutputStream`/`DataInputStream` exactly as specified (length, type, transferId, key/value fields). This is security-relevant: a malicious peer must not be able to make us allocate huge buffers or hang.

> **Enable it with:** `backend.transport=tcp` in `config/pipeline.properties`. The mock backend must keep working (default config).

## Tasks
- [ ] `write`: frame = int length + type + payload; flush after each message.
- [ ] `read`: validate length (1..`MAX_FRAME_BYTES`) BEFORE allocating; validate type ordinal; cap field count and string lengths.
- [ ] Throw `ProtocolException` for any malformed frame; `EOFException`/`IOException` for broken connections.
- [ ] Keep the codec free of sockets: it works on streams so it can be tested with `ByteArrayInput/OutputStream`.
- [ ] Unit tests: round trip for every message type, truncated frame, oversized length, unknown type, negative length, 1 MB garbage.

## Files / classes
- `modules/network/src/main/java/com/fileshare/network/ProtocolCodec.java`
- `modules/network/src/test/java/com/fileshare/network/`

## Acceptance criteria
- [ ] All frames from the spec round-trip.
- [ ] No test can make the codec allocate more than `MAX_FRAME_BYTES`.
- [ ] Test coverage of error paths.

## Depends on
- #3 Finalize the wire protocol specification (docs/protocol.md)
- #16 ProtocolMessage: typed factories and per-type field validation

## Definition of done
- [ ] PR merged into `main` with `Closes #17`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
