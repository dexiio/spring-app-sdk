package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.AppContext;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.components.FileFilterAppComponent;
import io.dexi.service.components.FileStorageAppComponent;
import io.dexi.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(FileFilterAppComponent.class)
@RestController
@RequestMapping("/dexi/file/")
public class FileFilterController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, FileFilterAppComponent<T, U>> fileStorageHandlers;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("filter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        final FileFilterAppComponent<T, U> fileFilterAppComponent = fileStorageHandlers.get(componentName);
        if (fileFilterAppComponent == null) {
            throw new NotFoundException("File filter handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, fileFilterAppComponent.getComponentConfigClass());
        fileFilterAppComponent.filter(new AppContext<>(activationId, activationConfig, componentName, componentConfig), request, response);
    }

}
