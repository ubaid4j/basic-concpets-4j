package dev.ubaid.ssbwj.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class PostV2 extends Topic {
    
    @NotNull
    @Column
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "PostV2{" +
                "content='" + content + '\'' +
                "} " + super.toString();
    }
}
