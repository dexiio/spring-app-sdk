package io.dexi.service.components;

import io.dexi.service.AppContext;

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
     * Method that gets invoked whenever a component that handles processing is invoked from dexi.
     *
     * Will be exposed as POST /dexi/data/process
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param payload the request body object to be processed
     * @param response the raw HTTP response - to write the result data/object
     * @throws IOException when failing to connect, when a processing problem or when writing the response
     */
    void process(AppContext<T,U> ctxt, Object payload, HttpServletResponse response) throws IOException;

}
