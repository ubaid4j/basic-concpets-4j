package dev.ubaid.ssbwji.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
//@Table(name = "post")
public class Post extends Topic {
    
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
