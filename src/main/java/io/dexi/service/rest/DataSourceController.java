package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.handlers.AppContext;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import io.dexi.service.handlers.DataSourceHandler;
import io.dexi.service.utils.JsonResultStream;
import org.apache.commons.lang.StringUtils;
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
    private ComponentConfigurationHandler<T, U> componentConfigurationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JsonFactory jsonFactory;

    @PostMapping("/read")
    public void read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                     @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                     @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                     @RequestParam(value = "offset", required = false, defaultValue = "") String offset,
                     @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
                     HttpServletResponse response) throws IOException {

        if (StringUtils.isBlank(offset)) {
            offset = "";
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(objectMapper.readTree(componentConfigJson), componentConfigurationHandler.getComponentConfigClass(componentName));
        try (final JsonResultStream resultStream = new JsonResultStream(jsonFactory, response.getOutputStream())) {
            dataSourceHandler.read(new AppContext<>(activationId, activationConfig, componentName, componentConfig), offset, limit, resultStream);
        }
    }

}
