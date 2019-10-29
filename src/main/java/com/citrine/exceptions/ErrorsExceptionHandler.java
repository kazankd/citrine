package com.citrine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequest(RuntimeException ex) {
        return "Bogus request: " + ex.getMessage();
    }
}