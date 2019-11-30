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

    private String nextOffset;

    public JsonResultStream(JsonFactory jsonFactory, OutputStream stream) throws IOException {
        this.jsonFactory = jsonFactory;
        generator = this.jsonFactory.createGenerator(stream, JsonEncoding.UTF8);
        generator.writeStartObject();
        generator.writeArrayFieldStart("rows");
    }


    @Override
    public void setNextOffset(String nextOffset) {
        this.nextOffset = nextOffset;
    }

    @Override
    public void append(Map<String,Object> row) throws IOException {
        generator.writeObject(row);
    }

    @Override
    public void close() throws IOException {
        generator.writeEndArray();
        generator.writeObjectField("nextOffset", nextOffset);
        generator.writeEndObject();
        generator.close();
    }
}
