package com.fileshare.contracts.protocol;

/**
 * All control messages of the application protocol (see docs/protocol.md).
 * File bytes (DATA) are NOT a framed message: they are a raw stream that follows TRANSFER_ACCEPT.
 */
public enum MessageType {
    DISCOVER,            // UDP broadcast: "I am here"
    DISCOVER_RESPONSE,   // UDP unicast reply
    TRANSFER_REQUEST,    // TCP: sender -> receiver, carries FileMetadata
    TRANSFER_ACCEPT,     // TCP: receiver -> sender
    TRANSFER_REJECT,     // TCP: receiver -> sender
    TRANSFER_COMPLETE,   // TCP: sender -> receiver, all bytes sent
    HASH_RESULT,         // TCP: receiver -> sender, verification outcome
    CANCEL,              // TCP: either side
    RESUME_REQUEST       // TCP: stretch goal
}
