package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.AppContext;
import io.dexi.service.components.DataFilterAppComponent;
import io.dexi.service.utils.JsonResultStream;
import io.dexi.service.utils.JsonRowStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(DataFilterAppComponent.class)
@RestController
@RequestMapping("/dexi/data/filter/")
public class DataFilterController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, DataFilterAppComponent<T, U>> dataFilterHandlers;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JsonFactory jsonFactory;

    @PostMapping("invoke")
    public void filter(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                       HttpServletResponse response,
                       HttpServletRequest request) throws IOException {

        final DataFilterAppComponent<T, U> dataFilterHandler = dataFilterHandlers.get(componentName);

        if (dataFilterHandler == null) {
            throw new NotFoundException("Filter handler not foudn for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);

        U componentConfig = objectMapper.readValue(componentConfigJson, dataFilterHandler.getComponentConfigClass());
        try (JsonRowStream rowStream = new JsonRowStream(jsonFactory, objectMapper, request.getInputStream())) {
            try (JsonResultStream resultStream = new JsonResultStream(jsonFactory, response.getOutputStream())) {
                dataFilterHandler.filter(new AppContext<>(activationId, activationConfig, componentName, componentConfig), rowStream, resultStream);
            }
        }
    }

}
