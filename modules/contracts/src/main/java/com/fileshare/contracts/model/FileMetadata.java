package com.fileshare.contracts.model;

import java.util.Objects;

/**
 * Describes a file about to be transferred. This is what travels inside TRANSFER_REQUEST.
 *
 * @param transferId unique ID of the transfer (UUID string)
 * @param fileName   plain file name only - NEVER a path (the receiver must still sanitize it)
 * @param sizeBytes  file size in bytes (0 is allowed: empty file)
 * @param sha256     lowercase hex SHA-256 of the file, 64 characters
 */
public record FileMetadata(String transferId, String fileName, long sizeBytes, String sha256) {

    public FileMetadata {
        Objects.requireNonNull(transferId, "transferId");
        Objects.requireNonNull(fileName, "fileName");
        Objects.requireNonNull(sha256, "sha256");
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("fileName must not be blank");
        }
        if (sizeBytes < 0) {
            throw new IllegalArgumentException("sizeBytes must be >= 0");
        }
        if (!sha256.matches("[0-9a-f]{64}")) {
            throw new IllegalArgumentException("sha256 must be 64 lowercase hex characters");
        }
    }
}
