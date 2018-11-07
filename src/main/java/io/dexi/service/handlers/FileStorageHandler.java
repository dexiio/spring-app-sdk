package io.dexi.service.handlers;

import javax.servlet.http.HttpServletRequest;

public interface FileStorageHandler<T, U> {
    Class<U> getComponentConfigClass();

    default void write(T activationConfig, U componentConfig, HttpServletRequest request) { }
}
