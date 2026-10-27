# ui-javafx (M7)

JavaFX desktop interface. Baseline: a window that shows the device name and number of discovered devices.

| Class | Status |
|---|---|
| `FxLauncher`, `FileShareFxApp`, `MainController` | working skeleton |
| `DeviceController`, `TransferController` | STUBS - see the UI issues |

Rules: use only `UiServices`; never block the JavaFX thread; update the UI via `Platform.runLater`/properties.
Run it: `make gui` (or `mvn -q -pl apps/fileshare-app exec:java -Dexec.args="--gui"`).
Depends on: `contracts` + JavaFX only.
