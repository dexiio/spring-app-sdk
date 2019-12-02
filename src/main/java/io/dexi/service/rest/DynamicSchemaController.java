package io.dexi.service.rest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.DynamicSchemaConfig;
import io.dexi.service.Schema;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.AppContext;
import io.dexi.service.components.ComponentHasDynamicDataSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(ComponentHasDynamicDataSchema.class)
@RestController
@RequestMapping("/dexi/data/dynamic-schema")
public class DynamicSchemaController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, ComponentHasDynamicDataSchema<T, U>> dynamicSchemaHandlers;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/read")
    public Schema read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson) throws IOException {

        final ComponentHasDynamicDataSchema<T, U> dynamicSchemaHandler = dynamicSchemaHandlers.get(componentName);
        if (dynamicSchemaHandler == null) {
            throw new NotFoundException("Dynamic schema handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        final Class<? extends U> componentConfigClass = dynamicSchemaHandler.getComponentConfigClass();
        final JavaType schemaType = objectMapper.getTypeFactory().constructParametricType(DynamicSchemaConfig.class, componentConfigClass);
        DynamicSchemaConfig<U> componentConfig = objectMapper.readValue(componentConfigJson, schemaType);
        return dynamicSchemaHandler.getSchema(new AppContext<>(activationId, activationConfig, componentName, componentConfig));
    }

}
