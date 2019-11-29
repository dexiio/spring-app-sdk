package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.exceptions.ComponentConfigurationException;
import io.dexi.service.handlers.AppContext;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/dexi/component/support/")
public class ComponentSupportController<T, U> extends AbstractAppController<T> {

    @Autowired
    private ComponentConfigurationHandler<T, U> componentConfigurationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void validateComponentConfiguration(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                                               @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                                               @RequestBody ObjectNode componentConfigJson) throws ComponentConfigurationException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.convertValue(componentConfigJson, componentConfigurationHandler.getComponentConfigClass(componentName));
        componentConfigurationHandler.validate(new AppContext<>(activationId, activationConfig, componentName, componentConfig));
    }

}
