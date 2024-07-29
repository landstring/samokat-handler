package com.example.samokathandler.exceptions.order;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException() {
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
