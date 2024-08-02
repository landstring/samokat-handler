package com.example.samokathandler.exceptions.product;

import com.example.samokathandler.exceptions.SamokatHandlerRuntimeException;

public class ProductOutOfStockException extends SamokatHandlerRuntimeException {

    public ProductOutOfStockException(String message) {
        super(message);
    }

}
