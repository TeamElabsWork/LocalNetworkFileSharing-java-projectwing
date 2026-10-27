package com.fileshare.contracts.port;

import java.io.IOException;
import java.net.InetAddress;
import java.util.function.Consumer;

/** Moves {@link PeerConnection}s between devices. Implementations: loopback (in-memory), tcp. */
public interface TransferTransport {

    /** Starts accepting incoming connections; each one is handed to {@code handler} on a worker thread. */
    void start(Consumer<PeerConnection> handler) throws IOException;

    void stop();

    /** Opens an outgoing connection. */
    PeerConnection connect(InetAddress address, int port, int timeoutMs) throws IOException;

    /** Port actually listening (useful when started with port 0 in tests). */
    int boundPort();
}
