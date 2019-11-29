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
     * @param ctxt Context information about the current activation and component configuration
     * @param rows stream of data rows.
     * @return optionally returns something depending on the schema defined in your dexi.yml. Can just return null
     *
     * @throws IOException if write fails
     */
    Object write(AppContext<T,U> ctxt, RowStream rows) throws IOException;
}
