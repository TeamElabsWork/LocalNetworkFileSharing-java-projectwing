# ADR-006: Mock-First Backends and Contract Tests

## Status
Accepted

## Context
Real UDP/TCP code needs two devices and is slow to debug; UI developers should not wait for it.

## Decision
Every component ships with a working default backend (`mock`, `loopback`, `stub`, `simulated`) and a stub for the real
one (`udp`, `tcp`, `local`, `network`). Where a port has several backends, a reusable abstract test defines its
behaviour (`AbstractTransportTest`); the real backend must pass the same test.

## Consequences
- `make demo` works from the first commit.
- Issues are phrased as "replace the stub", with clear acceptance criteria.
- The mocks are not removed when the real backends are done - they keep tests offline and deterministic.
