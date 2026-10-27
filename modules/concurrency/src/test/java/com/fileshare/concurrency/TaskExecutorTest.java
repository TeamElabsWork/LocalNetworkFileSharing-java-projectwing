package com.fileshare.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

class TaskExecutorTest {

    @Test
    void runsTasksOnNamedDaemonThreads() throws Exception {
        TaskExecutor executor = new TaskExecutor();
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<Thread> thread = new AtomicReference<>();
        executor.submit(() -> {
            thread.set(Thread.currentThread());
            done.countDown();
        });
        assertTrue(done.await(5, TimeUnit.SECONDS));
        assertTrue(thread.get().getName().startsWith("fileshare-worker"));
        assertTrue(thread.get().isDaemon());
        executor.shutdown();
    }

    @Test
    void scheduledTaskRepeats() throws Exception {
        TaskExecutor executor = new TaskExecutor();
        CountDownLatch ticks = new CountDownLatch(3);
        executor.scheduleAtFixedRate(ticks::countDown, 0, 10);
        assertTrue(ticks.await(5, TimeUnit.SECONDS));
        executor.shutdown();
        assertEquals(0, ticks.getCount());
    }
}
