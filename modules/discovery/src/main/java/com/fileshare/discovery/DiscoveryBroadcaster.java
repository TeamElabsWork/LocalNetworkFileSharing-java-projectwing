package com.fileshare.discovery;

import com.fileshare.contracts.config.AppConfig;

/** Periodically sends the UDP DISCOVER announcement. STUB - see the DiscoveryBroadcaster issue. */
public class DiscoveryBroadcaster implements Runnable {

    private final AppConfig config;

    public DiscoveryBroadcaster(AppConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        // TODO: build packet (docs/protocol.md section 2) and send on every usable interface
        throw new UnsupportedOperationException("TODO: DiscoveryBroadcaster");
    }
}
