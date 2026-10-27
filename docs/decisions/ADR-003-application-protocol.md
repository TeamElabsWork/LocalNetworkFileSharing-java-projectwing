# ADR-003: Application Protocol - UDP Discovery + TCP Framed Messages

## Status
Accepted (details finalized in the protocol-spec issue)

## Context
TCP only delivers bytes; peers must agree what they mean. Discovery needs a cheap broadcast mechanism.

## Decision
- Discovery: small UDP datagrams (magic, type, deviceId, name, tcpPort).
- Transfer: length-prefixed control frames over TCP (`ProtocolMessage`), followed by a raw byte stream for file data.
- Explicit fields only - no Java serialization.
- Decoder enforces a hard maximum frame size and rejects unknown types.

## Consequences
- The protocol is language-independent, so a future Android client can reuse it.
- Malformed input becomes a `ProtocolException`, never a crash or an unbounded allocation.
