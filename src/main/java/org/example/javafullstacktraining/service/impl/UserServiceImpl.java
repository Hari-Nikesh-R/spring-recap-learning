package org.example.javafullstacktraining.service.impl;

import org.example.javafullstacktraining.model.User;
import org.example.javafullstacktraining.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> fetchAllUser() {
        return List.of();
    }

    @Override
    public User getSpecificUser(int id) {
        return new User(id, "Hari", "Nikesh", 10L, "hari@gmail.com");
    }
}
