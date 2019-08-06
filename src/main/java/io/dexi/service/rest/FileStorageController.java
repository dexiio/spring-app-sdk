package io.dexi.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dexi.client.DexiAuth;
import io.dexi.service.DexiPayloadHeaders;
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
    private ObjectMapper objectMapper;

    @RequestMapping(value = "write", method = RequestMethod.POST)
    // Ensure HTTP 204/205 is returned for successful calls of this method to avoid client (okhttp) choking with
    // "No content to map due to end-of-input" error attempting to parse the empty string as JSON.
    // TODO: make more generic fix by sub-classing appropriate Jackson converter used by okhttp
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void write(@RequestHeader(DexiAuth.HEADER_ACTIVATION) String activationId,
                      @RequestHeader(DexiAuth.HEADER_COMPONENT) String componentName,
                      @RequestHeader(DexiPayloadHeaders.CONFIGURATION) String componentConfigJson,
                      HttpServletRequest request) throws IOException {
        T activationConfig = requireConfig(activationId);
        U fileStoragePayload = objectMapper.readValue(componentConfigJson, fileStorageHandler.getComponentConfigClass(componentName));
        fileStorageHandler.write(activationConfig, fileStoragePayload, request);
    }

}
