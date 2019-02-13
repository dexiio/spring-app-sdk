package io.dexi.service.handlers;

import io.dexi.service.Schema;

import java.net.URISyntaxException;

public interface DynamicConfigurationHandler<T, U> {
    default Schema getConfiguration(T activationConfig, String componentName, U shemaReadPayload) throws URISyntaxException { return null; }

    Class<U> getDynamicConfigurationPayloadClass();
}
