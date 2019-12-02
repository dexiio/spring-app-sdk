package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.AppContext;
import io.dexi.service.components.DataStorageAppComponent;
import io.dexi.service.utils.JsonRowStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(DataStorageAppComponent.class)
@RestController
@RequestMapping("/dexi/data/storage/")
public class DataStorageController<T, U> extends AbstractAppController<T> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Map<String, DataStorageAppComponent<T, U>> dataStorageHandlers;

    @Autowired
    private JsonFactory jsonFactory;

    @PostMapping("write")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigString,
                      HttpServletRequest request) throws IOException {


        final DataStorageAppComponent<T, U> dataStorageHandler = dataStorageHandlers.get(componentName);

        if (dataStorageHandler == null) {
            throw new NotFoundException("Storage handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigString, dataStorageHandler.getComponentConfigClass());
        try (JsonRowStream rowStream = new JsonRowStream(jsonFactory, objectMapper, request.getInputStream())) {
            dataStorageHandler.write(new AppContext<>(activationId, activationConfig, componentName, componentConfig), rowStream);
        }

    }

}
