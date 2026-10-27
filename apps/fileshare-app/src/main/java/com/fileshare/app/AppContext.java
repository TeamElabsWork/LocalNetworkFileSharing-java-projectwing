package com.fileshare.app;

import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.port.DiscoveryService;
import com.fileshare.contracts.port.FileService;
import com.fileshare.contracts.port.PeerDirectory;
import com.fileshare.contracts.port.TaskRunner;
import com.fileshare.contracts.port.TransferEngine;
import com.fileshare.contracts.port.TransferStore;
import com.fileshare.contracts.port.TransferTransport;
import com.fileshare.contracts.port.UiServices;

import java.io.IOException;

/** All wired services of one running app instance. Built by {@link PipelineFactory}. */
public final class AppContext implements AutoCloseable {

    private final AppConfig config;
    private final TaskRunner executor;
    private final PeerDirectory peers;
    private final TransferStore transfers;
    private final TransferTransport transport;
    private final FileService files;
    private final DiscoveryService discovery;
    private final TransferEngine engine;

    AppContext(AppConfig config, TaskRunner executor, PeerDirectory peers, TransferStore transfers,
               TransferTransport transport, FileService files, DiscoveryService discovery, TransferEngine engine) {
        this.config = config;
        this.executor = executor;
        this.peers = peers;
        this.transfers = transfers;
        this.transport = transport;
        this.files = files;
        this.discovery = discovery;
        this.engine = engine;
    }

    /** Starts discovery and the engine. Call once at startup. */
    public void start() throws IOException {
        discovery.start();
        engine.start();
    }

    @Override
    public void close() {
        engine.stop();
        discovery.stop();
        transport.stop();
        executor.shutdown();
    }

    public UiServices uiServices() {
        return new UiServices(config, peers, engine, transfers);
    }

    public AppConfig config() { return config; }
    public PeerDirectory peers() { return peers; }
    public TransferStore transfers() { return transfers; }
    public TransferEngine engine() { return engine; }
    public DiscoveryService discovery() { return discovery; }
    public FileService files() { return files; }
    public TransferTransport transport() { return transport; }
}
