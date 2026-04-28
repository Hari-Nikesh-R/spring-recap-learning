package org.example.javafullstacktraining.service;

import org.example.javafullstacktraining.model.User;

import java.util.List;

public interface UserService {
    List<User> fetchAllUser();

    User getSpecificUser(int id);

}
