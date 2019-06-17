package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.Schema;
import io.dexi.service.handlers.DynamicConfigurationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@ConditionalOnBean(DynamicConfigurationHandler.class)
@RestController
@RequestMapping("/dexi/data/dynamic-configuration")
public class DynamicConfigurationController<T, U> extends AbstractAppController<T> {

    @Autowired
    private DynamicConfigurationHandler<T, U> dynamicConfigurationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public Schema read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson) throws URISyntaxException, IOException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, dynamicConfigurationHandler.getDynamicConfigurationPayloadClass());
        return dynamicConfigurationHandler.getConfiguration(activationConfig, componentName, componentConfig);
    }

}
