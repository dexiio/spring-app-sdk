package io.dexi.service.handlers;

import io.dexi.service.Schema;

public interface DynamicSchemaHandler<T, U> {
    default Schema getSchema(T activationConfig, String componentName, U dynamicSchemaPayload) { return null; }

    Class<? extends U> getDynamicSchemaPayloadClass(String componentName);
}
