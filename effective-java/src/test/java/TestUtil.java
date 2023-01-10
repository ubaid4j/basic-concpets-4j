import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestUtil {

    static Object deserialize(byte[] sf) {
        try {
            return
                new ObjectInputStream(new ByteArrayInputStream(sf))
                    .readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
