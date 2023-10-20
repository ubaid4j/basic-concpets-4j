package dev.ubaid.labs.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileReader;

@Slf4j
public class DifferenceBetweenFileInputStreamAndFileReaderTest {
    
    
    private static final String BINARY_FILE_PATH = "src/test/resources/binarydata.bin";
    private static final String TEXT_FILE_PATH = "src/test/resources/textfile.txt";
    
    // File Input Stream
    @Test
    void fileInputStreamCanReadBinaryAndTextData() throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(BINARY_FILE_PATH)) {
            byte[] bytes = fileInputStream.readAllBytes();
            log.debug("file: {}", BINARY_FILE_PATH);
            log.debug("Bytes: {}", bytes);
            log.debug("Text: {}", new String(bytes));
        }

        try (FileInputStream fileInputStream = new FileInputStream(TEXT_FILE_PATH)) {
            byte[] bytes = fileInputStream.readAllBytes();
            log.debug("file: {}", TEXT_FILE_PATH);
            log.debug("Bytes: {}", bytes);
            log.debug("Text: {}", new String(bytes));
            
            String text = new String(bytes);
            Assertions.assertEquals("Hello World", text, "Contents of text file should equal to Hello World");
        }
    }

    @Test
    void fileReaderIsUsedToReadCharsFromTextFiles() throws Exception {
        try (FileReader fileReader = new FileReader(TEXT_FILE_PATH)) {
            StringBuilder builder = new StringBuilder();
            
            int character = fileReader.read();
            while (character != -1) {
                builder.append((char) character);
                character = fileReader.read();
            }
            
            Assertions.assertEquals(builder.toString(), "Hello World", "should equal to Hello world");
            
        }
    }
    
}
