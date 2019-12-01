package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.handlers.AppContext;
import io.dexi.service.handlers.ComponentConfigurationHandler;
import io.dexi.service.handlers.FileStorageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ConditionalOnBean(FileStorageHandler.class)
@RestController
@RequestMapping("/dexi/file/storage/")
public class FileStorageController<T, U> extends AbstractAppController<T> {

    @Autowired
    private FileStorageHandler<T, U> fileStorageHandler;

    @Autowired
    private ComponentConfigurationHandler<T, U> componentConfigurationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("write")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                      HttpServletRequest request) throws IOException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, componentConfigurationHandler.getComponentConfigClass(componentName));
        fileStorageHandler.write(new AppContext<>(activationId, activationConfig, componentName, componentConfig), request);
    }

}
