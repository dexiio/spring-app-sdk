package io.dexi.service.handlers;

import io.dexi.service.utils.ResultStream;

import java.io.IOException;

/**
 * Implement this handler to handle data-source components.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface DataSourceHandler<T, U> {
    /**
     * Method that gets invoked whenever a data-source component is invoked from dexi.
     *
     * @param activationId the ID of the users app activation
     * @param activationConfig the activation configuration DTO
     * @param componentName the name of the component being used as defined in your dexi.yml
     * @param componentConfig the component configuration DTO
     * @param resultStream object for writing results to. Provided to support streaming output
     * @throws IOException if reading fails
     */
    void read(String activationId, T activationConfig, String componentName, U componentConfig, ResultStream resultStream) throws IOException;

    /**
     * Get the component configuration class. Is used for (de)serialization
     *
     * @param componentName the name of the component as defined in your dexi.yml
     *
     * @return the class itself (U)
     */
    Class<U> getComponentConfigClass(String componentName);
}
