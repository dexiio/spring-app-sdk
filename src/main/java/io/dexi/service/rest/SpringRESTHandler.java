package io.dexi.service.rest;

import io.dexi.client.DexiClientFactory;
import io.dexi.service.config.ActivationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/apps/")
public class SpringRESTHandler {

    @Autowired
    private DexiClientFactory dexiClientFactory;

    @RequestMapping(value = "validate/activation", method = RequestMethod.POST)
    public void validateActivation(@RequestBody ActivationConfig activationConfig) {

        activationConfig.validate();

        //service.validate(activationConfig);

    }
}
