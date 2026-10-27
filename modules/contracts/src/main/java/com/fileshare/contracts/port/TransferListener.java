package com.fileshare.contracts.port;

import com.fileshare.contracts.model.TransferSession;

/** Notified when a transfer is added or its status/progress changed. Called on worker threads. */
@FunctionalInterface
public interface TransferListener {
    void onTransferChanged(TransferSession session);
}
