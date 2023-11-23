package dev.ubaid.labs.serialization;

import java.io.Serializable;

public class Street {
    private final String name;

    public Street(String name) {
        this.name = name;
    }

    public Street() {
        this.name = null;
    }

    public String getName() {
        return name;
    }
}
