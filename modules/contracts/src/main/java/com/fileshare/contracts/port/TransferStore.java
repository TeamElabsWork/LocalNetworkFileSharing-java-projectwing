package com.fileshare.contracts.port;

import com.fileshare.contracts.model.TransferSession;

import java.util.List;
import java.util.Optional;

/**
 * THE single registry of all transfers (active and finished). Engines register sessions and call
 * {@link #publish} after every change; UIs read and listen.
 */
public interface TransferStore {

    void register(TransferSession session);

    Optional<TransferSession> find(String transferId);

    List<TransferSession> all();

    /** Sessions whose status is not terminal. */
    List<TransferSession> active();

    /** Tells listeners that {@code session} changed. */
    void publish(TransferSession session);

    void addListener(TransferListener listener);

    void removeListener(TransferListener listener);
}
