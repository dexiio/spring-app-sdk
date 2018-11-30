package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.Schema;
import io.dexi.service.exceptions.ComponentConfigurationException;
import io.dexi.service.handlers.DynamicConfigurationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

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
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                       @RequestBody ObjectNode componentConfigJson) throws ComponentConfigurationException {
        T activationConfig = requireConfig(activationId);
        try {
            U componentConfig = objectMapper.convertValue(componentConfigJson, dynamicConfigurationHandler.getDynamicConfigurationPayloadClass());
            return dynamicConfigurationHandler.getConfiguration(activationConfig, componentId, componentConfig);
        } catch (Exception e) {
            // Cannot provide a standard Exception apparently, we are then placed in an infinite loop of retries
            throw new ComponentConfigurationException("Invalid configuration provided", e);
        }
    }

}
