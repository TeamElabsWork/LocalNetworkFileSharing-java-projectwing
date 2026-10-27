package com.fileshare.network;

import com.fileshare.contracts.protocol.ProtocolException;
import com.fileshare.contracts.protocol.ProtocolMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Encodes/decodes {@link ProtocolMessage}s to/from the wire format in docs/protocol.md (length-prefixed, via
 * DataOutputStream/DataInputStream). Pure framing - no business logic, no sockets.
 *
 * <p>STUB - see the ProtocolCodec issue. Must reject oversized or malformed frames with {@link ProtocolException}
 * instead of crashing or allocating unbounded memory.
 */
public final class ProtocolCodec {
    private ProtocolCodec() {}

    /** Hard upper bound for one control frame, to prevent memory exhaustion by a bad peer. */
    public static final int MAX_FRAME_BYTES = 64 * 1024;

    public static void write(DataOutputStream out, ProtocolMessage message) throws IOException {
        throw new UnsupportedOperationException("TODO: ProtocolCodec.write");
    }

    public static ProtocolMessage read(DataInputStream in) throws IOException, ProtocolException {
        throw new UnsupportedOperationException("TODO: ProtocolCodec.read");
    }
}
