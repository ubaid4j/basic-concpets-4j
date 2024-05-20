package dev.ubaid.ssbwj.domain.customtypes;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class Inet implements Serializable {
    private final String address;

    public Inet(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inet inet = (Inet) o;
        return Objects.equals(address, inet.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }
    
    public InetAddress toInetAddress() throws UnknownHostException {
        return Inet4Address.getByName(address);
    }
}
