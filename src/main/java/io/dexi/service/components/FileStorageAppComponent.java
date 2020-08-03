package io.dexi.service.components;

import io.dexi.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for the "file-storage" component type
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface FileStorageAppComponent<T, U> extends BaseAppComponent<U> {

    /**
     * Method that is invoked whenever a file-storage component is asked to store a file.
     *
     * You read the file content from the request (request.getInputStream()) and also have the content-type
     * available from the headers whenever possible.
     *
     * Will be exposed as POST /dexi/file/storage/write
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param request the raw HTTP request - to read the file content and headers from
     */
    String write(AppContext<T,U> ctxt, HttpServletRequest request);

}
