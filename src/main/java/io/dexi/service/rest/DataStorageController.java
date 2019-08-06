package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.Rows;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import io.dexi.service.handlers.DataStorageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
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
    // Ensure HTTP 204/205 is returned for successful calls of this method to avoid client (okhttp) choking with
    // "No content to map due to end-of-input" error attempting to parse the empty string as JSON.
    // TODO: make more generic fix by sub-classing appropriate Jackson converter used by okhttp
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigString,
                      @RequestBody Rows rows) throws IOException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigString, componentConfigurationHandler.getComponentConfigClass(componentName));

        dataStorageHandler.write(activationId, activationConfig, componentName, componentConfig, rows);
    }

}
