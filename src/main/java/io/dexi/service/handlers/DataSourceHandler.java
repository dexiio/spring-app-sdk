package io.dexi.service.handlers;

import io.dexi.service.utils.JsonResultStream;
import io.dexi.service.utils.ResultStream;

import java.io.IOException;

public interface DataSourceHandler<T, U> {
    void read(T activationConfig, String componentName, U componentConfig, ResultStream resultStream) throws IOException;

    Class<U> getComponentConfigClass(String componentName);
}
