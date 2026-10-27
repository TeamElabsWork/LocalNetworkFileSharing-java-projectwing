package com.fileshare.contracts.protocol;

import java.util.Map;
import java.util.Objects;

/**
 * One control message. {@code fields} holds simple string key/values (fileName, size, sha256, reason ...).
 * Never send Java-serialized objects over the network - always go through the codec in the network module.
 *
 * @param type       message type
 * @param transferId transfer this message belongs to (empty string for discovery messages)
 * @param fields     message-specific data, immutable
 */
public record ProtocolMessage(MessageType type, String transferId, Map<String, String> fields) {

    public ProtocolMessage {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(transferId, "transferId");
        fields = Map.copyOf(fields == null ? Map.of() : fields);
    }

    public static ProtocolMessage of(MessageType type, String transferId) {
        return new ProtocolMessage(type, transferId, Map.of());
    }
}
