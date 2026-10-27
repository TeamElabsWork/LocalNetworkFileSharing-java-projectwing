package com.fileshare.contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fileshare.contracts.util.FormatUtils;
import org.junit.jupiter.api.Test;

class FormatUtilsTest {

    @Test
    void formatsBytes() {
        assertEquals("0 B", FormatUtils.formatBytes(0));
        assertEquals("1023 B", FormatUtils.formatBytes(1023));
        assertEquals("1.5 KB", FormatUtils.formatBytes(1536));
        assertEquals("1.0 GB", FormatUtils.formatBytes(1024L * 1024 * 1024));
    }

    @Test
    void formatsDuration() {
        assertEquals("9s", FormatUtils.formatDuration(9));
        assertEquals("1m 05s", FormatUtils.formatDuration(65));
        assertEquals("1h 02m 05s", FormatUtils.formatDuration(3725));
    }

    @Test
    void rejectsNegative() {
        assertThrows(IllegalArgumentException.class, () -> FormatUtils.formatBytes(-1));
    }
}
