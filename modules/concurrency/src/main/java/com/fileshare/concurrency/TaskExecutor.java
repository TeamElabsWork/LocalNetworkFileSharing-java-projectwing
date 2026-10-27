package com.fileshare.concurrency;

import com.fileshare.contracts.port.TaskRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Baseline {@link TaskRunner}: a cached pool for long-running tasks (accept loops, transfers) and a scheduled pool
 * for periodic work (broadcasts). The "TaskExecutor: harden thread pools" issue adds a pool limit, uncaught-exception
 * logging and graceful shutdown.
 */
public class TaskExecutor implements TaskRunner {

    private final ExecutorService workers = Executors.newCachedThreadPool(namedDaemon("fileshare-worker"));
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2, namedDaemon("fileshare-sched"));

    @Override
    public Future<?> submit(Runnable task) {
        return workers.submit(task);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelayMs, long periodMs) {
        return scheduler.scheduleAtFixedRate(task, initialDelayMs, periodMs, TimeUnit.MILLISECONDS);
    }

    @Override
    public void shutdown() {
        scheduler.shutdownNow();
        workers.shutdownNow();
    }

    private static ThreadFactory namedDaemon(String prefix) {
        AtomicInteger n = new AtomicInteger();
        return r -> {
            Thread t = new Thread(r, prefix + "-" + n.incrementAndGet());
            t.setDaemon(true);
            return t;
        };
    }
}
