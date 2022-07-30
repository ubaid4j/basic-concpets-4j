package com.ubaid.learn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MainController {
    
    @GetMapping
    public String getInfo() {
        throw new RuntimeException("errorCode.badRequest");
    }
}
