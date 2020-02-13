package io.dexi.service.exceptions;

public class ActivationException extends Exception {

    public ActivationException(String message) {
        super(message);
    }

    public ActivationException(String message, Throwable cause) {
        super(message, cause);
    }
}
