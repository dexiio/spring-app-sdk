package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.AppContext;
import io.dexi.service.components.DataSourceAppComponent;
import io.dexi.service.utils.JsonResultStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(DataSourceAppComponent.class)
@RestController
@RequestMapping("/dexi/data/source")
public class DataSourceController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, DataSourceAppComponent<T, U>> dataSourceHandlers;

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


        final DataSourceAppComponent<T, U> dataSourceHandler = dataSourceHandlers.get(componentName);

        if (dataSourceHandler == null) {
            throw new NotFoundException("Source handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(objectMapper.readTree(componentConfigJson), dataSourceHandler.getComponentConfigClass());
        try (final JsonResultStream resultStream = new JsonResultStream(jsonFactory, response.getOutputStream())) {
            dataSourceHandler.read(new AppContext<>(activationId, activationConfig, componentName, componentConfig), offset, limit, resultStream);
        }
    }

}
