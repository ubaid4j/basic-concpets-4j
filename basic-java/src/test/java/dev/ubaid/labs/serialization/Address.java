package dev.ubaid.labs.serialization;

import java.io.Serializable;

public class Address {
    private final Street street;

    public Address(Street street) {
        this.street = street;
    }

    public Street getStreet() {
        return street;
    }
}
