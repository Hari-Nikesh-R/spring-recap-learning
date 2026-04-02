# What is Caching?
Caching = Store frequently used data in fast memory to avoid hitting slow resource (DB / API)
<br>

Example
<br>
Instead of
Client -> Spring boot app -> DB -> Spring boot app -> Response (Client)

We Do
Client -> Spring Boot app -> Cache -> Spring boot app -> Response -> Client

# Why Caching?
* Fast Response time.
* Reduce DB load.
* Lower infrastructure cost.

# Types of Caching
1. In-Memory Cache <br>
HashMap, ConcurrencyHashMap, List, Array. -- storing inside the application memory.

2. Distributed Cache (Recommended) <br>
Redis, Memcached -- 
Shared across multiple service.

3. CDN cache (Front end)
static files like images, JSS. CSS.

# Cache Strategies
## Cache Aside (Lazy Loading)
1. Check cache
2. if miss -> fetch from the DB.
3. Store in cache.
<br>
Used everywhere

## Write through
1. Write -> Cache -> DB

## Write Behind
1. Write -> Cache -> Later DB

## Cache Eviction
1. TTL (Time to Live)
2. LRU (Least recently used Cache)

# Spring boot implementation
## What we are building?
Client -> Spring Boot app -> Cache -> DB

# Create Docker Compose yaml file for redis and MongoDB
```yml
version: '3.8'

services:
  mongodb:
    image: mongo:7.0
    container_name: mongodb
    restart: always
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: password
      MONGO_INITDB_DATABASE: testdb
    volumes:
      - mongo-data:/data/db

  redis:
    image: redis:7
    container_name: redis
    restart: always
    ports:
      - "6379:6379"
volumes:
  mongo-data:
```

# Add application properties for MongoDb and Caching
```application.properties
server.port: 8096

# MONGODB
spring.data.mongodb.uri=mongodb://admin:password@localhost:27017/testdb?authSource=admin


# Spring Cache configuration with Redis.
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.redis.time-to-live=600000
spring.cache.type=redis
```

# Enable Caching in main class
```java
@SpringBootApplication
@EnableCaching
public class CachingMechanismApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingMechanismApplication.class, args);
    }
}
```

# Create user model and make them as MongoDb collection model

```java

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Need to make this MongoDb collection table
@Document(collection = "users")
public class UserModel {
    @Id
    private String id;
    private String name;
    private String email;

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

# We need to create repository layer for mongoDb
```java
@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    
}
```
# Write Cache configuration for Redis
Create RedisConfigManager and build it
```java
@Configuration
public class CacheConfig {
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)).disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
    }
}
```
# Add Service layer code for caching
```java

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
    @CacheEvict(value = "users", key = "#users.id")
    public UserModel saveUser(UserModel userModel) {
        return userRepository.save(userModel);
    }
    
    // DELETE
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
```

# Create Controller code to execue the service layer
```java

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping
    public UserModel getUser(@PathVariable("id") String id) {
        return userService.getByUserId(id);
    }
    
    @PostMapping
    public UserModel saveUser(@RequestBody  UserModel userModel) {
        return userService.saveUser(userModel);
    }
    
    @DeleteMapping
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
```








<br>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<br><br><br><br><br>


