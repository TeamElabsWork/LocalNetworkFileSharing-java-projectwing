package com.fileshare.contracts.port;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/** The ONLY way to run background work. Nobody else creates threads. */
public interface TaskRunner {

    Future<?> submit(Runnable task);

    ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelayMs, long periodMs);

    /** Stops accepting work and interrupts running tasks. */
    void shutdown();
}
