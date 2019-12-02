package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.exceptions.ComponentConfigurationException;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.handlers.AppContext;
import io.dexi.service.handlers.ComponentValidatesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dexi/component/support/")
public class ComponentSupportController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, ComponentValidatesConfiguration<T, U>> componentConfigurationHandlers;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void validateComponentConfiguration(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                                               @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                                               @RequestBody ObjectNode componentConfigJson) throws ComponentConfigurationException {

        final ComponentValidatesConfiguration<T, U> componentConfigurationHandler = componentConfigurationHandlers.get(componentName);
        if (componentConfigurationHandler == null) {
            throw new NotFoundException("Configuration handler not found for component: " + componentName);
        }
        T activationConfig = requireConfig(activationId);

        U componentConfig = objectMapper.convertValue(componentConfigJson, componentConfigurationHandler.getComponentConfigClass());
        componentConfigurationHandler.validate(new AppContext<>(activationId, activationConfig, componentName, componentConfig));
    }

}
