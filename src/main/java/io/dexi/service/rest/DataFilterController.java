package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.Result;
import io.dexi.service.Rows;
import io.dexi.service.handlers.DataFilterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@ConditionalOnBean(DataFilterHandler.class)
@RestController
@RequestMapping("/dexi/data/filter/")
public class DataFilterController<T, U> extends AbstractAppController<T> {

    @Autowired
    private DataFilterHandler<T, U> dataFilterHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "invoke", method = RequestMethod.POST)
    public Result filter(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                       @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                       @RequestBody Rows rows) throws IOException {
        T activationConfig = requireConfig(activationId);

        U dataSourcePayload = objectMapper.readValue(componentConfigJson, dataFilterHandler.getDataFilterPayloadClass(componentName));
        return dataFilterHandler.filter(activationConfig, componentName, dataSourcePayload, rows);
    }

}
