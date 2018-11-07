package io.dexi.service.handlers;

import io.dexi.service.Result;

public interface DataSourceHandler<T, U> {
    default Result read(T activationConfig, String componentId, U dataSourcePayload) { return null; }

    Class<U> getDataSourcePayloadClass();
}
