package io.dexi.service.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.AppContext;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.components.FileParserAppComponent;
import io.dexi.service.components.FileStorageAppComponent;
import io.dexi.service.exceptions.NotFoundException;
import io.dexi.service.utils.JsonResultStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@ConditionalOnBean(FileParserAppComponent.class)
@RestController
@RequestMapping("/dexi/file/")
public class FileParserController<T, U> extends AbstractAppController<T> {

    @Autowired
    private Map<String, FileParserAppComponent<T, U>> fileStorageHandlers;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JsonFactory jsonFactory;

    @PostMapping("parse")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        final FileParserAppComponent<T, U> fileParserAppComponent = fileStorageHandlers.get(componentName);
        if (fileParserAppComponent == null) {
            throw new NotFoundException("File parser handler not found for component: " + componentName);
        }

        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, fileParserAppComponent.getComponentConfigClass());


        try (final JsonResultStream resultStream = new JsonResultStream(jsonFactory, response.getOutputStream())) {
            fileParserAppComponent.parse(new AppContext<>(activationId, activationConfig, componentName, componentConfig), request, resultStream);
        }
    }

}
