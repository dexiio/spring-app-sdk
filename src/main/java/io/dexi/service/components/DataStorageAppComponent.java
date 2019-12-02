package io.dexi.service.components;

import io.dexi.service.AppContext;
import io.dexi.service.utils.RowStream;

import java.io.IOException;

/**
 * Interface for the "data-storage" component type
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface DataStorageAppComponent<T, U>  extends BaseAppComponent<U> {
    /**
     * Method that is invoked whenever a data-storage component is invoked from dexi
     *
     * Will be exposed as POST /dexi/data/storage/write
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param rows stream of data rows.
     * @return optionally returns something depending on the schema defined in your dexi.yml. Can just return null
     *
     * @throws IOException if write fails
     */
    Object write(AppContext<T,U> ctxt, RowStream rows) throws IOException;
}
