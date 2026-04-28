package org.example.javafullstacktraining.model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private Long rollNo;
    private String email;

    public User() {}

    public User(int id, String firstName, String lastName, Long rollNo, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNo = rollNo;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getRollNo() {
        return rollNo;
    }

    public void setRollNo(Long rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
