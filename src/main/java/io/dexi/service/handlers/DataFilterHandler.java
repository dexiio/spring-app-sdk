package io.dexi.service.handlers;

import io.dexi.service.Result;
import io.dexi.service.Rows;

public interface DataFilterHandler<T, U> {
    default Result filter(T activationConfig, String componentName, U componentConfig, Rows rows) { return null; }

    Class<U> getDataFilterPayloadClass();
}
