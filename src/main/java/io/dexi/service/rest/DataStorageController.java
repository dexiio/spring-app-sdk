package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.Rows;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import io.dexi.service.handlers.DataStorageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@ConditionalOnBean(DataStorageHandler.class)
@RestController
@RequestMapping("/dexi/data/storage/")
public class DataStorageController<T, U> extends AbstractAppController<T> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ComponentConfigurationHandler<T, U> componentConfigurationHandler;

    @Autowired
    private DataStorageHandler<T, U> dataStorageHandler;

    @RequestMapping(value = "write", method = RequestMethod.POST)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigString,
                      @RequestBody Rows rows) throws IOException {
        T activationConfig = requireConfig(activationId);

        ObjectNode componentConfigJson = objectMapper.readValue(componentConfigString, ObjectNode.class);
        U componentConfig = objectMapper.convertValue(componentConfigJson, componentConfigurationHandler.getComponentConfigClass());

        dataStorageHandler.write(activationId, activationConfig, componentId, componentConfig, rows);
    }

}
