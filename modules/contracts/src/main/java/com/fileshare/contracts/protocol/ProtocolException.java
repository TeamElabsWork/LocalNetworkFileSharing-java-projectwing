package com.fileshare.contracts.protocol;

/** Thrown when a received frame is malformed, oversized or semantically invalid. */
public class ProtocolException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProtocolException(String message) { super(message); }

    public ProtocolException(String message, Throwable cause) { super(message, cause); }
}
