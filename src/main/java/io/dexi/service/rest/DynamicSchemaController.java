package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.Schema;
import io.dexi.service.exceptions.ComponentConfigurationException;
import io.dexi.service.handlers.DynamicSchemaHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@ConditionalOnBean(DynamicSchemaHandler.class)
@RestController
@RequestMapping("/dexi/data/dynamic-schema")
public class DynamicSchemaController<T, U> extends AbstractAppController<T> {

    @Autowired
    private DynamicSchemaHandler<T, U> dynamicSchemaHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public Schema read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                       @RequestBody ObjectNode componentConfigJson) throws ComponentConfigurationException {
        T activationConfig = requireConfig(activationId);
        try {
            U componentConfig = objectMapper.convertValue(componentConfigJson, dynamicSchemaHandler.getDynamicSchemaPayloadClass());
            return dynamicSchemaHandler.getSchema(activationConfig, componentId, componentConfig);
        } catch (Exception e) {
            throw new ComponentConfigurationException("Invalid configuration provided", e);
        }
    }

}
