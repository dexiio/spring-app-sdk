package io.dexi.service.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implements a streaming reader for JSON payloads that have the following structure:
 * {
 *     "rows": [
 *          {"id": 1, ...},
 *          ...
 *     ],
 *     "nextOffset": 1234
 * }
 */
public class JsonRowStream implements RowStream {

    private final JsonFactory jsonFactory;

    private final ObjectMapper objectMapper;

    private final JsonParser parser;

    public JsonRowStream(JsonFactory jsonFactory, ObjectMapper objectMapper, InputStream stream) throws IOException {
        this.jsonFactory = jsonFactory;
        this.objectMapper = objectMapper;
        parser = this.jsonFactory.createParser(stream);

        readUntilRowStart();
    }

    /**
     * Move cursor to the start of the array
     * @throws IOException
     */
    private void readUntilRowStart() throws IOException {
        while(parser.nextToken() != JsonToken.START_ARRAY) {
            //Do nothing
        }
        parser.nextToken();
    }

    @Override
    public boolean hasNext() throws IOException {
        return parser.currentToken() == JsonToken.START_OBJECT;
    }

    @Override
    public Map<String,Object> next() throws IOException {
        final Map<String,Object> out = objectMapper.readValue(parser, Map.class);
        parser.nextToken();
        return out;
    }

    @Override
    public void close() {
        try {
            parser.close();
        } catch (IOException e) {
            //Ignored
        }
    }
}
