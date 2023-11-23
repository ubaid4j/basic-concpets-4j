package dev.ubaid.labs.serialization;

import dev.ubaid.labs.serialization.ser.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;

@Slf4j
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
    
    @Test
    void serializeANonSerializeFieldInSerializableClass() throws Exception {
        
        File file = Files.createTempFile("person", ".ser").toFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        
        Address address = new Address(new Street("Atk"));
        Person person = new Person(address);
        
        Assertions.assertThrows(NotSerializableException.class, () -> {
            try {
                objectOutputStream.writeObject(person);
            } catch (Exception exp) {
                log.error("exp:", exp);
                throw exp;
            }
        });
    }
    
    //it will ignore the fields of non serializable super class fields
    @Test
    void serializeASerializableSubClassThatIsExtendingNonSerializableClass() throws Exception {
        File file = Files.createTempFile("person", ".ser").toFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        FullAddress fullAddress = new FullAddress("Atk");
        
        objectOutputStream.writeObject(fullAddress);

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        FullAddress deserializedFullAddress = (FullAddress) objectInputStream.readObject();
        
        Assertions.assertNull(deserializedFullAddress.getName());

    }
    
}
