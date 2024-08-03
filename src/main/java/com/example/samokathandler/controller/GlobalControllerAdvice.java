package com.example.samokathandler.controller;

import com.example.samokathandler.exceptions.SamokatHandlerRuntimeException;
import com.example.samokathandler.exceptions.product.ProductOutOfStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(ProductOutOfStockException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleProductOutOfStockException(ProductOutOfStockException exception) {
        log.debug("Возникло исключение: {}", exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler(SamokatHandlerRuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSamokatHandlerRuntimeException(SamokatHandlerRuntimeException exception) {
        log.debug("Возникло исключение: {}", exception.getMessage());
        return exception.getMessage();
    }

}
