package org.example;

public class Student {
    private String name;
    private int age;
     int marks;
    private final String password = "Student@123";

    public Student(String name, int age, int marks) {
        this.name = name;
        this.age = age;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(String password, int marks) {
        if (this.password.equals(password)) {
            this.marks = marks;
        }
        else {
            System.out.println("Wrong password cannot modify the content");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", marks=" + marks +
                '}';
    }
}
