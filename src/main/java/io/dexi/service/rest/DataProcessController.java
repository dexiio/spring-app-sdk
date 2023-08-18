package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.AppContext;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.components.DataProcessAppComponent;
import io.dexi.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(DataProcessAppComponent.class)
@RestController
@RequestMapping("/dexi/data")
public class DataProcessController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, DataProcessAppComponent<T, U>> dataProcessHandlers;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/process")
    public void read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                     @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                     @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                     @RequestBody Object body,
                     HttpServletResponse response) throws IOException {

        final DataProcessAppComponent<T, U> dataProcessHandler = dataProcessHandlers.get(componentName);

        if (dataProcessHandler == null) {
            throw new NotFoundException("Source handler not found for component: " + componentName);
        }
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(objectMapper.readTree(componentConfigJson), dataProcessHandler.getComponentConfigClass());
        dataProcessHandler.process(new AppContext<>(activationId, activationConfig, componentName, componentConfig), body, response);
    }

}
