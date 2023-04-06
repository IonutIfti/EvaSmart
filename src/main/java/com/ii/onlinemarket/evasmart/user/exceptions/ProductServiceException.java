package com.ii.onlinemarket.evasmart.user.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@NoArgsConstructor
public class ProductServiceException extends RuntimeException {
    public ProductServiceException(String message) {
        super(message);
    }
}

