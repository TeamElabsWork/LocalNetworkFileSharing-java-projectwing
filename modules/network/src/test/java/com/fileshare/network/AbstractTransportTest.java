package com.fileshare.network;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fileshare.contracts.port.PeerConnection;
import com.fileshare.contracts.port.TransferTransport;
import com.fileshare.contracts.protocol.MessageType;
import com.fileshare.contracts.protocol.ProtocolMessage;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * CONTRACT TEST for every {@link TransferTransport}. {@code LoopbackTransportTest} runs it against the loopback
 * backend; the TcpTransport issue adds a {@code TcpTransportTest} that extends this class and must pass unchanged.
 */
abstract class AbstractTransportTest {

    /** A transport that listens on an ephemeral port (port 0). */
    protected abstract TransferTransport newServerTransport();

    /** A transport used only to call connect(); may be the same kind as the server. */
    protected abstract TransferTransport newClientTransport();

    private TransferTransport server;

    @AfterEach
    void tearDown() {
        if (server != null) server.stop();
    }

    private BlockingQueue<PeerConnection> startServer() throws IOException {
        server = newServerTransport();
        BlockingQueue<PeerConnection> accepted = new LinkedBlockingQueue<>();
        server.start(accepted::add);
        return accepted;
    }

    @Test
    void exchangesControlMessagesBothWays() throws Exception {
        BlockingQueue<PeerConnection> accepted = startServer();
        try (PeerConnection client = newClientTransport().connect(InetAddress.getLoopbackAddress(), server.boundPort(), 2000)) {
            ProtocolMessage request = new ProtocolMessage(MessageType.TRANSFER_REQUEST, "t-1", Map.of("fileName", "a.txt"));
            client.send(request);
            PeerConnection serverSide = accepted.poll(5, TimeUnit.SECONDS);
            assertEquals(request, serverSide.receive());
            ProtocolMessage reply = ProtocolMessage.of(MessageType.TRANSFER_ACCEPT, "t-1");
            serverSide.send(reply);
            assertEquals(reply, client.receive());
        }
    }

    @Test
    void streamsRawBytesUntilClose() throws Exception {
        BlockingQueue<PeerConnection> accepted = startServer();
        byte[] data = new byte[200_000];
        new java.util.Random(7).nextBytes(data);
        PeerConnection client = newClientTransport().connect(InetAddress.getLoopbackAddress(), server.boundPort(), 2000);
        PeerConnection serverSide = accepted.poll(5, TimeUnit.SECONDS);
        client.rawOutput().write(data);
        client.rawOutput().flush();
        client.close();
        assertArrayEquals(data, serverSide.rawInput().readAllBytes());
    }

    @Test
    void connectToNothingFails() throws Exception {
        TransferTransport client = newClientTransport();
        assertThrows(IOException.class, () -> client.connect(InetAddress.getLoopbackAddress(), 1, 500));
    }

    @Test
    void receiveFailsAfterPeerCloses() throws Exception {
        BlockingQueue<PeerConnection> accepted = startServer();
        PeerConnection client = newClientTransport().connect(InetAddress.getLoopbackAddress(), server.boundPort(), 2000);
        PeerConnection serverSide = accepted.poll(5, TimeUnit.SECONDS);
        client.close();
        assertThrows(IOException.class, serverSide::receive);
    }
}
