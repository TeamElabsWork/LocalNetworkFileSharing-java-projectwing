package com.fileshare.file;

import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.port.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Default backend ("stub"): just enough for the simulated engine - reads name and size, uses an all-zero placeholder
 * hash. It does NOT hash, sanitize or move files. Replace with {@link LocalFileService}.
 */
public class StubFileService implements FileService {

    static final String PLACEHOLDER_HASH = "0".repeat(64);

    @Override
    public FileMetadata describe(Path file, String transferId) throws IOException {
        if (!Files.isRegularFile(file) || !Files.isReadable(file)) {
            throw new IOException("Not a readable file: " + file);
        }
        return new FileMetadata(transferId, file.getFileName().toString(), Files.size(file), PLACEHOLDER_HASH);
    }

    @Override
    public Path partFileFor(Path targetDir, String untrustedFileName) {
        throw new UnsupportedOperationException("StubFileService does not write files");
    }

    @Override
    public Path finalizeDownload(Path partFile, Path targetDir, String untrustedFileName) {
        throw new UnsupportedOperationException("StubFileService does not write files");
    }
}
