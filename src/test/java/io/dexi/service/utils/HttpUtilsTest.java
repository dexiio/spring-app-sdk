package io.dexi.service.utils;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class HttpUtilsTest {
    private static final int KB = 1024;

    private byte[] createByteArray() {
        int size = KB * 8;
        byte[] content = new byte[size];
        for(int i = 0; i < size; i++) {
            content[i] = (byte) ((Math.random() * 255) + Byte.MIN_VALUE);
        }

        return content;
    }

    @Test
    public void can_chunk_up_input_stream() throws IOException {
        byte[] content = createByteArray();

        ByteArrayInputStream input = new ByteArrayInputStream(content);
        AtomicInteger counter = new AtomicInteger();

        long read = HttpUtils.readInputChunks(input, KB, chunk -> {
            int count = counter.getAndIncrement();
            assertEquals(KB, chunk.length);
            assertEquals(content[count * KB], chunk[0]);
            assertEquals(content[(count * KB) + 1], chunk[1]);
            assertEquals(content[((count + 1) * KB) - 1], chunk[chunk.length - 1]);
        });

        assertEquals(8 * KB, read);
        assertEquals(8, counter.get());
    }

    @Test
    public void when_handler_throws_chunking_stops() throws IOException {
        byte[] content = createByteArray();

        ByteArrayInputStream input = new ByteArrayInputStream(content);
        AtomicInteger counter = new AtomicInteger();

        try {
            HttpUtils.readInputChunks(input, KB, chunk -> {
                counter.getAndIncrement();
                throw new IOException("Failed!");
            });
            throw new AssertionError("Did not throw");
        } catch (IOException ex) {

        }
        assertEquals(1, counter.get());
    }

    @Test
    public void can_stream_output_while_flushing() throws IOException {
        byte[] content = createByteArray();

        ByteArrayInputStream input = new ByteArrayInputStream(content);
        final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        final ServletOutputStream output = Mockito.mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(output);

        final long written = HttpUtils.streamOutput(input, KB, response);

        assertEquals(8L * KB, written);
        verify(output, times(1)).write(eq(Arrays.copyOfRange(content, KB * 2, KB * 3)), eq(0), eq(KB));
        verify(output, times(8)).write(any(byte[].class), eq(0), anyInt());

        verify(response, times(8)).flushBuffer();
    }

}