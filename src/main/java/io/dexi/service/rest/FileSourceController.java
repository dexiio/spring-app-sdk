package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.AppContext;
import io.dexi.service.components.FileSourceAppComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(FileSourceAppComponent.class)
@RestController
@RequestMapping("/dexi/file/source/")
public class FileSourceController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, FileSourceAppComponent<T, U>> fileSourceHandlers;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("read")
    public void read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                     @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                     @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                     HttpServletResponse response) throws IOException {

        final FileSourceAppComponent<T, U> fileSourceHandler = fileSourceHandlers.get(componentName);
        if (fileSourceHandler == null) {
            throw new NotFoundException("File source handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, fileSourceHandler.getComponentConfigClass());
        fileSourceHandler.read(new AppContext<>(activationId, activationConfig, componentName, componentConfig), response);
    }

}
