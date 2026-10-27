package com.fileshare.discovery;

import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.PeerStatus;
import com.fileshare.contracts.port.PeerDirectory;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Thread-safe in-memory {@link PeerDirectory}, keyed by peerId. Baseline: storage + stale expiry. Change listeners
 * for the UI come with the "PeerDirectory: change notifications" issue.
 */
public class InMemoryPeerDirectory implements PeerDirectory {

    private final ConcurrentMap<String, Peer> peers = new ConcurrentHashMap<>();

    @Override
    public void upsert(Peer peer) {
        peers.put(peer.peerId(), peer);
    }

    @Override
    public Optional<Peer> find(String peerId) {
        return Optional.ofNullable(peers.get(peerId));
    }

    @Override
    public void remove(String peerId) {
        peers.remove(peerId);
    }

    @Override
    public List<Peer> snapshot() {
        return peers.values().stream().sorted(java.util.Comparator.comparing(Peer::deviceName)).toList();
    }

    @Override
    public int markStaleOffline(Duration timeout, Instant now) {
        int changed = 0;
        for (Peer p : peers.values()) {
            boolean stale = p.lastSeen().plus(timeout).isBefore(now);
            if (stale && p.status() == PeerStatus.ONLINE) {
                peers.replace(p.peerId(), p, p.withStatus(PeerStatus.OFFLINE));
                changed++;
            }
        }
        return changed;
    }
}
