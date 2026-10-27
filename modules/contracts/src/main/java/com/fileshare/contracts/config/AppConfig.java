package com.fileshare.contracts.config;

import java.net.InetAddress;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Central, immutable application settings. Defaults follow Appendix A of the project proposal.
 * Anything that is a tunable value must live here - do not hard-code ports or sizes elsewhere.
 *
 * @param deviceId            random ID of this running instance (used to ignore our own broadcasts)
 * @param deviceName          human-readable name shown to other peers
 * @param discoveryPort       UDP port used for discovery
 * @param tcpPort             TCP port the transfer server listens on
 * @param discoveryIntervalMs delay between discovery broadcasts
 * @param peerTimeoutMs       a peer not heard from for this long is marked OFFLINE
 * @param bufferSizeBytes     chunk size used when streaming files
 * @param downloadDir         directory where received files are stored
 * @param maxFileSizeBytes    largest file we agree to receive; 0 means "no limit"
 */
public record AppConfig(
        String deviceId,
        String deviceName,
        int discoveryPort,
        int tcpPort,
        long discoveryIntervalMs,
        long peerTimeoutMs,
        int bufferSizeBytes,
        Path downloadDir,
        long maxFileSizeBytes) {

    public static final int DEFAULT_DISCOVERY_PORT = 37020;
    public static final int DEFAULT_TCP_PORT = 37021;
    public static final int DEFAULT_BUFFER_SIZE = 64 * 1024;

    public static AppConfig defaults() {
        return new AppConfig(
                UUID.randomUUID().toString(),
                localHostName(),
                DEFAULT_DISCOVERY_PORT,
                DEFAULT_TCP_PORT,
                2_500,
                8_000,
                DEFAULT_BUFFER_SIZE,
                Path.of(System.getProperty("user.home"), "FileShare", "Downloads"),
                0);
    }

    private static String localHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "unknown-device";
        }
    }
}
