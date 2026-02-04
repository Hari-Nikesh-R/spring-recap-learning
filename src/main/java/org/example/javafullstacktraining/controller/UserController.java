package org.example.javafullstacktraining.controller;

import org.example.javafullstacktraining.model.User;
import org.example.javafullstacktraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/store/user")
        public String storeStaticUserData(@RequestBody User user) {
        return userService.saveStaticUserData(user);
    }
}
