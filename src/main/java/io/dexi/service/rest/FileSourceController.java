package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
import io.dexi.service.handlers.FileSourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ConditionalOnBean(FileSourceHandler.class)
@RestController
@RequestMapping("/dexi/file/source/")
public class FileSourceController<T, U> extends AbstractAppController<T> {

    @Autowired
    private FileSourceHandler<T, U> fileSourceHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("read")
    public void read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                     @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                     @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                     HttpServletResponse response) throws IOException {
        T activationConfig = requireConfig(activationId);
        U componentConfig = objectMapper.readValue(componentConfigJson, fileSourceHandler.getComponentConfigClass(componentName));
        fileSourceHandler.read(activationId, activationConfig, componentName, componentConfig, response);
    }

}
