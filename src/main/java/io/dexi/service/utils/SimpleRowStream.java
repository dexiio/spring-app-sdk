package io.dexi.service.utils;

import java.io.IOException;
import java.util.*;

public class SimpleRowStream implements RowStream {

    private final List<Map<String, Object>> rows;

    public SimpleRowStream(Collection<Map<String, Object>> rows) {
        this.rows = new ArrayList<>(rows);
    }

    @Override
    public boolean hasNext() throws IOException {
        return !rows.isEmpty();
    }

    @Override
    public Map<String, Object> next() throws IOException {
        return rows.remove(0);
    }
}
