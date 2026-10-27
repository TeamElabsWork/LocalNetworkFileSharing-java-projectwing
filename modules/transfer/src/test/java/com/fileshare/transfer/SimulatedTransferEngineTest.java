package com.fileshare.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.PeerStatus;
import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.model.TransferStatus;
import com.fileshare.contracts.port.ApprovalDecision;
import com.fileshare.contracts.port.FileService;
import com.fileshare.contracts.port.TaskRunner;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SimulatedTransferEngineTest {

    /** Modules may not depend on each other, so tests bring their own tiny TaskRunner and FileService. */
    private final ExecutorService pool = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    private final TaskRunner runner = new TaskRunner() {
        @Override
        public Future<?> submit(Runnable task) { return pool.submit(task); }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long i, long p) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void shutdown() { pool.shutdownNow(); }
    };

    private final FileService files = new FileService() {
        @Override
        public FileMetadata describe(Path file, String id) throws java.io.IOException {
            return new FileMetadata(id, file.getFileName().toString(), Files.size(file), "0".repeat(64));
        }

        @Override
        public Path partFileFor(Path d, String n) { throw new UnsupportedOperationException(); }

        @Override
        public Path finalizeDownload(Path p, Path d, String n) { throw new UnsupportedOperationException(); }
    };

    @TempDir
    Path dir;

    @AfterEach
    void stop() {
        runner.shutdown();
    }

    private static Peer peer() throws Exception {
        return new Peer("p1", "Laptop-B", InetAddress.getByName("192.168.1.20"), 37021, Instant.now(), PeerStatus.ONLINE);
    }

    private static void awaitTerminal(TransferSession s) throws Exception {
        long end = System.currentTimeMillis() + 5000;
        while (!s.status().isTerminal() && System.currentTimeMillis() < end) {
            Thread.sleep(5);
        }
    }

    @Test
    void sendCompletesAndReportsFullProgress() throws Exception {
        InMemoryTransferStore store = new InMemoryTransferStore();
        List<TransferStatus> seen = new ArrayList<>();
        store.addListener(s -> seen.add(s.status()));
        SimulatedTransferEngine engine = new SimulatedTransferEngine(runner, files, store, 0);
        Path f = dir.resolve("a.bin");
        Files.write(f, new byte[1234]);

        TransferSession session = engine.send(peer(), f);
        awaitTerminal(session);

        assertEquals(TransferStatus.COMPLETED, session.status());
        assertEquals(100.0, session.progress().percentage(), 0.001);
        assertEquals(1234, session.progress().transferredBytes());
        assertTrue(seen.contains(TransferStatus.CONNECTING) && seen.contains(TransferStatus.VERIFYING));
        assertEquals(1, store.all().size());
        assertTrue(store.active().isEmpty());
    }

    @Test
    void emptyFileCompletes() throws Exception {
        SimulatedTransferEngine engine = new SimulatedTransferEngine(runner, files, new InMemoryTransferStore(), 0);
        Path f = dir.resolve("empty");
        Files.write(f, new byte[0]);
        TransferSession session = engine.send(peer(), f);
        awaitTerminal(session);
        assertEquals(TransferStatus.COMPLETED, session.status());
    }

    @Test
    void incomingRequestIsRejectedByDefault() throws Exception {
        SimulatedTransferEngine engine = new SimulatedTransferEngine(runner, files, new InMemoryTransferStore(), 0);
        FileMetadata meta = new FileMetadata("t-9", "x.zip", 10, "a".repeat(64));
        TransferSession session = engine.injectIncomingRequest(meta, peer());
        assertEquals(TransferStatus.REJECTED, session.status());
    }

    @Test
    void acceptedIncomingRequestCompletes() throws Exception {
        SimulatedTransferEngine engine = new SimulatedTransferEngine(runner, files, new InMemoryTransferStore(), 0);
        engine.setIncomingRequestHandler((file, sender) -> ApprovalDecision.accept(dir));
        TransferSession session = engine.injectIncomingRequest(new FileMetadata("t-8", "x.zip", 10, "a".repeat(64)), peer());
        awaitTerminal(session);
        assertEquals(TransferStatus.COMPLETED, session.status());
    }

    @Test
    void cancelStopsARunningTransfer() throws Exception {
        InMemoryTransferStore store = new InMemoryTransferStore();
        SimulatedTransferEngine engine = new SimulatedTransferEngine(runner, files, store, 200);
        Path f = dir.resolve("big");
        Files.write(f, new byte[100]);
        TransferSession session = engine.send(peer(), f);
        Thread.sleep(300);
        engine.cancel(session.transferId());
        awaitTerminal(session);
        assertEquals(TransferStatus.CANCELLED, session.status());
    }
}
