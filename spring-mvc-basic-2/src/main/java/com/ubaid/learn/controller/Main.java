package com.ubaid.learn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Main {
    
    @GetMapping
    public String getInfo() {
        return "Hello, My name is Ubaid";
    }
}
