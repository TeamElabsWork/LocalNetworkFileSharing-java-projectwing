package com.fileshare.discovery;

import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.PeerStatus;
import com.fileshare.contracts.port.DiscoveryService;
import com.fileshare.contracts.port.PeerDirectory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

/**
 * Default backend ("mock"): "discovers" three fixed fake devices immediately - no network needed.
 * Lets UI and transfer developers work before UDP discovery exists.
 */
public class MockDiscoveryService implements DiscoveryService {

    private final PeerDirectory directory;

    public MockDiscoveryService(PeerDirectory directory) {
        this.directory = directory;
    }

    @Override
    public void start() {
        Instant now = Instant.now();
        directory.upsert(fake("mock-1", "Laptop-B", new byte[] {(byte) 192, (byte) 168, 1, 20}, 37021, now, PeerStatus.ONLINE));
        directory.upsert(fake("mock-2", "Phone-C", new byte[] {(byte) 192, (byte) 168, 1, 21}, 37021, now, PeerStatus.ONLINE));
        directory.upsert(fake("mock-3", "Old-Desktop", new byte[] {(byte) 192, (byte) 168, 1, 22}, 37021,
                now.minusSeconds(120), PeerStatus.OFFLINE));
    }

    @Override
    public void stop() {
        // nothing to release
    }

    @Override
    public PeerDirectory peers() {
        return directory;
    }

    private static Peer fake(String id, String name, byte[] ip, int port, Instant seen, PeerStatus status) {
        try {
            return new Peer(id, name, InetAddress.getByAddress(ip), port, seen, status);
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e); // cannot happen: 4-byte literal
        }
    }
}
