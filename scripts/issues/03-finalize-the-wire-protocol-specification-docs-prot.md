title: [network] Finalize the wire protocol specification (docs/protocol.md)
milestone: 0 - Foundation
labels: module:network|module:docs|difficulty:medium|priority:mvp|help wanted
---
**Module:** `network` (M3), `docs` (M8) · **Milestone:** 0 - Foundation · **Difficulty:** medium · **Priority:** MVP  
**Suggested branch:** `docs/protocol-spec`

> **Want to work on this?** Comment **"I'll take this"** below and the maintainer will assign it to you. One person per issue; check *Depends on* first. Read `CONTRIBUTING.md` and `docs/adding-a-module.md` before you start.

## Description
`docs/protocol.md` is a draft. Turn it into the final spec that discovery, TCP, transfer and UI all code against. This is the single most important coordination document: discovery packet layout, TCP frame format, message fields, sequence for accept/reject/cancel/hash result, validation rules and timeouts.

## Tasks
- [ ] Review every section of the draft with the discovery, transfer and integrity owners.
- [ ] Decide the exact field names/types for every message in section 4.
- [ ] Decide error handling: what happens on unknown message type, wrong order, oversized frame.
- [ ] Add sequence diagrams for: happy path, rejection, cancellation (sender and receiver), integrity failure.
- [ ] Add a 'version' field or document why it is deferred (the plan postpones protocol versioning).
- [ ] Mark the document 'v1 - agreed' and link it from the README.

## Files / classes
- `docs/protocol.md`

## Acceptance criteria
- [ ] Spec covers every `MessageType`.
- [ ] A second person can implement the codec from the document alone.
- [ ] Approved by at least discovery, TCP and transfer owners.

## Depends on
- #2 Review the baseline and agree on shared contracts (class signatures, ports, ownership)

## Notes
Owner: TCP & protocol member (Module 3). Blocks the codec, discovery message and upload/download issues.

## Definition of done
- [ ] PR merged into `main` with `Closes #3`, `make check` green (CI), module owner approval, tests added where applicable.
- [ ] Only your module (and `contracts` if agreed) changed; no imports of other modules.
