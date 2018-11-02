package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.Result;
import io.dexi.service.handlers.DataSourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@ConditionalOnBean(DataSourceHandler.class)
@RestController
@RequestMapping("/dexi/data/source/")
public class DataSourceController<T, U> extends AbstractDataController<T> {

    @Autowired
    private DataSourceHandler<T, U> dataSourceHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "read", method = RequestMethod.POST)
    public Result read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                       @RequestBody ObjectNode dataSourcePayloadJson) {
        T activationConfig = requireConfig(activationId);
        U dataSourcePayload = objectMapper.convertValue(dataSourcePayloadJson, dataSourceHandler.getDataSourcePayloadClass());
        return dataSourceHandler.read(activationConfig, componentId, dataSourcePayload);
    }

}
