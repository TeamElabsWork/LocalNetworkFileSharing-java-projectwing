package com.fileshare.file;

/**
 * Input validation for anything that came from the network. SECURITY-CRITICAL: a file name received from a peer must
 * never be used as a path directly (path traversal). STUB - see the ValidationUtils issue.
 */
public final class ValidationUtils {
    private ValidationUtils() {}

    /**
     * Returns a safe plain file name derived from {@code untrusted} (no directories, no "..", no reserved or control
     * characters, not empty, length-limited), or throws IllegalArgumentException if impossible.
     */
    public static String sanitizeFileName(String untrusted) {
        throw new UnsupportedOperationException("TODO: ValidationUtils.sanitizeFileName");
    }
}
