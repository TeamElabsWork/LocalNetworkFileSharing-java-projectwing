package com.fileshare.network;

import com.fileshare.contracts.port.PeerConnection;
import com.fileshare.contracts.port.TaskRunner;
import com.fileshare.contracts.port.TransferTransport;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.function.Consumer;

/**
 * Default backend ("loopback"): an in-memory {@link TransferTransport}. No sockets, no firewall, deterministic -
 * it is also the reference behaviour that {@code TcpTransport} must match (see AbstractTransportTest).
 */
public class LoopbackTransport implements TransferTransport {

    private final LoopbackNetwork network;
    private final TaskRunner executor;
    private final int configuredPort;
    private volatile Consumer<PeerConnection> handler;
    private volatile int boundPort = -1;

    public LoopbackTransport(LoopbackNetwork network, TaskRunner executor, int port) {
        this.network = network;
        this.executor = executor;
        this.configuredPort = port;
    }

    @Override
    public void start(Consumer<PeerConnection> connectionHandler) throws IOException {
        this.handler = connectionHandler;
        this.boundPort = network.bind(configuredPort, this);
    }

    @Override
    public void stop() {
        if (boundPort >= 0) {
            network.unbind(boundPort);
            boundPort = -1;
        }
    }

    @Override
    public PeerConnection connect(InetAddress address, int port, int timeoutMs) throws IOException {
        LoopbackTransport target = network.lookup(port);
        if (target == null || target.handler == null) {
            throw new ConnectException("Connection refused: nothing listening on port " + port);
        }
        LoopbackConnection[] pair = LoopbackConnection.pair("loopback:" + port, "loopback-client");
        Consumer<PeerConnection> h = target.handler;
        // The handler owns the server-side connection and must close it.
        target.executor.submit(() -> h.accept(pair[1]));
        return pair[0];
    }

    @Override
    public int boundPort() {
        return boundPort;
    }
}
