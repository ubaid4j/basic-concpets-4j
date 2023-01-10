package com.ubaid.learn.annotatedController.controller;

import com.github.javafaker.Faker;
import com.ubaid.learn.annotatedController.domain.Person;
import com.ubaid.learn.annotatedController.service.AppService;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MainController {

    private final static Faker faker = new Faker();
    
    private final AppService appService;
    
    public MainController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }
    
    @GetMapping("initialization-counter")
    public String getAppServiceInitializationCounter() {
        return String.valueOf(appService.getInitializedCounter());
    }
    
    @GetMapping("persons")
    public ResponseEntity<List<Person>> getPersons() {
        List<Person> persons = IntStream.range(0, 10).mapToObj(i -> {
           Person person = new Person();
           person.setUsername(faker.name().username());
           person.setAge(faker.number().numberBetween(18, 72));
           return person;
        }).toList();
        return ResponseEntity.ok(persons);
    }
}
