package com.ii.onlinemarket.evasmart.user.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@NoArgsConstructor
public class CartServiceException extends RuntimeException {
    public CartServiceException(String message) {
        super(message);
    }
    public CartServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

