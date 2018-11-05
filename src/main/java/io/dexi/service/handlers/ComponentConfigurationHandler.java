package io.dexi.service.handlers;

import io.dexi.service.config.ActivationConfig;
import io.dexi.service.config.ComponentConfig;
import io.dexi.service.exceptions.ComponentConfigurationException;

public interface ComponentConfigurationHandler<T extends ActivationConfig, U extends ComponentConfig> {
    Class<U> getComponentConfigClass();

    default void validate(T activationConfig, String componentId, U componentConfig) throws ComponentConfigurationException {}
}
