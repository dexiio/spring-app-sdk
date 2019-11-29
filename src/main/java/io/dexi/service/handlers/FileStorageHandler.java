package io.dexi.service.handlers;

import javax.servlet.http.HttpServletRequest;

/**
 * Implement this handler to handle file-storage components.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface FileStorageHandler<T, U> {

    /**
     * Method that is invoked whenever a file-storage component is asked to store a file.
     *
     * You read the file content from the request (request.getInputStream()) and also have the content-type
     * available from the headers whenever possible.
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param request the raw HTTP request - to read the file content and headers from
     */
    void write(AppContext<T,U> ctxt, HttpServletRequest request);

    /**
     * Get the component configuration class. Is used for (de)serialization
     *
     * @param componentName the name of the component as defined in your dexi.yml
     *
     * @return the class itself (U)
     */
    Class<U> getComponentConfigClass(String componentName);
}
