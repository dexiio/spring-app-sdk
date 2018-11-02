package io.dexi.service.handlers;

import io.dexi.service.DynamicSchemaPayload;
import io.dexi.service.Result;
import io.dexi.service.exceptions.DataException;

public interface DataHandler<T> {
    Class<T> getActivationConfigClass();

    default Result read(T activationConfig, DynamicSchemaPayload readPayload) throws DataException { return null; }
    default boolean write(String activationId, T activationConfig, DynamicSchemaPayload writePayload) throws DataException { return false; }
    default Result filter(String activationId, T activationConfig) throws DataException { return null; }
}
