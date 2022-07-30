package com.ubaid.learn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdviceController {
    
    @GetMapping(value = "/error") 
    public String hanlde() {
        return "Some error happened, I guess we don't have converter to show this error";
    }
}
