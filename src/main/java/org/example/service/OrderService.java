package org.example.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
// Consider this file belong to order service project
@Service
public class OrderService {
    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallBack")
    public String placeOrder() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/payment", String.class);
    }

    public String fallback(Exception e) {
        return "Payment Service service is unavailable, please try again later";
    }
}
