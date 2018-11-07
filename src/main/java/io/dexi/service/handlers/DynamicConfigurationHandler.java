package io.dexi.service.handlers;

import io.dexi.service.Schema;

import java.net.URISyntaxException;

public interface DynamicConfigurationHandler<T, U> {
    Class<T> getActivationConfigClass();

    default Schema getConfiguration(T activationConfig, String componentId, U shemaReadPayload) throws URISyntaxException { return null; }

    Class<U> getDynamicConfigurationPayloadClass();
}
