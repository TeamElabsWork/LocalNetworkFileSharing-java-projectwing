package com.fileshare.transfer;

import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.port.TransferListener;
import com.fileshare.contracts.port.TransferStore;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * In-memory {@link TransferStore}. Baseline: storage + change notification. The "TransferStore: state transitions,
 * throttled events, cleanup" issue adds validated transitions, event throttling and pruning of old sessions.
 */
public class InMemoryTransferStore implements TransferStore {

    private final List<TransferSession> sessions = new CopyOnWriteArrayList<>();
    private final List<TransferListener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void register(TransferSession session) {
        sessions.add(session);
        publish(session);
    }

    @Override
    public Optional<TransferSession> find(String transferId) {
        return sessions.stream().filter(s -> s.transferId().equals(transferId)).findFirst();
    }

    @Override
    public List<TransferSession> all() {
        return List.copyOf(sessions);
    }

    @Override
    public List<TransferSession> active() {
        return sessions.stream().filter(s -> !s.status().isTerminal()).toList();
    }

    @Override
    public void publish(TransferSession session) {
        for (TransferListener l : listeners) {
            try {
                l.onTransferChanged(session);
            } catch (RuntimeException e) {
                // a broken listener must never break a transfer
            }
        }
    }

    @Override
    public void addListener(TransferListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(TransferListener listener) {
        listeners.remove(listener);
    }
}
