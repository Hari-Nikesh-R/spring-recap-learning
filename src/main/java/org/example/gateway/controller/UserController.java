package org.example.gateway.controller;

import org.example.gateway.model.UserModel;
import org.example.gateway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public UserModel getUser(@PathVariable("id") String id) {
        return userService.getByUserId(id);
    }

    @PostMapping("/user")
    public UserModel saveUser(@RequestBody UserModel userModel) {
        return userService.saveUser(userModel);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
