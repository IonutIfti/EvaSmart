package com.ii.onlinemarket.evasmart.user.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@NoArgsConstructor
public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
    public UserServiceException(String message,Throwable cause) {
        super(message, cause);
    }

}

