package io.dexi.service.utils;

import org.apache.commons.lang.ArrayUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpUtils {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private static final int EOF = -1;

    public static long streamOutput(InputStream inputStream, HttpServletResponse response) throws IOException {
        return streamOutput(inputStream, DEFAULT_BUFFER_SIZE, response);
    }

    public static long streamOutput(InputStream inputStream, int bufferSize, HttpServletResponse response) throws IOException {
        byte[] buffer = new byte[bufferSize];
        long count = 0;
        int n = 0;

        try(OutputStream output = response.getOutputStream()) {
            while (EOF != (n = inputStream.read(buffer,0, buffer.length))) {
                output.write(buffer, 0, n);
                response.flushBuffer();
                buffer = new byte[buffer.length];
                count += n;
            }
        }

        return count;
    }

    public static long readInputChunks(InputStream inputStream, int chunkSize, ChunkHandler handler) throws IOException {

        byte[] buffer = new byte[chunkSize];

        long counter = 0;
        int offset = 0;

        while(true) {
            int b = inputStream.read();
            if (b == EOF || offset >= buffer.length) {

                byte[] chunk = offset >= buffer.length ? buffer : ArrayUtils.subarray(buffer, 0, offset);

                handler.handle(chunk);

                if (b == EOF) {
                    break;
                }
                offset = 0;
                buffer = new byte[buffer.length];
            }

            counter++;
            buffer[offset] = (byte) b;
            offset++;
        }

        return counter;
    }

    public interface ChunkHandler {

        void handle(byte[] chunk) throws IOException;
    }
}
