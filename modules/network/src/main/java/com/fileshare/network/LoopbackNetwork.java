package com.fileshare.network;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/** The "wire" shared by all {@link LoopbackTransport}s of one simulated network: port -> listening transport. */
public final class LoopbackNetwork {
    private static final LoopbackNetwork SHARED = new LoopbackNetwork();

    private final Map<Integer, LoopbackTransport> listeners = new ConcurrentHashMap<>();
    private final AtomicInteger nextEphemeral = new AtomicInteger(40_000);

    /** Process-wide default network used by the application wiring. Tests should create their own instance. */
    public static LoopbackNetwork shared() {
        return SHARED;
    }

    int bind(int port, LoopbackTransport transport) {
        int actual = port == 0 ? nextEphemeral.getAndIncrement() : port;
        if (listeners.putIfAbsent(actual, transport) != null) {
            throw new IllegalStateException("Port already in use: " + actual);
        }
        return actual;
    }

    void unbind(int port) {
        listeners.remove(port);
    }

    LoopbackTransport lookup(int port) {
        return listeners.get(port);
    }
}
