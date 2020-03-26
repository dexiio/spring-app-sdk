package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.AppContext;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.components.TaskAppComponent;
import io.dexi.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(TaskAppComponent.class)
@RestController
@RequestMapping("/dexi/task/")
public class TaskController<T, U> extends AbstractAppController<T> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Map<String, TaskAppComponent<T, U>> taskHandlers;

    @Autowired
    private JsonFactory jsonFactory;

    @PostMapping("invoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigString,
                      @RequestBody TaskAppComponent.Task task) throws IOException {


        final TaskAppComponent<T, U> taskAppComponent = taskHandlers.get(componentName);

        if (taskAppComponent == null) {
            throw new NotFoundException("Task handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigString, taskAppComponent.getComponentConfigClass());

        taskAppComponent.invoke(new AppContext<>(activationId, activationConfig, componentName, componentConfig), task);

    }

}
