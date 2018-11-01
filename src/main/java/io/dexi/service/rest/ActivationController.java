package io.dexi.service.rest;

import io.dexi.client.DexiClientFactory;
import io.dexi.service.config.ActivationConfig;
import io.dexi.service.exceptions.ActivationException;
import io.dexi.service.handlers.ActivationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ConditionalOnBean(ActivationHandler.class)
@RestController
@RequestMapping("/activations/")
public class ActivationController {

    @Autowired
    private ActivationHandler activationHandler;

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    public void validateActivation(@RequestBody ActivationConfig activationConfig) throws ActivationException {
        activationHandler.validate(activationConfig);
    }

}
