package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.dexi.client.DexiAuth;
import io.dexi.service.handlers.FileStorageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@ConditionalOnBean(FileStorageHandler.class)
@RestController
@RequestMapping("/dexi/file/storage/")
public class FileStorageController<T, U> extends AbstractAppController<T> {

    @Autowired
    private FileStorageHandler<T, U> fileStorageHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "write", method = RequestMethod.POST)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                       @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentId,
                       @RequestBody ObjectNode fileStoragePayloadJson,
                       HttpServletRequest request) {
        T activationConfig = requireConfig(activationId);
        U fileStoragePayload = objectMapper.convertValue(fileStoragePayloadJson, fileStorageHandler.getComponentConfigClass());
        fileStorageHandler.write(activationConfig, fileStoragePayload, request);
    }

}
