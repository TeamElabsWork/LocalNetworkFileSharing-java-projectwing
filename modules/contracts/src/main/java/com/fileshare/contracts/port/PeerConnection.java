package com.fileshare.contracts.port;

import com.fileshare.contracts.protocol.ProtocolException;
import com.fileshare.contracts.protocol.ProtocolMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An established connection to a peer: framed control messages plus raw streams for the file bytes that follow
 * TRANSFER_ACCEPT. Always close it (try-with-resources).
 */
public interface PeerConnection extends AutoCloseable {

    void send(ProtocolMessage message) throws IOException;

    /** Blocks until the next message arrives. Throws {@link ProtocolException} for malformed input. */
    ProtocolMessage receive() throws IOException, ProtocolException;

    /** Raw stream of file bytes coming from the peer. */
    InputStream rawInput() throws IOException;

    /** Raw stream of file bytes going to the peer. */
    OutputStream rawOutput() throws IOException;

    /** Human-readable description of the remote end, for logs. */
    String remoteDescription();

    /** Idempotent. */
    @Override
    void close();
}
