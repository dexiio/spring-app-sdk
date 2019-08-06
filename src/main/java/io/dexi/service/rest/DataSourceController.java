package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.Result;
import io.dexi.service.handlers.DataSourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@ConditionalOnBean(DataSourceHandler.class)
@RestController
@RequestMapping("/dexi/data/source")
public class DataSourceController<T, U> extends AbstractAppController<T> {

    @Autowired
    private DataSourceHandler<T, U> dataSourceHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public Result read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson) throws IOException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(objectMapper.readTree(componentConfigJson), dataSourceHandler.getDataSourcePayloadClass(componentName));
        return dataSourceHandler.read(activationConfig, componentName, componentConfig);
    }

}
