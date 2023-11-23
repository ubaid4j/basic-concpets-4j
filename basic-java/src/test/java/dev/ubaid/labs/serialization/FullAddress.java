package dev.ubaid.labs.serialization;

import java.io.Serial;
import java.io.Serializable;

public class FullAddress extends Street implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public FullAddress(String street) {
        super(street);
    }

    public FullAddress() {
        super();
    }
}
