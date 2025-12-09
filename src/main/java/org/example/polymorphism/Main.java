package org.example.polymorphism;

public class Main {
    public static void main(String[] args) {
        Payment payment = new UpiPayment();
        payment.pay();
    }
}
