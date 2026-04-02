package org.example.gateway.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

// Need to make this MongoDb collection table
@Document(collection = "users")
public class UserModel implements Serializable {
    private String id;
    private String name;
    private String email;

    public UserModel(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
