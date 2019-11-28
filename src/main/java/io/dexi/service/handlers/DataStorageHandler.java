package io.dexi.service.handlers;

import io.dexi.service.utils.RowStream;

import java.io.IOException;

public interface DataStorageHandler<T, U> {
    Object write(String activationId, T activationConfig, String componentName, U componentConfig, RowStream rows) throws IOException;
}
