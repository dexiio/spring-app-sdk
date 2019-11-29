package io.dexi.service.handlers;

import io.dexi.service.utils.RowStream;

import java.io.IOException;

/**
 * Implement this handler to handler data-storage components.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface DataStorageHandler<T, U> {
    /**
     * Method that is invoked whenever a data-storage component is invoked from dexi
     *
     * @param activationId the ID of the users app activation
     * @param activationConfig the activation configuration DTO
     * @param componentName the name of the component being used as defined in your dexi.yml
     * @param componentConfig the component configuration DTO
     * @param rows stream of data rows.
     * @return optionally returns something depending on the schema defined in your dexi.yml. Can just return null
     *
     * @throws IOException if write fails
     */
    Object write(String activationId, T activationConfig, String componentName, U componentConfig, RowStream rows) throws IOException;
}
