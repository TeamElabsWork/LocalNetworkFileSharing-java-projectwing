package com.fileshare.contracts.model;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Objects;

/**
 * Another device running the app. Immutable: to "update" a peer create a copy with
 * {@link #withLastSeen(Instant)} / {@link #withStatus(PeerStatus)} and store that.
 *
 * @param peerId     unique ID announced by the peer (never trust the IP alone)
 * @param deviceName human-readable name
 * @param address    IP address the peer was seen at
 * @param tcpPort    TCP port of the peer's transfer server
 * @param lastSeen   time of the last discovery packet
 * @param status     ONLINE / OFFLINE
 */
public record Peer(String peerId, String deviceName, InetAddress address, int tcpPort,
                   Instant lastSeen, PeerStatus status) {

    public Peer {
        Objects.requireNonNull(peerId, "peerId");
        Objects.requireNonNull(deviceName, "deviceName");
        Objects.requireNonNull(address, "address");
        Objects.requireNonNull(lastSeen, "lastSeen");
        Objects.requireNonNull(status, "status");
        if (tcpPort < 1 || tcpPort > 65_535) {
            throw new IllegalArgumentException("tcpPort out of range: " + tcpPort);
        }
    }

    public Peer withLastSeen(Instant when) { return new Peer(peerId, deviceName, address, tcpPort, when, status); }

    public Peer withStatus(PeerStatus newStatus) { return new Peer(peerId, deviceName, address, tcpPort, lastSeen, newStatus); }
}
