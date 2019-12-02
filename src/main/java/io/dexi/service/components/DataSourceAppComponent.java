package io.dexi.service.components;

import io.dexi.service.AppContext;
import io.dexi.service.utils.ResultStream;

import java.io.IOException;

/**
 * Interface for the "data-source" component type
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface DataSourceAppComponent<T, U> extends BaseAppComponent<U> {
    /**
     * Method that gets invoked whenever a data-source component is invoked from dexi.
     *
     * Will be exposed as POST /dexi/data/source/read
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param resultStream object for writing results to. Provided to support streaming output
     * @param offset The offset for this result set. We read rows in batches to avoid long-running connections.
     * @param batchSize the size of the batch to retrieve (limit)
     * @throws IOException if reading fails
     */
    void read(AppContext<T,U> ctxt, String offset, int batchSize, ResultStream resultStream) throws IOException;

}
