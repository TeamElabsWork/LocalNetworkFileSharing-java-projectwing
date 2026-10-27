package com.fileshare.contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.PeerStatus;
import com.fileshare.contracts.model.TransferDirection;
import com.fileshare.contracts.model.TransferProgress;
import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.model.TransferStatus;
import com.fileshare.contracts.protocol.MessageType;
import com.fileshare.contracts.protocol.ProtocolMessage;
import java.net.InetAddress;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ModelTest {
    private static final String HASH = "a".repeat(64);

    @Test
    void fileMetadataValidates() {
        new FileMetadata("id", "a.txt", 0, HASH);
        assertThrows(IllegalArgumentException.class, () -> new FileMetadata("id", " ", 1, HASH));
        assertThrows(IllegalArgumentException.class, () -> new FileMetadata("id", "a.txt", -1, HASH));
        assertThrows(IllegalArgumentException.class, () -> new FileMetadata("id", "a.txt", 1, "xyz"));
    }

    @Test
    void peerIsImmutableAndValidated() throws Exception {
        InetAddress ip = InetAddress.getByName("192.168.1.5");
        Peer p = new Peer("id", "name", ip, 37021, Instant.EPOCH, PeerStatus.ONLINE);
        Peer off = p.withStatus(PeerStatus.OFFLINE);
        assertEquals(PeerStatus.ONLINE, p.status());
        assertEquals(PeerStatus.OFFLINE, off.status());
        assertThrows(IllegalArgumentException.class, () -> new Peer("id", "n", ip, 0, Instant.EPOCH, PeerStatus.ONLINE));
    }

    @Test
    void progressTracksPercentage() {
        TransferProgress p = new TransferProgress(200);
        p.start();
        p.add(50);
        assertEquals(25.0, p.percentage(), 0.001);
        p.add(150);
        assertEquals(100.0, p.percentage(), 0.001);
    }

    @Test
    void emptyFileIsCompleteOnceStarted() {
        TransferProgress p = new TransferProgress(0);
        assertEquals(0.0, p.percentage());
        p.start();
        assertEquals(100.0, p.percentage());
    }

    @Test
    void sessionStatusHelpers() {
        TransferSession s = new TransferSession(new FileMetadata("id", "a", 1, HASH), null, TransferDirection.UPLOAD);
        assertEquals(TransferStatus.PENDING, s.status());
        assertFalse(s.status().isTerminal());
        s.fail("disk full");
        assertEquals(TransferStatus.FAILED, s.status());
        assertEquals("disk full", s.message().orElseThrow());
        assertTrue(TransferStatus.REJECTED.isTerminal());
    }

    @Test
    void protocolMessageFieldsAreImmutable() {
        Map<String, String> fields = new HashMap<>(Map.of("k", "v"));
        ProtocolMessage m = new ProtocolMessage(MessageType.CANCEL, "t", fields);
        fields.put("x", "y");
        assertEquals(1, m.fields().size());
        assertThrows(UnsupportedOperationException.class, () -> m.fields().put("a", "b"));
    }
}
