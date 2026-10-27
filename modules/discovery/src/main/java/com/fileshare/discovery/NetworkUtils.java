package com.fileshare.discovery;

import java.net.InetAddress;
import java.util.List;

/**
 * Chooses usable network interfaces/addresses (skip loopback, down and virtual interfaces).
 * STUB - see the NetworkUtils issue.
 */
public final class NetworkUtils {
    private NetworkUtils() {}

    /** Local IPv4 addresses of interfaces that are up and not loopback. */
    public static List<InetAddress> localIpv4Addresses() {
        throw new UnsupportedOperationException("TODO: NetworkUtils.localIpv4Addresses");
    }

    /** Broadcast addresses of all usable interfaces (plus 255.255.255.255 as fallback). */
    public static List<InetAddress> broadcastAddresses() {
        throw new UnsupportedOperationException("TODO: NetworkUtils.broadcastAddresses");
    }
}
