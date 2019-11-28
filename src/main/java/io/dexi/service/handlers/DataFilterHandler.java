package io.dexi.service.handlers;

import io.dexi.service.Result;
import io.dexi.service.Rows;

public interface DataFilterHandler<T, U> {
    Result filter(T activationConfig, String componentName, U componentConfig, Rows rows);

    Class<U> getDataFilterPayloadClass(String componentName);
}
