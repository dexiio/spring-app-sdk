package io.dexi.service.handlers;

import io.dexi.service.Schema;

import java.net.URISyntaxException;

public interface DynamicConfigurationHandler<T, U> {
    Schema getConfiguration(T activationConfig, String componentName, U schemaPayload);

    Class<U> getDynamicConfigurationPayloadClass(String componentName);
}
