package com.fileshare.contracts.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Everything the app knows about one transfer. Created by a TransferEngine, stored in the TransferStore,
 * observed by the UIs. Status is volatile because workers write it and the UI reads it.
 */
public final class TransferSession {

    private final FileMetadata file;
    private final Peer peer;
    private final TransferDirection direction;
    private final TransferProgress progress;
    private volatile TransferStatus status = TransferStatus.PENDING;
    private volatile String message;

    public TransferSession(FileMetadata file, Peer peer, TransferDirection direction) {
        this.file = Objects.requireNonNull(file, "file");
        this.peer = peer; // may be null on the receiving side until the peer is resolved
        this.direction = Objects.requireNonNull(direction, "direction");
        this.progress = new TransferProgress(file.sizeBytes());
    }

    public String transferId() { return file.transferId(); }
    public FileMetadata file() { return file; }
    public Optional<Peer> peer() { return Optional.ofNullable(peer); }
    public TransferDirection direction() { return direction; }
    public TransferProgress progress() { return progress; }
    public TransferStatus status() { return status; }
    /** Failure / rejection reason, if any. */
    public Optional<String> message() { return Optional.ofNullable(message); }

    public void setStatus(TransferStatus newStatus) { this.status = Objects.requireNonNull(newStatus); }

    public void fail(String reason) {
        this.message = reason;
        this.status = TransferStatus.FAILED;
    }

    public void reject(String reason) {
        this.message = reason;
        this.status = TransferStatus.REJECTED;
    }
}
