package io.dexi.service.utils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class JsonResultStream implements ResultStream {

    private final JsonFactory jsonFactory;

    private final JsonGenerator generator;

    public JsonResultStream(JsonFactory jsonFactory, OutputStream stream) throws IOException {
        this.jsonFactory = jsonFactory;
        generator = this.jsonFactory.createGenerator(stream, JsonEncoding.UTF8);
        generator.writeStartObject();
        generator.writeArrayFieldStart("rows");
    }

    @Override
    public void append(Map<String,Object> row) throws IOException {
        generator.writeObject(row);

    }

    @Override
    public void close() throws IOException {
        generator.writeEndArray();
        generator.writeEndObject();
        generator.close();
    }
}
