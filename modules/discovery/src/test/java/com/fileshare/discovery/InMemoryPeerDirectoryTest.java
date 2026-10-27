package com.fileshare.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.PeerStatus;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class InMemoryPeerDirectoryTest {

    private static Peer peer(String id, Instant seen) throws Exception {
        return new Peer(id, "dev-" + id, InetAddress.getByName("192.168.1.10"), 37021, seen, PeerStatus.ONLINE);
    }

    @Test
    void upsertReplacesExistingPeer() throws Exception {
        InMemoryPeerDirectory directory = new InMemoryPeerDirectory();
        Instant now = Instant.now();
        directory.upsert(peer("a", now));
        directory.upsert(peer("a", now.plusSeconds(1)));
        assertEquals(1, directory.snapshot().size());
    }

    @Test
    void stalePeersBecomeOffline() throws Exception {
        InMemoryPeerDirectory directory = new InMemoryPeerDirectory();
        Instant now = Instant.now();
        directory.upsert(peer("old", now.minusSeconds(30)));
        directory.upsert(peer("fresh", now));
        assertEquals(1, directory.markStaleOffline(Duration.ofSeconds(10), now));
        assertEquals(PeerStatus.OFFLINE, directory.find("old").orElseThrow().status());
        assertEquals(PeerStatus.ONLINE, directory.find("fresh").orElseThrow().status());
        assertTrue(directory.find("missing").isEmpty());
    }
}
