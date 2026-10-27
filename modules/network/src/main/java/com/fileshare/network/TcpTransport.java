package com.fileshare.network;

import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.port.PeerConnection;
import com.fileshare.contracts.port.TaskRunner;
import com.fileshare.contracts.port.TransferTransport;

import java.io.IOException;
import java.net.InetAddress;
import java.util.function.Consumer;

/**
 * REAL backend ("tcp") - STUB. Composes {@link TcpServer} and {@link TcpConnection}. Must pass the same
 * AbstractTransportTest as {@link LoopbackTransport}. Select with {@code backend.transport=tcp}.
 */
public class TcpTransport implements TransferTransport {

    private final TcpServer server;

    public TcpTransport(AppConfig config, TaskRunner executor) {
        this.server = new TcpServer(config.tcpPort(), executor);
    }

    @Override
    public void start(Consumer<PeerConnection> handler) throws IOException {
        server.start(handler);
    }

    @Override
    public void stop() {
        server.stop();
    }

    @Override
    public PeerConnection connect(InetAddress address, int port, int timeoutMs) throws IOException {
        return TcpConnection.connect(address, port, timeoutMs);
    }

    @Override
    public int boundPort() {
        return server.boundPort();
    }
}
