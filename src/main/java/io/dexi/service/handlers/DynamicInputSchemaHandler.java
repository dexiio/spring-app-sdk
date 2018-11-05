package io.dexi.service.handlers;

import io.dexi.service.Schema;

public interface DynamicInputSchemaHandler<T, U> {
    Class<T> getActivationConfigClass();

    default Schema getSchema(T activationConfig, String componentId, U dynamicInputSchemaPayload) { return null; }

    Class<U> getDynamicInputSchemaPayloadClass();
}
