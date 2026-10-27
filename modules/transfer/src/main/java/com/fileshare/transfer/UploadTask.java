package com.fileshare.transfer;

import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.port.PeerConnection;

/**
 * Sender side after TRANSFER_ACCEPT: reads the file in chunks, writes them to the connection, updates
 * {@code TransferProgress}, sends TRANSFER_COMPLETE and waits for HASH_RESULT. STUB - see the UploadTask issue.
 */
public class UploadTask implements Runnable {

    private final TransferSession session;
    private final PeerConnection connection;
    private final int bufferSize;

    public UploadTask(TransferSession session, PeerConnection connection, int bufferSize) {
        this.session = session;
        this.connection = connection;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("TODO: UploadTask");
    }
}
