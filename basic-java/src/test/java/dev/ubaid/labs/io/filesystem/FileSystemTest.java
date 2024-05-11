package dev.ubaid.labs.io.filesystem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.nio.file.attribute.PosixFilePermission.GROUP_READ;
import static java.nio.file.attribute.PosixFilePermission.GROUP_WRITE;
import static java.nio.file.attribute.PosixFilePermission.OTHERS_READ;
import static java.nio.file.attribute.PosixFilePermission.OWNER_READ;
import static java.nio.file.attribute.PosixFilePermission.OWNER_WRITE;

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

    @Test
    void fileAttributes() throws IOException {
        Path file1 = Path.of("src", "test", "resources", "paths", "file1");
        Assertions.assertEquals(6, Files.size(file1));
        Assertions.assertFalse(Files.isDirectory(file1));
        Assertions.assertTrue(Files.isRegularFile(file1));
        Assertions.assertFalse(Files.isSymbolicLink(file1));
        Assertions.assertFalse(Files.isHidden(file1));
        Assertions.assertTrue(Files.getLastModifiedTime(file1).toInstant().isBefore(Instant.now()));
        Assertions.assertTrue(List.of("ubaid", "runner").contains(Files.getOwner(file1).getName()));
        
        //TODO fix this        
//        Assertions.assertTrue(Files.getPosixFilePermissions(file1).containsAll(Set.of(OTHERS_READ, OWNER_READ, OWNER_WRITE, GROUP_WRITE, GROUP_READ)));

        BasicFileAttributes attributes = Files.readAttributes(file1, BasicFileAttributes.class);
        Assertions.assertTrue(attributes.isRegularFile());

        DosFileAttributes dosFileAttributes = Files.readAttributes(file1, DosFileAttributes.class);
        Assertions.assertFalse(dosFileAttributes.isHidden());
    }

    @Test
    void posixFilePermissions() throws IOException {
        Path file1 = Path.of("src", "test", "resources", "paths", "file1");
        PosixFileAttributes attr = Files.readAttributes(file1, PosixFileAttributes.class);
        log.info("{} {} {}", attr.owner().getName(), attr.group().getName(), PosixFilePermissions.toString(attr.permissions()));

        FileAttribute<Set<PosixFilePermission>> newAttr = PosixFilePermissions.asFileAttribute(attr.permissions());

        Path newFile = file1.getParent().resolve(UUID.randomUUID().toString());

        try {
            Files.createFile(newFile, newAttr);
            Assertions.assertEquals(attr.permissions(), Files.readAttributes(newFile, PosixFileAttributes.class).permissions());
        } finally {
            Files.delete(newFile);
        }
    }

    @Test
    void fileType() throws IOException {
        Path file1 = Path.of("src", "test", "resources", "paths", "file1.txt");
        String type = Files.probeContentType(file1);
        Assertions.assertEquals("text/plain", type);
    }
}
