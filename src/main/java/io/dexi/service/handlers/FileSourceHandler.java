package io.dexi.service.handlers;

import javax.servlet.http.HttpServletResponse;

/**
 * Implement this handler to handle file-source components.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface FileSourceHandler<T, U> extends BaseComponentHandler<U> {
    /**
     * This method is invoked whenever a file-source component is asked to read a file.
     * File content must be written to the HttpServletResponse - and ideally also include the content type and
     * potentially the filename in the Content-Disposition header.
     *
     * Will be exposed as POST /dexi/file/source/read
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param response the raw HTTP response - to write the data contents and content-type to
     */
    void read(AppContext<T,U> ctxt, HttpServletResponse response);

}
