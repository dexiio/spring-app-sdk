package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.Result;
import io.dexi.service.exceptions.ComponentConfigurationException;
import io.dexi.service.handlers.DataFilterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

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
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                       @RequestBody ObjectNode dataSourcePayloadJson) throws ComponentConfigurationException {
        T activationConfig = requireConfig(activationId);
        try {
            U dataSourcePayload = objectMapper.convertValue(dataSourcePayloadJson, dataFilterHandler.getDataFilterPayloadClass());
            return dataFilterHandler.filter(activationConfig, componentId, dataSourcePayload);
        } catch (Exception e) {
            throw new ComponentConfigurationException("Invalid configuration provided", e);
        }
    }

}
