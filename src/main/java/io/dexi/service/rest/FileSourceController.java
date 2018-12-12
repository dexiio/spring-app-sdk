package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.handlers.FileSourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@ConditionalOnBean(FileSourceHandler.class)
@RestController
@RequestMapping("/dexi/file/source/")
public class FileSourceController<T, U> extends AbstractAppController<T> {

    @Autowired
    private FileSourceHandler<T, U> fileSourceHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "read", method = RequestMethod.POST)
    public void read(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                     @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                     @RequestBody ObjectNode fileSourcePayloadJson,
                     HttpServletResponse response) {
        T activationConfig = requireConfig(activationId);
        U fileSourcePayload = objectMapper.convertValue(fileSourcePayloadJson, fileSourceHandler.getComponentConfigClass());
        fileSourceHandler.read(activationConfig, fileSourcePayload, response);
    }

}
