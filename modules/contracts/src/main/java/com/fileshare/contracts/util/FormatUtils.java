package com.fileshare.contracts.util;

import java.util.Locale;

/** Human-readable formatting helpers shared by every UI and by logs. */
public final class FormatUtils {
    private FormatUtils() {}

    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB"};

    /** 1536 -> "1.5 KB" (1024-based units). */
    public static String formatBytes(long bytes) {
        if (bytes < 0) {
            throw new IllegalArgumentException("bytes must be >= 0");
        }
        double value = bytes;
        int unit = 0;
        while (value >= 1024 && unit < UNITS.length - 1) {
            value /= 1024;
            unit++;
        }
        return unit == 0 ? bytes + " B" : String.format(Locale.ROOT, "%.1f %s", value, UNITS[unit]);
    }

    /** 3725 -> "1h 02m 05s", 65 -> "1m 05s", 9 -> "9s". */
    public static String formatDuration(long seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("seconds must be >= 0");
        }
        long h = seconds / 3600;
        long m = (seconds % 3600) / 60;
        long s = seconds % 60;
        if (h > 0) return String.format(Locale.ROOT, "%dh %02dm %02ds", h, m, s);
        if (m > 0) return String.format(Locale.ROOT, "%dm %02ds", m, s);
        return s + "s";
    }

    /** Bytes per second -> "2.3 MB/s". */
    public static String formatSpeed(double bytesPerSecond) {
        return formatBytes((long) Math.max(0, bytesPerSecond)) + "/s";
    }
}
