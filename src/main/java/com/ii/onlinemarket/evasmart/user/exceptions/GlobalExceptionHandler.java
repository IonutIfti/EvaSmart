package com.ii.onlinemarket.evasmart.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            ProductNotFoundException.class,
            CartNotFoundException.class,
            UserNotFoundException.class,
            CartItemNotFoundException.class,
            CartOperationException.class,
            CartServiceException.class,
            ProductServiceException.class,
            UserServiceException.class
    })
    public ResponseEntity<ErrorResponse> handleCustomException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        if (ex instanceof CartOperationException || ex instanceof CartServiceException || ex instanceof ProductServiceException || ex instanceof UserServiceException) {
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

