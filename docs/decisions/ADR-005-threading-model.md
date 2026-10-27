# ADR-005: Threading Model and TaskRunner

## Status
Accepted

## Decision
Only `modules/concurrency` creates threads (`TaskExecutor implements TaskRunner`). Everyone else submits work through
`TaskRunner`. The JavaFX thread does UI only; workers update the UI through `Platform.runLater` or JavaFX properties.
Cancellation = `Future.cancel(true)` plus closing the connection and cleaning up files.

## Consequences
- One place to tune pool sizes, naming, error logging and shutdown.
- Tests can pass a tiny fake `TaskRunner`.
