package com.fileshare.file;

import java.io.IOException;
import java.nio.file.Path;

/** Writes received chunks into a .part file (receiver side). STUB - see the FileChunkReader/Writer issue. */
public class FileChunkWriter implements AutoCloseable {

    public FileChunkWriter(Path partFile) throws IOException {
        // TODO: open for writing; never silently overwrite
    }

    public void write(byte[] buffer, int offset, int length) throws IOException {
        throw new UnsupportedOperationException("TODO: FileChunkWriter.write");
    }

    @Override
    public void close() throws IOException {
        // TODO: flush + close; idempotent
    }
}
