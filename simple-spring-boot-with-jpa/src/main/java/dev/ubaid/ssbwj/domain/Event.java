package dev.ubaid.ssbwj.domain;

import dev.ubaid.ssbwj.domain.customtypes.IPv4Type;
import dev.ubaid.ssbwj.domain.customtypes.Inet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.Type;

@Entity
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @Column(name = "ip", columnDefinition = "inet")
    @Type(IPv4Type.class)
    private Inet ip;
    
    public Event() {
        
    }
    
    public Event(String address) {
        this.ip = new Inet(address);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inet getIp() {
        return ip;
    }

    public void setIp(Inet ip) {
        this.ip = ip;
    }
}
