package dev.ubaid.labs.io.path;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotLinkException;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
public class SymbolicLinksTest {

    @Test
    void createSymbolic() throws IOException {
        Path rootPath = Path.of("src", "test", "resources", "paths");
        Path file1 = rootPath.resolve("file1");

        Path newLink = rootPath.resolve("links").resolve(UUID.randomUUID().toString());

        try {
            Path path = Files.createSymbolicLink(newLink, file1);
            Assertions.assertTrue(Files.isSymbolicLink(path));
            
            Path target = Files.readSymbolicLink(path);
            
            Assertions.assertThrowsExactly(NotLinkException.class, () -> Files.readSymbolicLink(target));
            
            String output = String.join(" ", Files.readAllLines(target));
            
            Assertions.assertEquals("no ext", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Files.delete(newLink);
        }
    }

    @Test
    void createHardLink() throws IOException {
        Path rootPath = Path.of("src", "test", "resources", "paths");
        Path file1 = rootPath.resolve("file1");

        Path newLink = rootPath.resolve("links").resolve(UUID.randomUUID().toString());
        try {
            Path hardLink = Files.createLink(newLink, file1);
            String output = String.join(",", Files.readAllLines(hardLink));
            Assertions.assertEquals("no ext", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Files.delete(newLink);
        }
    }
}
