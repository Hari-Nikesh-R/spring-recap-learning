package org.example.polymorphism;

public class UpiPayment implements Payment {
    @Override
    public void pay() {
        System.out.println("This method contains UPI payment");
    }

    public void checkInternetConnection() {
        System.out.println("Internet connection available");
    }

    @Override
    public boolean canPay() {
        return false;
    }
}
