package org.example.javafullstacktraining.controller;

import org.example.javafullstacktraining.model.Student;
import org.example.javafullstacktraining.service.JpaExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JpaExampleController {

    @Autowired
    private JpaExampleService jpaExampleService;

    @PostMapping(value = "/store/student")
        public String storeStaticStudentData(@RequestBody Student student) {
        return jpaExampleService.saveStaticStudentData(student);
    }
}
