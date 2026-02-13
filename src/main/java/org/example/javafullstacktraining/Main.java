package org.example.javafullstacktraining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

/**
 * What is microservices?
 * 1 small spring boot project running individually is micro service.
 * When we combine more than 1 spring boot project and make them to work together as a single entity
 *
 * Why?
 * Food Delivery app. (Swiggy)
 * - Login
 * - Restaurant list.
 * - Order
 * - Payment
 * - Notifications.
 * - Delivery Tracking.
 *
 * Build this application as one project.
 * Everything, inside one project. - (Monolithic Architecture)
 *
 * FoodApp
 *   |- Login
 *      |- controller
 *      |- services
 *      |- repository
 *      |- model
 *   |- Orders
 *      |- controller
 *      |- services
 *      |- repository
 *      |- model
 *   |- Payments
 *   |- Notifications
 *   |- Delivery
 *
 *   => Easy to code
 *   => Easy to run
 *   => Easy to deploy.
 *
 *   But after 6 months,
 *   1. Small change -> redeploy the entire app.
 *   2. One bug -> Full system crash.
 *   3. Team conflicts
 *   4. Scaling problem.
 *   5. Now, written the code in Java, forever Java.
 *
 *   Amazon, Netflix, Uber.
 *
 *   User service -> One spring boot project. (Login) - User DB (Postgres)
 *   Order service ->  One spring boot project. (Orders) - Order DB (Mongodb)
 *   Payment service -> One spring boot project. (Payments) - Payment DB (Redis)
 *   Restautant service -> One spring boot project. (Hotels) - Hotel DB
 *   Notification service -> One spring boot project. (Notifications)
 *   Delivery service -> Python fast api. (Delivery) - Delivery DB
 *
 *   Each one:
 *      Run separately.
 *      Has its own database.
 *
 *  How microservices talk to each other.
 *  - Inter-service communication
 *  - HTTP protocol for communication.
 *  Order service ---> Rest Template / RestClient ---> Payment service.
 *
 */