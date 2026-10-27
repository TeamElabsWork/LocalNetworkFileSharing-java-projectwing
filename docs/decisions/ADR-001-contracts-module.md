# ADR-001: Contracts Module as the Only Shared Dependency

## Status
Accepted

## Context
Several people develop discovery, networking, transfer, file and UI code at the same time. Direct module-to-module
dependencies cause tight coupling, circular imports and merge conflicts.

## Decision
All shared types and interfaces live in `modules/contracts`. Every other module's `pom.xml` depends on `contracts`
only; only `apps/fileshare-app` depends on all modules. Cross-module communication uses the port interfaces.

## Consequences
- Maven refuses (compile error) any import of another module's classes.
- Changes to `contracts` need review from the affected module owners (`CODEOWNERS`).
- Modules can be developed and tested independently.
