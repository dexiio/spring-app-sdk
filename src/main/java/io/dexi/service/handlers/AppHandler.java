package io.dexi.service.handlers;

import io.dexi.service.exceptions.ActivationException;

public interface AppHandler<T> {
    void activate(T app) throws ActivationException;
    void deactivate(T app) throws ActivationException;
}
