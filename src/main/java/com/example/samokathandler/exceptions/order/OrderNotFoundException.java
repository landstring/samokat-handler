package com.example.samokathandler.exceptions.order;

import com.example.samokathandler.exceptions.SamokatHandlerRuntimeException;

public class OrderNotFoundException extends SamokatHandlerRuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }

}
