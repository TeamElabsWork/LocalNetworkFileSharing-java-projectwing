package com.fileshare.network;

import com.fileshare.contracts.port.PeerConnection;
import com.fileshare.contracts.port.TaskRunner;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Binds the TCP port and runs the accept loop. The loop only accepts; every connection is handed to a worker via the
 * {@link TaskRunner}. STUB - see the TcpServer issue.
 */
public class TcpServer {

    private final int port;
    private final TaskRunner executor;

    public TcpServer(int port, TaskRunner executor) {
        this.port = port;
        this.executor = executor;
    }

    public void start(Consumer<PeerConnection> handler) throws IOException {
        throw new UnsupportedOperationException("TODO: TcpServer.start");
    }

    public void stop() {
        // TODO: close the ServerSocket; unblocks the accept loop
    }

    public int boundPort() {
        throw new UnsupportedOperationException("TODO: TcpServer.boundPort");
    }
}
