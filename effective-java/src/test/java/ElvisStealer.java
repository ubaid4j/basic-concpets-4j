import java.io.Serial;
import java.io.Serializable;

public class ElvisStealer implements Serializable {
    static Elvis impersonator;
    private Elvis payload;
    
    @Serial
    private Object readResolve() {
        impersonator = payload;
        
        return new String[] {"A Fool such as I"};
    }
    
    @Serial
    private static final long serialVersionUID = 0;
}
