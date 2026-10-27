package com.fileshare.contracts.port;

import com.fileshare.contracts.model.Peer;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Thread-safe store of discovered peers. Written by discovery threads, read by the UIs and the transfer engine. */
public interface PeerDirectory {

    /** Adds a new peer or refreshes an existing one. */
    void upsert(Peer peer);

    Optional<Peer> find(String peerId);

    void remove(String peerId);

    /** Snapshot of all known peers (ONLINE and OFFLINE). */
    List<Peer> snapshot();

    /** Marks peers not seen within {@code timeout} as OFFLINE. Returns how many changed. */
    int markStaleOffline(Duration timeout, Instant now);
}
