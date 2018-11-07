package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.config.ActivationConfig;
import io.dexi.service.config.ComponentConfig;
import io.dexi.service.exceptions.ComponentConfigurationException;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dexi/component/support/")
public class ComponentSupportController<T extends ActivationConfig, U extends ComponentConfig> extends AbstractAppController<T> {

    @Autowired
    private ComponentConfigurationHandler<T, U> componentConfigurationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    public void validateComponentConfiguration(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                                               @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                                               @RequestBody ObjectNode componentConfigJson) throws ComponentConfigurationException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(componentConfigJson, componentConfigurationHandler.getComponentConfigClass());
        componentConfigurationHandler.validate(activationConfig, componentId, componentConfig);
    }

}
