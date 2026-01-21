package org.example.javafullstacktraining.controller;

import org.example.javafullstacktraining.model.Student;
import org.example.javafullstacktraining.service.JpaExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JpaExampleController {

    @Autowired
    private JpaExampleService jpaExampleService;

    @GetMapping("/store/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("store/new")
    public String newStudent() {
        return "New Student";
    }

    @PostMapping(value = "/store/student")
        public String storeStaticStudentData(@RequestBody Student student) {
        return jpaExampleService.saveStaticStudentData(student);
    }
}
