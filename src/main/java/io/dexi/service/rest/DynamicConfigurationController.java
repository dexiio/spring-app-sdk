package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.Schema;
import io.dexi.service.handlers.AppContext;
import io.dexi.service.handlers.DynamicConfigurationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/read")
    public Schema read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson) throws URISyntaxException, IOException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, dynamicConfigurationHandler.getComponentConfigClass(componentName));
        return dynamicConfigurationHandler.getConfiguration(new AppContext<>(activationId, activationConfig, componentName, componentConfig));
    }

}
