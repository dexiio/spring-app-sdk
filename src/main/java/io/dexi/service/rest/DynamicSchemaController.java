package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.Schema;
import io.dexi.service.handlers.DataSourceHandler;
import io.dexi.service.handlers.DynamicSchemaHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@ConditionalOnBean(DataSourceHandler.class)
@RestController
@RequestMapping("/dexi/data/dynamic-schema/")
public class DynamicSchemaController<T, U> extends AbstractAppController<T> {

    @Autowired
    private DynamicSchemaHandler<T, U> dynamicSchemaHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "dynamic-schema", method = RequestMethod.GET)
    public Schema read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                       @RequestBody ObjectNode componentConfigJson) {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(componentConfigJson, dynamicSchemaHandler.getDynamicSchemaPayloadClass());
        return dynamicSchemaHandler.getSchema(activationConfig, componentId, componentConfig);
    }

}
