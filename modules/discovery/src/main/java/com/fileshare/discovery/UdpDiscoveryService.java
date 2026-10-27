package com.fileshare.discovery;

import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.port.DiscoveryService;
import com.fileshare.contracts.port.PeerDirectory;
import com.fileshare.contracts.port.TaskRunner;

import java.io.IOException;

/**
 * REAL backend ("udp") - STUB. Runs {@link DiscoveryListener}, schedules {@link DiscoveryBroadcaster} and a sweeper
 * that marks silent peers OFFLINE. Implemented in the "DiscoveryService lifecycle" issue; select it with
 * {@code backend.discovery=udp} in config/pipeline.properties.
 */
public class UdpDiscoveryService implements DiscoveryService {

    private final AppConfig config;
    private final PeerDirectory directory;
    private final TaskRunner executor;

    public UdpDiscoveryService(AppConfig config, PeerDirectory directory, TaskRunner executor) {
        this.config = config;
        this.directory = directory;
        this.executor = executor;
    }

    @Override
    public void start() throws IOException {
        throw new UnsupportedOperationException("TODO: UdpDiscoveryService.start (see issue 'DiscoveryService lifecycle')");
    }

    @Override
    public void stop() {
        // TODO: stop listener/broadcaster/sweeper, close sockets; idempotent
    }

    @Override
    public PeerDirectory peers() {
        return directory;
    }
}
