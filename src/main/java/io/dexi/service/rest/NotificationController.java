package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.AppContext;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.components.NotificationAppComponent;
import io.dexi.service.components.TaskAppComponent;
import io.dexi.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(NotificationAppComponent.class)
@RestController
@RequestMapping("/dexi/notification/")
public class NotificationController<T, U> extends AbstractAppController<T> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Map<String, NotificationAppComponent<T, U>> notificationHandlers;

    @Autowired
    private JsonFactory jsonFactory;

    @PostMapping("invoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigString,
                      @RequestBody NotificationAppComponent.Notification notification) throws IOException {


        final NotificationAppComponent<T, U> notificationAppComponent = notificationHandlers.get(componentName);

        if (notificationAppComponent == null) {
            throw new NotFoundException("Notification handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigString, notificationAppComponent.getComponentConfigClass());

        notificationAppComponent.invoke(new AppContext<>(activationId, activationConfig, componentName, componentConfig), notification);

    }

}
