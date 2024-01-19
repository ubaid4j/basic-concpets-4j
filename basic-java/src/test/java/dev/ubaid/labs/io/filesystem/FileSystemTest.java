package dev.ubaid.labs.io.filesystem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

@Slf4j
public class FileSystemTest {
    
    @Test
    void pathSeparator() {
        String separator = File.separator;
        Assertions.assertEquals("/", separator);
        
        String separator2 = FileSystems.getDefault().getSeparator();
        Assertions.assertEquals("/", separator2);
    }
    
    @Test
    void fileStore() {
        FileSystem fileSystem = FileSystems.getDefault();
        for (FileStore fileStore : fileSystem.getFileStores()) {
            log.info("{}", fileStore);
        }
    }
}
