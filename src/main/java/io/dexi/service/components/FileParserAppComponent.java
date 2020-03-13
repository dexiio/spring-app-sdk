package io.dexi.service.components;

import io.dexi.service.AppContext;
import io.dexi.service.utils.ResultStream;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for the "file-parser" component type
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface FileParserAppComponent<T, U> extends BaseAppComponent<U> {

    /**
     * Method that is invoked whenever a file-parser component is asked to parse a file.
     *
     * You read the file content from the request (request.getInputStream()) and also have the content-type
     * available from the headers whenever possible.
     *
     * Will be exposed as POST /dexi/file/parse
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param request the raw HTTP request - to read the file content and headers from
     * @param resultStream object for writing results to. Provided to support streaming output
     */
    void parse(AppContext<T, U> ctxt, HttpServletRequest request, ResultStream resultStream);

}
