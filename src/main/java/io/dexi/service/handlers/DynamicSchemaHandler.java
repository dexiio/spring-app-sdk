package io.dexi.service.handlers;

import io.dexi.service.Schema;

public interface DynamicSchemaHandler<T, U> {
    Class<T> getActivationConfigClass();

    default Schema getSchema(T activationConfig, String componentId, U dynamicSchemaPayload) { return null; }

    Class<U> getDynamicSchemaPayloadClass();
}
