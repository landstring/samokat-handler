package com.example.samokathandler.exceptions.order;

import com.example.samokathandler.exceptions.SamokatHandlerRuntimeException;

public class CurrentOrderNotFoundException extends SamokatHandlerRuntimeException {

    public CurrentOrderNotFoundException(String message) {
        super(message);
    }

}
