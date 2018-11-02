package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.DynamicSchemaPayload;
import io.dexi.service.Result;
import io.dexi.service.exceptions.DataException;
import io.dexi.service.handlers.ActivationHandler;
import io.dexi.service.handlers.DataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@ConditionalOnBean(ActivationHandler.class)
@RestController
@RequestMapping("/dexi/data/")
public class DataController<T> {

    @Autowired
    private DataHandler<T> dataHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "read", method = RequestMethod.POST)
    public Result read(@RequestBody ObjectNode activationConfigJson,
                       @RequestBody ObjectNode readPayloadJson) throws DataException {
        T activationConfig = objectMapper.convertValue(activationConfigJson, dataHandler.getActivationConfigClass());
        DynamicSchemaPayload readPayload = objectMapper.convertValue(readPayloadJson, DynamicSchemaPayload.class);
        return dataHandler.read(activationConfig, readPayload);
    }

    @RequestMapping(value = "write", method = RequestMethod.POST)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestBody ObjectNode activationConfigJson,
                      @RequestBody ObjectNode writePayloadJson) throws DataException {
        T activationConfig = objectMapper.convertValue(activationConfigJson, dataHandler.getActivationConfigClass());
        DynamicSchemaPayload writePayload = objectMapper.convertValue(writePayloadJson, DynamicSchemaPayload.class);
        dataHandler.write(activationId, activationConfig, writePayload);
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    public Result filter(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                         @RequestBody ObjectNode activationConfigJson) throws DataException {
        T activationConfig = objectMapper.convertValue(activationConfigJson, dataHandler.getActivationConfigClass());
        return dataHandler.filter(activationId, activationConfig);
    }

}
