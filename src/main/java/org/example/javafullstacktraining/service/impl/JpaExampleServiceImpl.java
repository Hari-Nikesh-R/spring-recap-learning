package org.example.javafullstacktraining.service.impl;

import org.example.javafullstacktraining.model.Student;
import org.example.javafullstacktraining.service.JpaExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaExampleServiceImpl implements JpaExampleService {


    @Override
    public String saveStaticStudentData(Student student) {
        // save this data in database
        // No need of writing an insert query.
        return "Successfully saved";
    }
}
