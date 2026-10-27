package com.fileshare.transfer;

import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.port.FileService;
import com.fileshare.contracts.port.IncomingRequestHandler;
import com.fileshare.contracts.port.TaskRunner;
import com.fileshare.contracts.port.TransferEngine;
import com.fileshare.contracts.port.TransferStore;
import com.fileshare.contracts.port.TransferTransport;

import java.io.IOException;
import java.nio.file.Path;

/**
 * REAL backend ("network") - STUB. send(): validate file, describe it, connect through the {@link TransferTransport},
 * send TRANSFER_REQUEST, wait for the decision, submit an {@link UploadTask}. Incoming connections: validate the
 * request, ask the {@link IncomingRequestHandler}, reply ACCEPT/REJECT, submit a {@link DownloadTask}.
 * Implemented in the "TransferService" issue; select with {@code backend.engine=network}.
 */
public class NetworkTransferEngine implements TransferEngine {

    private final AppConfig config;
    private final TaskRunner executor;
    private final TransferStore store;
    private final FileService files;
    private final TransferTransport transport;
    private volatile IncomingRequestHandler handler =
            (file, sender) -> com.fileshare.contracts.port.ApprovalDecision.reject("No handler installed");

    public NetworkTransferEngine(AppConfig config, TaskRunner executor, TransferStore store, FileService files,
                                 TransferTransport transport) {
        this.config = config;
        this.executor = executor;
        this.store = store;
        this.files = files;
        this.transport = transport;
    }

    @Override
    public void start() throws IOException {
        throw new UnsupportedOperationException("TODO: NetworkTransferEngine.start (transport.start(this::handleIncoming))");
    }

    @Override
    public void stop() {
        // TODO
    }

    @Override
    public TransferSession send(Peer peer, Path file) throws IOException {
        throw new UnsupportedOperationException("TODO: NetworkTransferEngine.send");
    }

    @Override
    public void cancel(String transferId) {
        throw new UnsupportedOperationException("TODO: NetworkTransferEngine.cancel");
    }

    @Override
    public void setIncomingRequestHandler(IncomingRequestHandler newHandler) {
        this.handler = newHandler;
    }
}
