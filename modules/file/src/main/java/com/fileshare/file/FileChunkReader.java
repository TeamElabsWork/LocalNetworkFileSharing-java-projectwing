package com.fileshare.file;

import java.io.IOException;
import java.nio.file.Path;

/** Reads a file in fixed-size chunks (sender side). STUB - see the FileChunkReader/Writer issue. */
public class FileChunkReader implements AutoCloseable {

    public FileChunkReader(Path file, int bufferSize) throws IOException {
        // TODO: open the file (BufferedInputStream or FileChannel)
    }

    /** Fills {@code buffer} and returns the number of bytes read, or -1 at end of file. */
    public int read(byte[] buffer) throws IOException {
        throw new UnsupportedOperationException("TODO: FileChunkReader.read");
    }

    @Override
    public void close() throws IOException {
        // TODO
    }
}
