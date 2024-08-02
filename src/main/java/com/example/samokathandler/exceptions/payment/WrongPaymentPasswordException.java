package com.example.samokathandler.exceptions.payment;

import com.example.samokathandler.exceptions.SamokatHandlerRuntimeException;

public class WrongPaymentPasswordException extends SamokatHandlerRuntimeException {

    public WrongPaymentPasswordException(String message) {
        super(message);
    }

}
