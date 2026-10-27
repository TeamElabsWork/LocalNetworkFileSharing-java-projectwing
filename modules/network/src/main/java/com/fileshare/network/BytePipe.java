package com.fileshare.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * One-directional in-memory byte pipe (writer thread -> reader thread). Unlike {@code PipedInputStream} it never
 * complains about dead threads. Unbounded (no back-pressure) - fine for tests and the loopback transport.
 */
final class BytePipe {
    private static final byte[] EOF = new byte[0];

    private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
    private final AtomicBoolean writerClosed = new AtomicBoolean();

    final InputStream input = new InputStream() {
        private byte[] current = new byte[0];
        private int pos;
        private boolean eof;

        @Override
        public int read() throws IOException {
            byte[] one = new byte[1];
            int n = read(one, 0, 1);
            return n < 0 ? -1 : one[0] & 0xff;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (len == 0) return 0;
            if (eof) return -1;
            while (pos >= current.length) {
                try {
                    current = queue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("interrupted while reading", e);
                }
                pos = 0;
                if (current == EOF) {
                    eof = true;
                    return -1;
                }
            }
            int n = Math.min(len, current.length - pos);
            System.arraycopy(current, pos, b, off, n);
            pos += n;
            return n;
        }
    };

    final OutputStream output = new OutputStream() {
        @Override
        public void write(int b) throws IOException {
            write(new byte[] {(byte) b}, 0, 1);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            if (writerClosed.get()) throw new IOException("pipe closed");
            if (len > 0) queue.add(java.util.Arrays.copyOfRange(b, off, off + len));
        }

        @Override
        public void close() {
            closeWriter();
        }
    };

    /** Signals end-of-stream to the reader. Idempotent. */
    void closeWriter() {
        if (writerClosed.compareAndSet(false, true)) {
            queue.add(EOF);
        }
    }
}
