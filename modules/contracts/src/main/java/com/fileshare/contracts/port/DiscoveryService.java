package com.fileshare.contracts.port;

import java.io.IOException;

/** Finds other devices and keeps the {@link PeerDirectory} up to date. Implementations: mock, udp. */
public interface DiscoveryService {

    /** Starts discovery. Must not block the caller. */
    void start() throws IOException;

    /** Stops all discovery activity and releases sockets. Safe to call twice. */
    void stop();

    PeerDirectory peers();
}
