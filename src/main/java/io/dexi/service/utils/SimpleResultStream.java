package io.dexi.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleResultStream implements ResultStream {

    private final List<Map<String, Object>> result = new ArrayList<>();

    @Override
    public void append(Map<String, Object> row) {
        result.add(row);
    }

    public List<Map<String, Object>> getResult() {
        return result;
    }
}
