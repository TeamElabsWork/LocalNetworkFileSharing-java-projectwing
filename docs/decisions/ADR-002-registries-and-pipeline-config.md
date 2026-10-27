# ADR-002: Registries and Config-Driven Backends

## Status
Accepted

## Context
We want to swap the mock discovery for the real UDP one (and likewise transport, files, engine) without editing other
people's code.

## Decision
Each pluggable component has a registry (`DiscoveryRegistry`, `TransportRegistry`, `FileServiceRegistry`,
`TransferEngineRegistry`) built on the generic `ComponentRegistry<T>`. `PipelineFactory` reads
`config/pipeline.properties` and looks up each backend by name. A missing or `unset` slot falls back to the default
mock. Registration is explicit (`registerDefaults()`), never as a side effect of class loading.

## Consequences
- Switching a backend is a one-line config change.
- Unknown names fail fast with a message that lists the available backends.
- `.properties` files cannot hold trailing comments - comments go on their own lines.
