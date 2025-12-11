package org.example.javafullstacktraining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Data persistence.
 * JDBC - Java DataBase Connectivity.
 * Spring JDBC Template / Spring Data JPA (Hiberate)
 *
 * Let's about JDBC template
 * 1. We need to add Java dependencies for JPA
 * 2. We need to add the Database(Postgresql) library.
 */

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Hello", "World");
        list.forEach(System.out::println);
//        SpringApplication.run(Main.class, args);
    }
}