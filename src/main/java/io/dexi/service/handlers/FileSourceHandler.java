package io.dexi.service.handlers;

import io.dexi.service.config.ComponentConfig;

import javax.servlet.http.HttpServletResponse;

public interface FileSourceHandler<T, U extends ComponentConfig> {
    Class<U> getComponentConfigClass();

    default void read(T activationConfig, U componentConfig, HttpServletResponse response) { }
}
