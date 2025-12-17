package org.example.javafullstacktraining.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    private String firstName;
    private String lastName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rollNo;
    private String email;
    private String address;

    public Student() {}

    public Student(String firstName, String lastName, Long rollNo, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNo = rollNo;
        this.email = email;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
