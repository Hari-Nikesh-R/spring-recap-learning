package org.example.javafullstacktraining.service.impl;

import org.example.javafullstacktraining.model.User;
import org.example.javafullstacktraining.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public String saveStaticUserData(User user) {
        // save this data in database
        // No need of writing an insert query.
        return "Successfully saved";
    }
}
