package io.dexi.service.components;

import io.dexi.service.AppContext;
import io.dexi.service.utils.ResultStream;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface for the "data-process" component type
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface DataProcessAppComponent<T, U> extends BaseAppComponent<U> {
    /**
     * Method that gets invoked whenever a data-process component is invoked from dexi.
     *
     * Will be exposed as POST /dexi/data/process
     *
     * @param ctxt Context information about the current activation and component configuration

     * @throws IOException if reading fails
     */
    void process(AppContext<T,U> ctxt, Object payload, HttpServletResponse response) throws IOException;

}
