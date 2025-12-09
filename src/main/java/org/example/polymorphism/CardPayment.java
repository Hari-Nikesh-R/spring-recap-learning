package org.example.polymorphism;

public class CardPayment implements Payment {
    @Override
    public void pay() {
        System.out.println("This method contains Card payment");
    }

    @Override
    public boolean canPay() {
        return false;
    }
}
