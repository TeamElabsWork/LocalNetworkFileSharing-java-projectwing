# Guide: Implementing a Backend (Replacing a Mock)

Follow these steps whenever you replace a stub with a real implementation or add a new backend.

## The Golden Rules
1. **Only depend on `contracts`.** If you need something from another module, use (or propose) a port interface in
   `contracts`. Never import another module's classes.
2. **Never break contracts.** Add, don't change. Contract changes need sign-off from affected owners (`CODEOWNERS`).
3. **Threads only through `TaskRunner`.** No `new Thread`, no own executors.
4. **Register, don't hard-wire.** Add your backend to your module's registry and select it in
   `config/pipeline.properties`. No registration in static initializers - use `registerDefaults()`.
5. **Mocks stay.** Keep the mock backend working: other teammates build against it.
6. **Pass the contract test.** Transports must pass `AbstractTransportTest`; add the same kind of reusable test for
   other ports where useful.

## Example: implementing the UDP discovery backend (`discovery`, M2)

### Step 1 - work in your module
`modules/discovery/src/main/java/com/fileshare/discovery/`

### Step 2 - implement the port
`UdpDiscoveryService` already exists as a stub implementing `DiscoveryService`. Fill in the helper classes first
(`NetworkUtils`, `DiscoveryListener`, `DiscoveryBroadcaster`), then `start()` / `stop()`:

```java
@Override
public void start() throws IOException {
    executor.submit(new DiscoveryListener(config, directory));
    executor.scheduleAtFixedRate(new DiscoveryBroadcaster(config), 0, config.discoveryIntervalMs());
}
```

### Step 3 - it is already registered
`DiscoveryRegistry.registerDefaults()` registers `udp`. For a brand-new backend add a line there:
```java
register("mdns", ctx -> new MdnsDiscoveryService(ctx.config(), ctx.peers(), ctx.executor()));
```

### Step 4 - switch the config
```properties
backend.discovery=udp
```
Run `make demo` - the CLI now lists real devices instead of the three mock ones.

### Step 5 - tests
- Unit tests in `modules/discovery/src/test/java/...` - no network needed where possible.
- Never depend on another module in tests: if you need a `TaskRunner`, write a 10-line one in the test
  (see `LoopbackTransportTest`).

## Example: implementing the TCP transport (`network`, M3)
1. Implement `TcpConnection`, `TcpServer`, `ProtocolCodec`; `TcpTransport` composes them.
2. Create `TcpTransportTest extends AbstractTransportTest` (provide `newServerTransport()` / `newClientTransport()`
   using port 0). It must pass **without changing** the abstract test.
3. `backend.transport=tcp`.

## Example: implementing the real transfer engine (`transfer`, M4)
`NetworkTransferEngine` gets the transport and file service through its constructor (from `BackendContext`).
Fill in `send()`, the incoming-connection handler, `UploadTask` and `DownloadTask`. Develop it against the
`loopback` transport and the `local` file service, then switch all of these to the real ones at the end:
```properties
backend.engine=network
backend.transport=tcp
backend.files=local
backend.discovery=udp
```

## Checklist before opening your PR
- [ ] `make check` passes
- [ ] Mock backend still works (`make demo` with default config)
- [ ] Your backend works when selected in `config/pipeline.properties`
- [ ] Docs updated (`modules/<name>/README.md`, `docs/protocol.md` if you touched the protocol)
