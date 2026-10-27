package com.fileshare.file;

import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.port.FileService;

import java.io.IOException;
import java.nio.file.Path;

/**
 * REAL backend ("local") - STUB: real hashing, safe destination paths, .part files, duplicate-name handling and an
 * atomic move after verification. Implemented in the "FileService" issue; select with {@code backend.files=local}.
 */
public class LocalFileService implements FileService {

    private final AppConfig config;

    public LocalFileService(AppConfig config) {
        this.config = config;
    }

    @Override
    public FileMetadata describe(Path file, String transferId) throws IOException {
        throw new UnsupportedOperationException("TODO: LocalFileService.describe");
    }

    @Override
    public Path partFileFor(Path targetDir, String untrustedFileName) throws IOException {
        throw new UnsupportedOperationException("TODO: LocalFileService.partFileFor");
    }

    @Override
    public Path finalizeDownload(Path partFile, Path targetDir, String untrustedFileName) throws IOException {
        throw new UnsupportedOperationException("TODO: LocalFileService.finalizeDownload");
    }
}
