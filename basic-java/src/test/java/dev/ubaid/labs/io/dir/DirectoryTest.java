package dev.ubaid.labs.io.dir;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class DirectoryTest {
    
    DirectoryStream.Filter<Path> dirFilter = Files::isDirectory;
    
    @Test
    void dirStream() throws IOException {
        Path dir1 = Path.of("src", "test", "resources", "paths", "dir1");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir1, "*.txt")) {
            for (Path path : stream) {
                Assertions.assertTrue(path.endsWith("p1.txt"));
            }
        }
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir1, dirFilter)) {
            for (Path path : stream) {
                Assertions.assertTrue(path.endsWith("subdir1"));
            }
        }
    }
}
