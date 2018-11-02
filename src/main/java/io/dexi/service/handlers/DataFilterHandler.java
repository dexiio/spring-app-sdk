package io.dexi.service.handlers;

import io.dexi.service.Result;

public interface DataFilterHandler<T, U> {
    Class<T> getActivationConfigClass();

    default Result read(T activationConfig, String componentId, U dataSourcePayload) { return null; }

    Class<U> getDataFilterPayloadClass();
}
