package io.dexi.service.handlers;

import io.dexi.service.Schema;

public interface DynamicSchemaHandler<T, U> {
    Schema getSchema(T activationConfig, String componentName, U dynamicSchemaPayload);

    Class<? extends U> getDynamicSchemaPayloadClass(String componentName);
}
