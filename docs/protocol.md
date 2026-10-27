# Wire protocol (DRAFT v0)

Status: **draft**. Finalised in the "Finalize protocol specification" issue. Once agreed, changes need a PR
reviewed by the project lead. Everything is big-endian (`DataOutputStream` default).

## 1. Ports
| Purpose | Transport | Default |
|---|---|---|
| Discovery | UDP | 37020 |
| Transfer | TCP | 37021 |

Both configurable through `AppConfig`.

## 2. UDP discovery packet
Datagram payload (UTF-8, `DataOutputStream.writeUTF` fields in this order):

| Field | Type | Notes |
|---|---|---|
| magic | UTF string | `"FSHARE1"` - ignore packets with a different value |
| type | byte | 0 = DISCOVER, 1 = DISCOVER_RESPONSE |
| deviceId | UTF string | unique per running instance; ignore packets with our own ID |
| deviceName | UTF string | max 64 chars |
| tcpPort | int | 1-65535 |

Rules: broadcast every `discoveryIntervalMs`; a DISCOVER is answered with a unicast DISCOVER_RESPONSE; invalid
packets are dropped silently (never crash the listener). Max packet size 512 bytes.

## 3. TCP control frame
```
+-----------+-----------+-----------------------------+
| int length| byte type | payload (length-1 bytes)    |
+-----------+-----------+-----------------------------+
```
- `length` counts the type byte + payload; must be `1 .. ProtocolCodec.MAX_FRAME_BYTES` (64 KiB), otherwise the
  connection is closed with a `ProtocolException`.
- `type` = ordinal of `MessageType` (unknown value -> `ProtocolException`).
- payload = `transferId` (UTF) + field count (short) + repeated `key` (UTF), `value` (UTF).
- No Java serialization.

## 4. Messages
| Type | Direction | Fields |
|---|---|---|
| TRANSFER_REQUEST | sender -> receiver | `fileName`, `size`, `sha256` |
| TRANSFER_ACCEPT | receiver -> sender | - |
| TRANSFER_REJECT | receiver -> sender | `reason` |
| (raw data) | sender -> receiver | exactly `size` bytes, **not framed** |
| TRANSFER_COMPLETE | sender -> receiver | - (sent after the last byte) |
| HASH_RESULT | receiver -> sender | `ok` = `true`/`false`, `calculated` (hex) |
| CANCEL | either | `reason` (optional) |
| RESUME_REQUEST | receiver -> sender | `offset` - stretch goal, not in MVP |

## 5. Sequence (happy path)
```
Sender                                Receiver
  | --- TRANSFER_REQUEST ------------> |   user is asked
  | <-- TRANSFER_ACCEPT -------------- |
  | === raw bytes (size) ============> |   written to <name>.part
  | --- TRANSFER_COMPLETE -----------> |   SHA-256 verified, .part renamed
  | <-- HASH_RESULT ok=true ---------- |
```
Rejection: `TRANSFER_REQUEST` -> `TRANSFER_REJECT` -> both close.
Cancellation: either side sends `CANCEL`, closes the socket; the receiver deletes the `.part` file.

## 6. Receiver validation (must all pass before sending TRANSFER_ACCEPT)
- fileName sanitized by `ValidationUtils.sanitizeFileName` (no path separators, no `..`).
- size >= 0 and <= `maxFileSizeBytes` (if set) and fits free disk space.
- sha256 is 64 lowercase hex chars.
- Destination stays inside the chosen download directory; existing files are never overwritten.

## 7. Timeouts
Connect 5 s; control-frame read 30 s; data read inactivity 30 s (configurable). A timeout fails the transfer.
