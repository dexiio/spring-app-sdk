package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.handlers.AppContext;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import io.dexi.service.handlers.DataFilterHandler;
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

@ConditionalOnBean(DataFilterHandler.class)
@RestController
@RequestMapping("/dexi/data/filter/")
public class DataFilterController<T, U> extends AbstractAppController<T> {

    @Autowired
    private DataFilterHandler<T, U> dataFilterHandler;

    @Autowired
    private ComponentConfigurationHandler<T, U> componentConfigurationHandler;

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
        T activationConfig = requireConfig(activationId);

        U componentConfig = objectMapper.readValue(componentConfigJson, componentConfigurationHandler.getComponentConfigClass(componentName));
        try (JsonRowStream rowStream = new JsonRowStream(jsonFactory, objectMapper, request.getInputStream())) {
            try (JsonResultStream resultStream = new JsonResultStream(jsonFactory, response.getOutputStream())) {
                dataFilterHandler.filter(new AppContext<>(activationId, activationConfig, componentName, componentConfig), rowStream, resultStream);
            }
        }
    }

}
