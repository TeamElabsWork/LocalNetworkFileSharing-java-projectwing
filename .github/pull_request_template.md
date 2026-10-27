## What does this PR do?
<!-- 1-3 sentences -->

Closes #

## How was it tested?
- [ ] `make check` (`mvn -B verify`) passes
- [ ] Unit tests added/updated
- [ ] Tried manually (describe)

## Checklist
- [ ] Only touches my module (and `contracts` if agreed); no imports of other modules
- [ ] Mock/default backend still works (`make demo`)
- [ ] No public-signature change to shared classes (or it is called out above)
- [ ] Sockets/streams are closed on success and failure paths
- [ ] No network/disk work on the JavaFX thread
