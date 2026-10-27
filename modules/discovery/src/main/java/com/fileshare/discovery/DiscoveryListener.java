package com.fileshare.discovery;

import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.port.PeerDirectory;

/**
 * Receives UDP discovery packets, validates them, ignores our own, updates the {@link PeerDirectory} and answers
 * DISCOVER with DISCOVER_RESPONSE. STUB - see the DiscoveryListener issue.
 */
public class DiscoveryListener implements Runnable {

    private final AppConfig config;
    private final PeerDirectory directory;

    public DiscoveryListener(AppConfig config, PeerDirectory directory) {
        this.config = config;
        this.directory = directory;
    }

    @Override
    public void run() {
        // TODO: loop on DatagramSocket.receive(); stop cleanly when the socket is closed
        throw new UnsupportedOperationException("TODO: DiscoveryListener");
    }
}
