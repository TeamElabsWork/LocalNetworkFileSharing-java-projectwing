package com.fileshare.contracts.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Per-transfer progress counters. Thread-safe: updated by the transfer worker, read by the UI.
 * There must be exactly ONE implementation of this class - extend it, never duplicate it.
 *
 * <p>Baseline: percentage + simple average speed. The TransferProgress issue adds a moving-average speed and ETA.
 */
public class TransferProgress {

    private final long totalBytes;
    private final AtomicLong transferredBytes = new AtomicLong();
    private volatile long startNanos = -1;

    public TransferProgress(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    /** Call once when bytes start flowing. */
    public void start() {
        startNanos = System.nanoTime();
    }

    public void add(long bytes) {
        transferredBytes.addAndGet(bytes);
    }

    public long totalBytes() { return totalBytes; }

    public long transferredBytes() { return transferredBytes.get(); }

    /** 0.0 - 100.0; an empty file counts as 100 once started. */
    public double percentage() {
        if (totalBytes == 0) {
            return startNanos < 0 ? 0.0 : 100.0;
        }
        return Math.min(100.0, transferredBytes.get() * 100.0 / totalBytes);
    }

    /** Average speed since start() in bytes per second (0 if not started). */
    public double bytesPerSecond() {
        if (startNanos < 0) {
            return 0.0;
        }
        double seconds = (System.nanoTime() - startNanos) / 1_000_000_000.0;
        return seconds <= 0 ? 0.0 : transferredBytes.get() / seconds;
    }

    // TODO (issue "TransferProgress: moving-average speed and ETA"): long etaSeconds()
}
