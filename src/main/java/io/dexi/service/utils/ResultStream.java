package io.dexi.service.utils;

import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Map;

public interface ResultStream extends AutoCloseable {

    /**
     * Append row to the result stream
     *
     * @param row the result row
     * @throws IOException throws if underlying stream is closed or unavailable
     */
    void append(Map<String,Object> row) throws IOException;

    /**
     * Close the stream
     * @throws IOException throws if the underlying stream throws
     */
    default void close() throws IOException {}
}
