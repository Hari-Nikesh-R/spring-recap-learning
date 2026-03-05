package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Consider this file belong to payment service project
@RestController
public class PaymentController {
    @GetMapping("/payment")
    public String payment() {
        if (Math.random() < 0.7) {
            throw new RuntimeException("Payment service is Down");
        }
        return "Payment Success";
    }
}
