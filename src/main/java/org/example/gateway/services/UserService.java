package org.example.gateway.services;

import org.apache.catalina.User;
import org.example.gateway.model.UserModel;
import org.example.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // READ
    @Cacheable(value = "users", key = "#id")
    public UserModel getByUserId(String id) {
        return userRepository.findById(id).orElse(null);
    }

    // CREATE / UPDATE
    @CacheEvict(value = "users", key = "#user.id", condition = "#user != null")
    public UserModel saveUser(UserModel userModel) {
        return userRepository.save(userModel);
    }

    // DELETE
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
