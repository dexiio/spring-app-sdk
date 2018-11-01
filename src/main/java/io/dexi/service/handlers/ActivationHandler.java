package io.dexi.service.handlers;

import io.dexi.service.exceptions.ActivationException;

public interface ActivationHandler<T> {
    void validate(T activationConfig) throws ActivationException;
}
