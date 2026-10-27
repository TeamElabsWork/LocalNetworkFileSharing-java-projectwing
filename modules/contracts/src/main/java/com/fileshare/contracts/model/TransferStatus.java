package com.fileshare.contracts.model;

/**
 * Lifecycle of a transfer (merges the status lists of the implementation plan and the proposal).
 *
 * <pre>
 * PENDING -> CONNECTING -> RUNNING -> VERIFYING -> COMPLETED
 *                \            \           \-> FAILED (integrity mismatch)
 *                 \            \-> FAILED / CANCELLED
 *                  \-> FAILED / REJECTED
 * </pre>
 */
public enum TransferStatus {
    /** Created, not started (or waiting for the receiver's decision). */
    PENDING,
    /** Opening the connection / negotiating metadata. */
    CONNECTING,
    /** File bytes are flowing. */
    RUNNING,
    /** All bytes received; computing and comparing SHA-256. */
    VERIFYING,
    COMPLETED,
    FAILED,
    CANCELLED,
    /** Receiver declined the request. */
    REJECTED;

    public boolean isTerminal() {
        return this == COMPLETED || this == FAILED || this == CANCELLED || this == REJECTED;
    }
}
