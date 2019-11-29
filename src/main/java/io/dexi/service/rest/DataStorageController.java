package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import io.dexi.service.handlers.DataStorageHandler;
import io.dexi.service.utils.JsonRowStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private JsonFactory jsonFactory;

    @PostMapping("write")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigString,
                      HttpServletRequest request) throws IOException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigString, componentConfigurationHandler.getComponentConfigClass(componentName));
        try (JsonRowStream rowStream = new JsonRowStream(jsonFactory, objectMapper, request.getInputStream())) {
            dataStorageHandler.write(activationId, activationConfig, componentName, componentConfig, rowStream);
        }

    }

}
