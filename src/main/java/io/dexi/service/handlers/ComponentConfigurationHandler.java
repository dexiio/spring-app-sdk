package io.dexi.service.handlers;

import io.dexi.service.exceptions.ComponentConfigurationException;

/**
 * Implement this to validate component configuration for a given activation.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface ComponentConfigurationHandler<T, U> {
    /**
     * Is invoked whenever the user uses a component and clicks "Test configuration" for a component.
     *
     * @param ctxt Context information about the current activation and component configuration
     * @throws ComponentConfigurationException throws exception if configuration is invalid
     */
    void validate(AppContext<T,U> ctxt) throws ComponentConfigurationException;

    /**
     * Returns class for component configuration DTO. Is used for (de)serialization
     *
     * @param componentName The name of the component as defined in your dexi.yml file
     * @return the class itself (U)
     */
    Class<? extends U> getComponentConfigClass(String componentName);
}
