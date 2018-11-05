package io.dexi.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ComponentConfigurationException extends Exception {
    public ComponentConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
