package io.dexi.service.rest;

import io.dexi.client.DexiClientException;
import io.dexi.client.DexiClientFactory;
import io.dexi.service.exceptions.UserErrorException;
import io.dexi.service.handlers.ActivationHandler;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class AbstractAppController<T> {

    @Autowired
    private ActivationHandler<T> activationHandler;

    @Autowired
    protected DexiClientFactory dexiClientFactory;

    protected T requireConfig(String activationId) {
        assert activationId != null && !activationId.isEmpty();

        try {
            final T activationConfig = dexiClientFactory.getActivationConfig(activationId, activationHandler.getActivationConfigClass());
            if (activationConfig == null) {
                throw new UserErrorException("Could not find activation configuration for activation id: " + activationId);
            }
            return activationConfig;
        } catch (DexiClientException e) {
            throw new UserErrorException("Could not get configuration for app activation", e);
        }
    }

}
