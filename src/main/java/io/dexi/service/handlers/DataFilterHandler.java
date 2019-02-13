package io.dexi.service.handlers;

import io.dexi.service.Result;

public interface DataFilterHandler<T, U> {
    default Result filter(T activationConfig, String componentName, U dataSourcePayload) { return null; }

    Class<U> getDataFilterPayloadClass();
}
