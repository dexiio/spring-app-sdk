package io.dexi.service.handlers;

import io.dexi.service.exceptions.ActivationException;

public interface ActivationHandler<T> {
    Class<T> getActivationConfigClass();

    default void validate(T activationConfig) throws ActivationException {}
    default void activate(String activationId, T activationConfig) throws ActivationException {};
    default void deactivate(String activationId) throws ActivationException {};
}
