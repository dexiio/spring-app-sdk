package io.dexi.service.utils;

import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Map;

public interface ResultStream extends AutoCloseable {


    /**
     * Set the offset of the next page if one is available
     *
     * Note that the offset is a string due to certain data sources not using numbers for offset.
     * The next offset is decided by the data source and passed as-is on to subsequent page requests
     *
     * @param nextOffset Empty if there are no more pages available - otherwise the offset of the next page
     */
    void setNextOffset(String nextOffset);

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
