package org.example.controller;

import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Consider this file belong to order service project
@RestController
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/order")
    public String order() {
        return service.placeOrder();
    }
}
