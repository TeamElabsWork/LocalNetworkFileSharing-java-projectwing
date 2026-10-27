package com.fileshare.contracts.port;

import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.TransferSession;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Public API of the transfer layer - UIs only talk to this interface. Implementations: simulated, network.
 */
public interface TransferEngine {

    /** Starts listening for incoming requests. */
    void start() throws IOException;

    void stop();

    /** Starts sending {@code file} to {@code peer}; returns immediately with the new session. */
    TransferSession send(Peer peer, Path file) throws IOException;

    /** Cancels a running transfer (either direction). No-op for finished transfers. */
    void cancel(String transferId);

    /** Installs the callback that decides on incoming requests (default: reject everything). */
    void setIncomingRequestHandler(IncomingRequestHandler handler);

    /**
     * Demo/test hook: behave as if {@code from} just asked to send {@code file}. Runs the handler on the CALLING
     * thread, then continues on a worker. Real engines need not support it.
     */
    default TransferSession injectIncomingRequest(FileMetadata file, Peer from) {
        throw new UnsupportedOperationException("injectIncomingRequest is only supported by the simulated engine");
    }
}
