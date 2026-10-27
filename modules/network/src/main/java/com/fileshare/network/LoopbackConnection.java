package com.fileshare.network;

import com.fileshare.contracts.port.PeerConnection;
import com.fileshare.contracts.protocol.ProtocolException;
import com.fileshare.contracts.protocol.ProtocolMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/** One end of an in-memory connection created by {@link LoopbackTransport}. */
final class LoopbackConnection implements PeerConnection {

    private static final Object CLOSED = new Object();

    private final String remote;
    private final BlockingQueue<Object> inbox = new LinkedBlockingQueue<>();
    private final BytePipe rawIn;
    private final BytePipe rawOut;
    private final AtomicBoolean closed = new AtomicBoolean();
    private LoopbackConnection peer;

    private LoopbackConnection(String remote, BytePipe rawIn, BytePipe rawOut) {
        this.remote = remote;
        this.rawIn = rawIn;
        this.rawOut = rawOut;
    }

    /** Creates two connected ends: [0] = client side, [1] = server side. */
    static LoopbackConnection[] pair(String clientRemote, String serverRemote) {
        BytePipe clientToServer = new BytePipe();
        BytePipe serverToClient = new BytePipe();
        LoopbackConnection client = new LoopbackConnection(clientRemote, serverToClient, clientToServer);
        LoopbackConnection server = new LoopbackConnection(serverRemote, clientToServer, serverToClient);
        client.peer = server;
        server.peer = client;
        return new LoopbackConnection[] {client, server};
    }

    @Override
    public void send(ProtocolMessage message) throws IOException {
        if (closed.get()) throw new IOException("connection closed");
        peer.inbox.add(message);
    }

    @Override
    public ProtocolMessage receive() throws IOException, ProtocolException {
        try {
            Object next = inbox.take();
            if (next == CLOSED) {
                inbox.add(CLOSED); // keep later receive() calls failing too
                throw new EOFException("connection closed");
            }
            return (ProtocolMessage) next;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("interrupted while waiting for a message", e);
        }
    }

    @Override
    public InputStream rawInput() {
        return rawIn.input;
    }

    @Override
    public OutputStream rawOutput() {
        return rawOut.output;
    }

    @Override
    public String remoteDescription() {
        return remote;
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            rawOut.closeWriter();
            inbox.add(CLOSED);       // unblock our own pending receive()
            peer.inbox.add(CLOSED);  // tell the other end
        }
    }
}
