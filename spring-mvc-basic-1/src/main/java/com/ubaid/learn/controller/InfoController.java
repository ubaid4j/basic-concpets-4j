package com.ubaid.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
public class InfoController {
    
    @Autowired
    String info;
    
    @GetMapping("system")
    public String appInfo() {
        return info;
    } 
}
