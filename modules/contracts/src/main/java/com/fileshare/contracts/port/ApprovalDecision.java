package com.fileshare.contracts.port;

import java.nio.file.Path;

/** Answer to an incoming transfer request. {@code destinationDir} is ignored when not accepted. */
public record ApprovalDecision(boolean accepted, Path destinationDir, String reason) {
    public static ApprovalDecision accept(Path dir) { return new ApprovalDecision(true, dir, ""); }

    public static ApprovalDecision reject(String reason) { return new ApprovalDecision(false, null, reason); }
}
