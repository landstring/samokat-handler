package com.example.samokathandler.exceptions.order;

import com.example.samokathandler.exceptions.SamokatHandlerRuntimeException;

public class PaymentNotFoundException extends SamokatHandlerRuntimeException {

    public PaymentNotFoundException(String message) {
        super(message);
    }

}
