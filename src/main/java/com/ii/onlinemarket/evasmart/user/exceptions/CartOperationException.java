package com.ii.onlinemarket.evasmart.user.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@NoArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CartOperationException extends RuntimeException {
    public CartOperationException(String message) {
        super(message);
    }
}
