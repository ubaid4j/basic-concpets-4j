package dev.ubaid.labs.serialization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

public class SerializationTest {
    
    @Test
    void serializable() throws Exception {
        
        record Person(String name, Integer age) implements Serializable {
            @Serial
            private static final long serialVersionUID = 1L;
        }
        
        Person person = new Person("ubaid", 27);

        File file = new File("ubaid.ser");
        
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(person);

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Person deserilizedPerson = (Person) objectInputStream.readObject();

        Assertions.assertEquals(person, deserilizedPerson);
        
    }
    
}
