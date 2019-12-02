package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.AppContext;
import io.dexi.service.components.FileStorageAppComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(FileStorageAppComponent.class)
@RestController
@RequestMapping("/dexi/file/storage/")
public class FileStorageController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, FileStorageAppComponent<T, U>> fileStorageHandlers;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("write")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                      HttpServletRequest request) throws IOException {

        final FileStorageAppComponent<T, U> fileStorageHandler = fileStorageHandlers.get(componentName);
        if (fileStorageHandler == null) {
            throw new NotFoundException("File storage handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, fileStorageHandler.getComponentConfigClass());
        fileStorageHandler.write(new AppContext<>(activationId, activationConfig, componentName, componentConfig), request);
    }

}
