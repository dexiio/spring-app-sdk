package io.dexi.service.handlers;

import io.dexi.service.exceptions.ComponentConfigurationException;

public interface ComponentConfigurationHandler<T, U> {
    default void validate(T activationConfig, String componentName, U componentConfig) throws ComponentConfigurationException {}

    Class<U> getComponentConfigClass();
}
