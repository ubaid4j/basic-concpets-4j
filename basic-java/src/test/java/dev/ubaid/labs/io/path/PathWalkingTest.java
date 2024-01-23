package dev.ubaid.labs.io.path;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

import static java.nio.file.FileVisitResult.CONTINUE;

public class PathWalkingTest {
    
    @Test
    void printEachFileAndRicInGivenPath() throws Exception {
        Path path = Path.of("src", "test", "resources", "paths");
        Files.walkFileTree(path, new PrintFiles());
    }
    
    @Test
    void findFile() throws Exception {
        Path path = Path.of("src", "test", "resources", "paths");

        Finder finder = new Finder("*.{java,jjxx}");
        Files.walkFileTree(path, finder);
        finder.done();

        Assertions.assertEquals(2, finder.numMatches);
        
        Finder txtFileFiner = new Finder("*.{txt}");
        Files.walkFileTree(path, txtFileFiner);
        txtFileFiner.done();
        Assertions.assertEquals(3, txtFileFiner.numMatches);
    }
    
    

    @Slf4j
    private static class PrintFiles extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            log.info("preVisitDirectory: {}", dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            log.info("visitFile: {}", file);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            log.error("visitFileFailed: {}", file, exc);
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            log.info("postVisitDirectory: {}", dir);
            if (Objects.nonNull(exc)) {
                log.error("postVisitDirectory: ", exc);
            }
            return CONTINUE;
        }
    }
    
    @Slf4j
    private static class Finder extends SimpleFileVisitor<Path> {
        
        private int numMatches;
        private final PathMatcher pathMatcher;
        
        public Finder(String pattern) {
            pathMatcher = FileSystems.getDefault().getPathMatcher(STR."glob:\{pattern}");
        }

        void find(Path file) {
            Path name = file.getFileName();
            if (Objects.nonNull(name) && pathMatcher.matches(name)) {
                numMatches++;
                log.info("{}", file);
            }
        }
        
        void done() {
            log.info("Total Matched: {}", numMatches);
        }


        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            find(file);
            return CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            find(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            log.error("error for {} ",file, exc);
            return CONTINUE;
        }
    }
}


