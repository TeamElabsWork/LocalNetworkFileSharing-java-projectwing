package com.fileshare.contracts.port;

import com.fileshare.contracts.model.FileMetadata;

import java.io.IOException;
import java.nio.file.Path;

/** File-system operations needed by the transfer engine. Implementations: stub, local. */
public interface FileService {

    /** Builds FileMetadata (name, size, SHA-256) for a local file about to be sent. */
    FileMetadata describe(Path file, String transferId) throws IOException;

    /** Safe path of the temporary .part file for an incoming transfer, inside {@code targetDir}. */
    Path partFileFor(Path targetDir, String untrustedFileName) throws IOException;

    /** Moves a verified .part file to its final name without overwriting existing files; returns the final path. */
    Path finalizeDownload(Path partFile, Path targetDir, String untrustedFileName) throws IOException;
}
