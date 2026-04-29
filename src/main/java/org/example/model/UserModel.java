package org.example.model;


public class UserModel {

    private Long id;
    private String name;
    private boolean active;
    private double Salary;

    public UserModel(Long id, String name, boolean active, double salary) {
        this.id = id;
        this.name = name;
        this.active = active;
        Salary = salary;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    public UserModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
