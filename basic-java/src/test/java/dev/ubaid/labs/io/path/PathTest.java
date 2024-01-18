package dev.ubaid.labs.io.path;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    
}
