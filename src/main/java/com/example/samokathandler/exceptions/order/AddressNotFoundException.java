package com.example.samokathandler.exceptions.order;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException() {
    }

    public AddressNotFoundException(String message) {
        super(message);
    }
}
