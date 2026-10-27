package com.fileshare.network;

import com.fileshare.contracts.port.TaskRunner;
import com.fileshare.contracts.port.TransferTransport;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import org.junit.jupiter.api.AfterEach;

class LoopbackTransportTest extends AbstractTransportTest {

    /** Minimal TaskRunner for tests - modules may not depend on the concurrency module. */
    private static final class TestRunner implements TaskRunner {
        private final ExecutorService pool = Executors.newCachedThreadPool(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        @Override
        public Future<?> submit(Runnable task) { return pool.submit(task); }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelayMs, long periodMs) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void shutdown() { pool.shutdownNow(); }
    }

    private final LoopbackNetwork network = new LoopbackNetwork();
    private final TestRunner executor = new TestRunner();

    @AfterEach
    void stopRunner() {
        executor.shutdown();
    }

    @Override
    protected TransferTransport newServerTransport() {
        return new LoopbackTransport(network, executor, 0);
    }

    @Override
    protected TransferTransport newClientTransport() {
        return new LoopbackTransport(network, executor, 0);
    }
}
