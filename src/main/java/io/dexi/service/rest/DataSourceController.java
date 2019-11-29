package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.handlers.AppContext;
import io.dexi.service.handlers.DataSourceHandler;
import io.dexi.service.utils.JsonResultStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ConditionalOnBean(DataSourceHandler.class)
@RestController
@RequestMapping("/dexi/data/source")
public class DataSourceController<T, U> extends AbstractAppController<T> {

    @Autowired
    private DataSourceHandler<T, U> dataSourceHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JsonFactory jsonFactory;

    @PostMapping("/read")
    public void read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                     @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                     @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                     @RequestParam(value = "offset") int offset,
                     @RequestParam(value = "limit") int limit,
                     HttpServletResponse response) throws IOException {

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(objectMapper.readTree(componentConfigJson), dataSourceHandler.getComponentConfigClass(componentName));
        try (final JsonResultStream resultStream = new JsonResultStream(jsonFactory, response.getOutputStream())) {
            dataSourceHandler.read(new AppContext<>(activationId, activationConfig, componentName, componentConfig), offset, limit, resultStream);
        }
    }

}
