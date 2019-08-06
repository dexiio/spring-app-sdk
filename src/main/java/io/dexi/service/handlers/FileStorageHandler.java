package io.dexi.service.handlers;

import javax.servlet.http.HttpServletRequest;

public interface FileStorageHandler<T, U> {
    default void write(T activationConfig, U componentConfig, HttpServletRequest request) { }

    Class<U> getComponentConfigClass(String componentName);
}
