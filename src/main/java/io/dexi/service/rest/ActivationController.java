package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.exceptions.ActivationException;
import io.dexi.service.handlers.ActivationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ConditionalOnBean(ActivationHandler.class)
@RestController
@RequestMapping("/dexi/activations/")
public class ActivationController<T> {

    @Autowired
    private ActivationHandler<T> activationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void validateActivation(@RequestBody ObjectNode activationConfigJson) throws ActivationException {
        T activationConfig = objectMapper.convertValue(activationConfigJson, activationHandler.getActivationConfigClass());
        activationHandler.<T>validate(activationConfig);
    }

    @PostMapping("activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@RequestBody ObjectNode activationConfigJson,
                         @RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId) throws ActivationException {
        T activationConfig = objectMapper.convertValue(activationConfigJson, activationHandler.getActivationConfigClass());
        activationHandler.activate(activationId, activationConfig);
    }

    @PostMapping("deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId) throws ActivationException {
        activationHandler.deactivate(activationId);
    }

}
