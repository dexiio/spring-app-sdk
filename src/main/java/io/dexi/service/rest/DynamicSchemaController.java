package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.Schema;
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
    private ObjectMapper objectMapper;

    @PostMapping("/read")
    public Schema read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson) throws IOException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, dynamicSchemaHandler.getComponentConfigClass(componentName));
        return dynamicSchemaHandler.getSchema(activationId, activationConfig, componentName, componentConfig);
    }

}
