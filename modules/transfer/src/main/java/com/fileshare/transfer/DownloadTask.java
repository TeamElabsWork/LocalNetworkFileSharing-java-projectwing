package com.fileshare.transfer;

import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.port.FileService;
import com.fileshare.contracts.port.PeerConnection;

import java.nio.file.Path;

/**
 * Receiver side after accepting: reads exactly {@code size} bytes into a .part file, verifies SHA-256, then finalizes
 * the file. STUB - see the DownloadTask issue.
 */
public class DownloadTask implements Runnable {

    private final TransferSession session;
    private final PeerConnection connection;
    private final FileService files;
    private final Path destinationDir;
    private final int bufferSize;

    public DownloadTask(TransferSession session, PeerConnection connection, FileService files, Path destinationDir,
                        int bufferSize) {
        this.session = session;
        this.connection = connection;
        this.files = files;
        this.destinationDir = destinationDir;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("TODO: DownloadTask");
    }
}
