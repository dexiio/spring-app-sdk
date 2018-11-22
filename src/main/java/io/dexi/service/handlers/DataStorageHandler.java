package io.dexi.service.handlers;

import io.dexi.service.Rows;

public interface DataStorageHandler<T, U> {
    default Object write(String activationId, T activationConfig, String componentId, U componentConfig, Rows rows) { return false; }
}
