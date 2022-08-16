package com.ubaid.forj.util;

import java.util.Objects;

public class PhoneNumber implements Cloneable {
    private final String name;
    private final String number;

    public PhoneNumber(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return name.equals(that.name) && number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
            "name='" + name + '\'' +
            ", number='" + number + '\'' +
            '}';
    }

    @Override
    public PhoneNumber clone() throws CloneNotSupportedException {
        return (PhoneNumber) super.clone();
    }
}
