package com.fileshare.app;

import com.fileshare.concurrency.TaskExecutor;
import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.port.DiscoveryService;
import com.fileshare.contracts.port.FileService;
import com.fileshare.contracts.port.PeerDirectory;
import com.fileshare.contracts.port.TaskRunner;
import com.fileshare.contracts.port.TransferEngine;
import com.fileshare.contracts.port.TransferStore;
import com.fileshare.contracts.port.TransferTransport;
import com.fileshare.contracts.registry.BackendContext;
import com.fileshare.discovery.DiscoveryRegistry;
import com.fileshare.discovery.InMemoryPeerDirectory;
import com.fileshare.file.FileServiceRegistry;
import com.fileshare.network.TransportRegistry;
import com.fileshare.transfer.InMemoryTransferStore;
import com.fileshare.transfer.TransferEngineRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Builds an {@link AppContext} from {@code config/pipeline.properties}: looks up each backend by name in its module's
 * registry (discovery, transport, files, engine). Names that are missing or "unset" fall back to the safe mock
 * backend and log it. This is the ONLY place that knows all modules.
 *
 * <p>Wiring order matters: transport -> files -> discovery -> engine (the engine needs the first two).
 */
public final class PipelineFactory {
    private static final Logger LOG = Logger.getLogger(PipelineFactory.class.getName());

    private PipelineFactory() {}

    public static AppContext create(Path configDir) throws IOException {
        Properties options = new Properties();
        Path file = configDir.resolve("pipeline.properties");
        if (Files.isRegularFile(file)) {
            try (InputStream in = Files.newInputStream(file)) {
                options.load(in);
            }
        } else {
            LOG.warning("No " + file + " found - using default (mock) backends.");
        }
        return create(options);
    }

    public static AppContext create(Properties options) {
        DiscoveryRegistry.reset();
        DiscoveryRegistry.registerDefaults();
        TransportRegistry.reset();
        TransportRegistry.registerDefaults();
        FileServiceRegistry.reset();
        FileServiceRegistry.registerDefaults();
        TransferEngineRegistry.reset();
        TransferEngineRegistry.registerDefaults();

        AppConfig config = AppConfig.defaults();
        TaskRunner executor = new TaskExecutor();
        PeerDirectory peers = new InMemoryPeerDirectory();
        TransferStore transfers = new InMemoryTransferStore();
        BackendContext ctx = new BackendContext(config, executor, peers, transfers, null, null, options);

        TransferTransport transport = TransportRegistry.get(backend(options, "transport"), ctx);
        ctx = ctx.withTransport(transport);
        FileService files = FileServiceRegistry.get(backend(options, "files"), ctx);
        ctx = ctx.withFiles(files);
        DiscoveryService discovery = DiscoveryRegistry.get(backend(options, "discovery"), ctx);
        TransferEngine engine = TransferEngineRegistry.get(backend(options, "engine"), ctx);

        return new AppContext(config, executor, peers, transfers, transport, files, discovery, engine);
    }

    private static String backend(Properties options, String slot) {
        String value = options.getProperty("backend." + slot);
        if (value == null || value.isBlank() || value.trim().equalsIgnoreCase("unset")) {
            LOG.info("backend." + slot + " is unset - using the default (mock) backend.");
            return "default";
        }
        return value.trim();
    }
}
