package com.example.samokathandler.exceptions.product;

import com.example.samokathandler.exceptions.SamokatHandlerRuntimeException;

public class ProductNotFoundException extends SamokatHandlerRuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}
