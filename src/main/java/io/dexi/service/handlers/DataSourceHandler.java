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
     * @param ctxt Context information about the current activation and component configuration
     * @param resultStream object for writing results to. Provided to support streaming output
     * @param offset The offset for this result set. We read rows in batches to avoid long-running connections.
     * @param batchSize the size of the batch to retrieve (limit)
     * @throws IOException if reading fails
     */
    void read(AppContext<T,U> ctxt, String offset, int batchSize, ResultStream resultStream) throws IOException;

    /**
     * Get the component configuration class. Is used for (de)serialization
     *
     * @param componentName the name of the component as defined in your dexi.yml
     *
     * @return the class itself (U)
     */
    Class<U> getComponentConfigClass(String componentName);
}
