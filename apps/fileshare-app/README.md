# apps/fileshare-app

Wires everything together and provides the entry point (`com.fileshare.app.Main`).

- `PipelineFactory` - builds the app from `config/pipeline.properties` (backend per slot, falls back to mocks).
- `AppContext` - the wired services; `start()` / `close()`.
- `Main` - CLI demo by default, `--gui` for JavaFX.

This is the only module allowed to depend on all other modules.
