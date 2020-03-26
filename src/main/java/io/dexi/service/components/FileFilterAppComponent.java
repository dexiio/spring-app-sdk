package io.dexi.service.components;

import io.dexi.service.AppContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for the "file-filter" component type
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface FileFilterAppComponent<T, U> extends BaseAppComponent<U> {

    /**
     * Method that is invoked whenever a file-filter component is asked to process a file.
     *
     * You read the file content from the request (request.getInputStream()) and also have the content-type
     * available from the headers whenever possible. You then process the stream however is needed and
     * write the resulting filtered stream to response.getOutputStream()
     *
     * Will be exposed as POST /dexi/file/filter
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param request the raw HTTP request - to read the file content and headers from
     * @param response the raw HTTP response - to write the resulting file content and headers to
     */
    void filter(AppContext<T, U> ctxt, HttpServletRequest request, HttpServletResponse response);

}
