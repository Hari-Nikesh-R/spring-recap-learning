package org.example.exceptions;

public class MainExample2 {
    public static void main(String[] args) {
        int age = 23;
        validation(age);
    }

    static void validation(int age) {
        if (age < 18) {
        } else {
            System.out.println("Allow the person");
        }
    }
}
