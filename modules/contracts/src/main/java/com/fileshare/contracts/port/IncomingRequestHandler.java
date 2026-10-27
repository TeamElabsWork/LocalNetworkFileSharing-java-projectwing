package com.fileshare.contracts.port;

import com.fileshare.contracts.model.FileMetadata;

import java.net.InetAddress;

/**
 * Asked whenever a peer wants to send us a file. Called on a WORKER thread and allowed to block while the user
 * decides. The CLI answers with a prompt; the JavaFX UI shows a dialog (Platform.runLater + CompletableFuture).
 */
@FunctionalInterface
public interface IncomingRequestHandler {
    ApprovalDecision onIncomingRequest(FileMetadata file, InetAddress sender);
}
