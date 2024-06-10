package dev.ubaid.ssbwji.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table
public class Announcement extends Topic {
    
    @NotNull
    @Column
    private Instant validUntil;

    public @NotNull Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(@NotNull Instant validUntil) {
        this.validUntil = validUntil;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "validUntil=" + validUntil +
                "} " + super.toString();
    }
}
