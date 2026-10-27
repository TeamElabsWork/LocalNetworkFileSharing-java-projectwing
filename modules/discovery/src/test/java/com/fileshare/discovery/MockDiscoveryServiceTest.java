package com.fileshare.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fileshare.contracts.model.PeerStatus;
import org.junit.jupiter.api.Test;

class MockDiscoveryServiceTest {

    @Test
    void publishesFakePeers() {
        InMemoryPeerDirectory directory = new InMemoryPeerDirectory();
        new MockDiscoveryService(directory).start();
        assertEquals(3, directory.snapshot().size());
        assertTrue(directory.find("mock-1").isPresent());
        assertEquals(PeerStatus.OFFLINE, directory.find("mock-3").orElseThrow().status());
    }

    @Test
    void registryKnowsMockAndUdp() {
        DiscoveryRegistry.reset();
        DiscoveryRegistry.registerDefaults();
        assertTrue(DiscoveryRegistry.listAvailable().containsAll(java.util.List.of("mock", "default", "udp")));
    }
}
