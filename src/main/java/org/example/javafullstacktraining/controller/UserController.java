package org.example.javafullstacktraining.controller;


import org.example.javafullstacktraining.model.User;
import org.example.javafullstacktraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<EntityModel<User>> getUser(@PathVariable("id") int id) {
        // consider that I retrieved this data from database.
        // Wrapped in the Entity model.
        EntityModel<User> model = EntityModel.of(userService.getSpecificUser(id));

        model.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());

        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("get-all-users"));

        model.add(linkTo(methodOn(UserController.class).deleteUser()).withRel("delete-users"));

        return ResponseEntity.ok(model);
    }

    @GetMapping("/user/all")
    public ResponseEntity<EntityModel<List<User>>> getAllUsers() {

        EntityModel<List<User>> model = EntityModel.of(userService.fetchAllUser());
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());

        model.add(linkTo(methodOn(UserController.class).getUser(1)).withRel("get-users"));

        model.add(linkTo(methodOn(UserController.class).deleteUser()).withRel("delete-users"));

        return ResponseEntity.ok(EntityModel.of(List.of()));
    }

    @DeleteMapping("/v1/user/delete")
    public ResponseEntity<EntityModel<String>> deleteUser() {

        EntityModel<List<User>> model = EntityModel.of(List.of());
        model.add(linkTo(methodOn(UserController.class).deleteUser()).withSelfRel());

        model.add(linkTo(methodOn(UserController.class).getUser(1)).withRel("get-users"));

        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("get-all-users"));
        return ResponseEntity.ok(EntityModel.of("User deleted successful"));
    }
}
