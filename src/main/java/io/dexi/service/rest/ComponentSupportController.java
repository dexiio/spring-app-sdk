package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.exceptions.ComponentConfigurationException;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dexi/component/support/")
public class ComponentSupportController<T, U> extends AbstractAppController<T> {

    @Autowired
    private ComponentConfigurationHandler<T, U> componentConfigurationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    // Ensure HTTP 204/205 is returned for successful calls of this method to avoid client (okhttp) choking with
    // "No content to map due to end-of-input" error attempting to parse the empty string as JSON.
    // TODO: make more generic fix by sub-classing appropriate Jackson converter used by okhttp
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void validateComponentConfiguration(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                                               @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                                               @RequestBody ObjectNode componentConfigJson) throws ComponentConfigurationException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(componentConfigJson, componentConfigurationHandler.getComponentConfigClass());
        try {
            componentConfigurationHandler.validate(activationConfig, componentId, componentConfig);
        } catch (Exception e) {
            throw new ComponentConfigurationException("Invalid configuration provided", e);
        }
    }

}
