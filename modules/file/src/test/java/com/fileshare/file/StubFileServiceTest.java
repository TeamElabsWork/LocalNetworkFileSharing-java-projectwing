package com.fileshare.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fileshare.contracts.model.FileMetadata;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class StubFileServiceTest {

    @TempDir
    Path dir;

    @Test
    void describesNameAndSize() throws Exception {
        Path f = dir.resolve("hello.txt");
        Files.writeString(f, "hello");
        FileMetadata meta = new StubFileService().describe(f, "t-1");
        assertEquals("hello.txt", meta.fileName());
        assertEquals(5, meta.sizeBytes());
    }

    @Test
    void missingFileIsRejected() {
        assertThrows(IOException.class, () -> new StubFileService().describe(dir.resolve("nope"), "t-1"));
    }
}
