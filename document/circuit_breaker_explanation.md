# What is Circuit breaker

## What problem does it solve?
Imagine a Microservice architecture

**Order service --> Payment service**

If the payment service down, what happens?

Order service -> Payment service (Down) -> timeout <br>
Order service -> Payment service (Down) -> timeout <br>
Order service -> Payment service (Down) -> timeout <br>

### Problem?
1. Thread exhaust.
2. Cascading failures.
3. Entire system crash.

### Solution : Circuit breakers
## Circuit Breaker states
1. Closed
2. Open
3. Half Open

### Closed 
It is a normal state, if everything works.
Normal state => Client -> Service (Spring Boot @Service) -> Client <br>

### Open 
If failures that exceed threshold
Open state => Client ---Request--> Circuit Breaker -> Fallback.

### Half Open
If successfully => Client -- Request --> Service
If failure => Client -- Request --> Circuit Breaker

### Circuit Breaker - Resilience4j
1. Add dependencies
   - Spring Web
   - Resilience4j
