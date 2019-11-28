package io.dexi.service.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface RowStream extends AutoCloseable {


    @SafeVarargs
    static RowStream from(Map<String, Object> ... rows) {
        return new SimpleRowStream(Arrays.asList(rows));
    }

    boolean hasNext() throws IOException;

    /**
     * Get the next row from the input
     *
     * @return next row from the input
     * @throws IOException throws if underlying stream throws
     */
    Map<String, Object> next() throws IOException;

    /**
     * Returns a list of values in batches. Useful when inserting into a database or similar where bulk operations
     * are much faster.
     *
     * @param chunkSize the size of each chunk
     * @return a batch of rows with a size of at most chunkSize
     * @throws IOException throws if the underlying stream throws
     */
    default List<Map<String, Object>> nextBatch(int chunkSize) throws IOException {
        List<Map<String,Object>> out = new ArrayList<>();

        while(out.size() < chunkSize && hasNext()) {
            out.add(next());
        }

        return out;
    }

    /**
     * Close the stream
     */
    default void close() {}
}
