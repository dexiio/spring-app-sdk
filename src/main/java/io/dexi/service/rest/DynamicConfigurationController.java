package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.Schema;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.AppContext;
import io.dexi.service.components.ComponentHasDynamicConfigurationSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@ConditionalOnBean(ComponentHasDynamicConfigurationSchema.class)
@RestController
@RequestMapping("/dexi/data/dynamic-configuration")
public class DynamicConfigurationController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, ComponentHasDynamicConfigurationSchema<T, U>> dynamicConfigurationHandlers;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/read")
    public Schema read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson) throws URISyntaxException, IOException {


        final ComponentHasDynamicConfigurationSchema<T, U> dynamicConfigurationHandler = dynamicConfigurationHandlers.get(componentName);

        if (dynamicConfigurationHandler == null) {
            throw new NotFoundException("Dynamic configuration handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, dynamicConfigurationHandler.getComponentConfigClass());
        return dynamicConfigurationHandler.getConfigurationSchema(new AppContext<>(activationId, activationConfig, componentName, componentConfig));
    }

}
