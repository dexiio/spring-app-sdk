package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.handlers.DataStorageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@ConditionalOnBean(DataStorageHandler.class)
@RestController
@RequestMapping("/dexi/data/storage/")
public class DataStorageController<T, U extends DataStorageHandler.AbstractDataStoragePayload<?>> extends AbstractAppController<T> {

    @Autowired
    private DataStorageHandler<T, U> dataStorageHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "write", method = RequestMethod.POST)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                      @RequestBody ObjectNode dataStoragePayloadJson) {
        T activationConfig = requireConfig(activationId);
        U dataStoragePayload = objectMapper.convertValue(dataStoragePayloadJson, dataStorageHandler.getDataStoragePayloadClass());
        dataStorageHandler.write(activationId, activationConfig, componentId, dataStoragePayload);
    }

}
