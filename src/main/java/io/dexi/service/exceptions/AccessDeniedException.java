package io.dexi.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
