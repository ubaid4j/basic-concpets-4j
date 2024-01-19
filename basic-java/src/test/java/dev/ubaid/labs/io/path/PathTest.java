package dev.ubaid.labs.io.path;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class PathTest {
    
    @Test
    void test() {
        Path path = Paths.get("/", "tmp");
        
        log.info("{}", path);

        
        Path path1 = Path.of("/", "tmp");

        Assertions.assertEquals(path, path1);
        
        log.info("file name: {}", path.getFileName());
        log.info("path count: {}", path.getNameCount());
        log.info("parent: {}", path.getParent());
        log.info("root: {}", path.getRoot());
    }
    
    
    @Test
    void absolutePath() throws IOException {
        Path rootPath = Paths.get("src", "test", "resources", "paths");
        log.info("path: {}", rootPath);
        Path path = rootPath.resolve("file1");
        Assertions.assertTrue(Files.isRegularFile(path));
        
        path = rootPath.resolve("subPath");
        Assertions.assertTrue(Files.isDirectory(path));
        
        Path textFile = rootPath.resolve("subPath").resolve("file.txt");
        textFile = textFile.toRealPath();
        Assertions.assertTrue(Files.isRegularFile(textFile));
    }
    
    @Test
    void relativePaths() {
        Path rootPath = Path.of("src", "test", "resources", "paths");
        Path sib1 = rootPath.resolve("sib1");
        Path sib2 = rootPath.resolve("sib2");
        
        Path sib2FromSib1 = sib1.relativize(sib2);
        Assertions.assertEquals(Path.of("..", "sib2"), sib2FromSib1);
        
        Path sib1Sub = rootPath.resolve("sib1").resolve("sib1Sub");
        
        Path fromSib1SubToRoot = sib1Sub.relativize(rootPath);
        Assertions.assertEquals(Path.of("..", "/", ".."), fromSib1SubToRoot);
    }
    
    @Test
    void compareThePath() {
        Path rootPath = Path.of("src", "test", "resources", "paths");
        Path sib2 = rootPath.resolve("sib2");
        Path sib2e = Path.of("src", "test", "resources", "paths", "sib2");
        
        Assertions.assertEquals(sib2, sib2e);
        
        log.info("Path: {}", sib2);
        for (Path subPath : sib2) {
            log.info("parts: {}", subPath);
        }
        
        Assertions.assertTrue(sib2.startsWith(Path.of("src")));
        Assertions.assertTrue(sib2.endsWith(Path.of("sib2")));
    }
    
    @Test
    void fileExistence() {
        Path rootPath = Path.of("src", "test", "resources", "paths");
        Path file1 = rootPath.resolve("file1");
        
        Assertions.assertTrue(Files.exists(file1));
        Assertions.assertTrue(Files.isRegularFile(file1));
        Assertions.assertTrue(Files.isReadable(file1));
        Assertions.assertFalse(Files.isExecutable(file1));
    }
    
    @Test
    void isSameFile() throws IOException {
        Path rootPath = Path.of("src", "test", "resources", "paths");
        Path file1 = rootPath.resolve("file1");
        Path symlink = rootPath
                .resolve("sib1")
                .resolve("sib1Sub")
                .resolve("file1");
        Assertions.assertTrue(Files.isSameFile(file1, symlink));
    }
    
    
}
