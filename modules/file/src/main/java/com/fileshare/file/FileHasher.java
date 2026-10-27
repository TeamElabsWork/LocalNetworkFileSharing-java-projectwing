package com.fileshare.file;

import java.io.IOException;
import java.nio.file.Path;

/** SHA-256 of a file, computed by streaming (bounded memory), lowercase hex. STUB - see the FileHasher issue. */
public final class FileHasher {
    private FileHasher() {}

    public static String sha256(Path file) throws IOException {
        throw new UnsupportedOperationException("TODO: FileHasher.sha256");
    }
}
