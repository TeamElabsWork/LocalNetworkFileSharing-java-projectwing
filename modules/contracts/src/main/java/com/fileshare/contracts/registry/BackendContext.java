package com.fileshare.contracts.registry;

import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.port.FileService;
import com.fileshare.contracts.port.PeerDirectory;
import com.fileshare.contracts.port.TaskRunner;
import com.fileshare.contracts.port.TransferStore;
import com.fileshare.contracts.port.TransferTransport;

import java.util.Properties;

/**
 * Everything a backend factory may need to build itself. Fields are filled in wiring order, so a factory can only
 * rely on the ones created before it (see PipelineFactory in the app module); later ones are {@code null}.
 *
 * @param options free-form key/values from config/pipeline.properties (e.g. {@code simulated.step_ms})
 */
public record BackendContext(AppConfig config, TaskRunner executor, PeerDirectory peers, TransferStore transfers,
                             TransferTransport transport, FileService files, Properties options) {

    public BackendContext withTransport(TransferTransport t) {
        return new BackendContext(config, executor, peers, transfers, t, files, options);
    }

    public BackendContext withFiles(FileService f) {
        return new BackendContext(config, executor, peers, transfers, transport, f, options);
    }

    public int intOption(String key, int fallback) {
        String v = options == null ? null : options.getProperty(key);
        return v == null || v.isBlank() ? fallback : Integer.parseInt(v.trim());
    }
}
