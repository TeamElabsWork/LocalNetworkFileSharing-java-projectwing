package com.fileshare.network;

import com.fileshare.contracts.port.PeerConnection;
import com.fileshare.contracts.protocol.ProtocolException;
import com.fileshare.contracts.protocol.ProtocolMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

/** A {@link PeerConnection} over a real TCP socket. STUB - see the TcpConnection issue. */
public class TcpConnection implements PeerConnection {

    /** Opens an outgoing connection with a connect timeout. */
    public static TcpConnection connect(InetAddress address, int port, int timeoutMs) throws IOException {
        throw new UnsupportedOperationException("TODO: TcpConnection.connect");
    }

    @Override
    public void send(ProtocolMessage message) throws IOException {
        throw new UnsupportedOperationException("TODO: TcpConnection.send");
    }

    @Override
    public ProtocolMessage receive() throws IOException, ProtocolException {
        throw new UnsupportedOperationException("TODO: TcpConnection.receive");
    }

    @Override
    public InputStream rawInput() throws IOException {
        throw new UnsupportedOperationException("TODO: TcpConnection.rawInput");
    }

    @Override
    public OutputStream rawOutput() throws IOException {
        throw new UnsupportedOperationException("TODO: TcpConnection.rawOutput");
    }

    @Override
    public String remoteDescription() {
        throw new UnsupportedOperationException("TODO: TcpConnection.remoteDescription");
    }

    @Override
    public void close() {
        // TODO: close socket and streams; idempotent
    }
}
