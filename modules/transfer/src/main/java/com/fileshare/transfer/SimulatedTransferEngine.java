package com.fileshare.transfer;

import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.TransferDirection;
import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.model.TransferStatus;
import com.fileshare.contracts.port.ApprovalDecision;
import com.fileshare.contracts.port.FileService;
import com.fileshare.contracts.port.IncomingRequestHandler;
import com.fileshare.contracts.port.TaskRunner;
import com.fileshare.contracts.port.TransferEngine;
import com.fileshare.contracts.port.TransferStore;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * Default backend ("simulated"): walks a transfer through CONNECTING -> RUNNING (10 progress steps) -> VERIFYING ->
 * COMPLETED WITHOUT moving any bytes. Lets UI developers build against realistic status/progress events, and
 * everyone run the end-to-end demo, before the real engine exists. Replace with {@link NetworkTransferEngine}.
 */
public class SimulatedTransferEngine implements TransferEngine {

    private static final int STEPS = 10;

    private final TaskRunner executor;
    private final FileService files;
    private final TransferStore store;
    private final long stepMs;
    private final Map<String, Future<?>> running = new ConcurrentHashMap<>();
    private volatile IncomingRequestHandler handler = (file, sender) -> ApprovalDecision.reject("No handler installed");

    public SimulatedTransferEngine(TaskRunner executor, FileService files, TransferStore store, long stepMs) {
        this.executor = executor;
        this.files = files;
        this.store = store;
        this.stepMs = stepMs;
    }

    @Override
    public void start() {
        // nothing to listen on
    }

    @Override
    public void stop() {
        running.values().forEach(f -> f.cancel(true));
        running.clear();
    }

    @Override
    public void setIncomingRequestHandler(IncomingRequestHandler newHandler) {
        this.handler = newHandler;
    }

    @Override
    public TransferSession send(Peer peer, Path file) throws IOException {
        FileMetadata meta = files.describe(file, UUID.randomUUID().toString());
        TransferSession session = new TransferSession(meta, peer, TransferDirection.UPLOAD);
        store.register(session);
        launch(session);
        return session;
    }

    @Override
    public TransferSession injectIncomingRequest(FileMetadata file, Peer from) {
        TransferSession session = new TransferSession(file, from, TransferDirection.DOWNLOAD);
        store.register(session);
        ApprovalDecision decision = handler.onIncomingRequest(file, from.address());
        if (!decision.accepted()) {
            session.reject(decision.reason().isBlank() ? "Rejected by user" : decision.reason());
            store.publish(session);
            return session;
        }
        launch(session);
        return session;
    }

    @Override
    public void cancel(String transferId) {
        store.find(transferId).ifPresent(session -> {
            if (session.status().isTerminal()) {
                return;
            }
            session.setStatus(TransferStatus.CANCELLED);
            Future<?> f = running.remove(transferId);
            if (f != null) {
                f.cancel(true);
            }
            store.publish(session);
        });
    }

    private void launch(TransferSession session) {
        running.put(session.transferId(), executor.submit(() -> simulate(session)));
    }

    private void simulate(TransferSession session) {
        try {
            transition(session, TransferStatus.CONNECTING);
            pause();
            transition(session, TransferStatus.RUNNING);
            session.progress().start();
            long total = session.file().sizeBytes();
            long chunk = total / STEPS;
            for (int i = 1; i <= STEPS; i++) {
                pause();
                if (session.status().isTerminal()) {
                    return;
                }
                session.progress().add(i == STEPS ? total - chunk * (STEPS - 1) : chunk);
                store.publish(session);
            }
            transition(session, TransferStatus.VERIFYING);
            pause();
            transition(session, TransferStatus.COMPLETED);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (!session.status().isTerminal()) {
                transition(session, TransferStatus.CANCELLED);
            }
        } finally {
            running.remove(session.transferId());
        }
    }

    private void transition(TransferSession session, TransferStatus next) {
        if (!session.status().isTerminal()) {
            session.setStatus(next);
            store.publish(session);
        }
    }

    private void pause() throws InterruptedException {
        if (stepMs > 0) {
            Thread.sleep(stepMs);
        }
    }
}
