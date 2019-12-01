package io.dexi.service.rest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.DynamicSchemaConfig;
import io.dexi.service.Schema;
import io.dexi.service.handlers.AppContext;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import io.dexi.service.handlers.DynamicSchemaHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@ConditionalOnBean(DynamicSchemaHandler.class)
@RestController
@RequestMapping("/dexi/data/dynamic-schema")
public class DynamicSchemaController<T, U> extends AbstractAppController<T> {

    @Autowired
    private DynamicSchemaHandler<T, U> dynamicSchemaHandler;

    @Autowired
    private ComponentConfigurationHandler<T, U> componentConfigurationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/read")
    public Schema read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson) throws IOException {
        T activationConfig = requireConfig(activationId);
        final Class<? extends U> componentConfigClass = componentConfigurationHandler.getComponentConfigClass(componentName);
        final JavaType schemaType = objectMapper.getTypeFactory().constructParametricType(DynamicSchemaConfig.class, componentConfigClass);
        DynamicSchemaConfig<U> componentConfig = objectMapper.readValue(componentConfigJson, schemaType);
        return dynamicSchemaHandler.getSchema(new AppContext<>(activationId, activationConfig, componentName, componentConfig));
    }

}
