package io.dexi.service.utils;

import io.dexi.client.StreamingRequestBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestUtils {

    public static RequestBody createStreamingBody(HttpServletRequest request) throws IOException {
        final MediaType mediaType = MediaType.parse(request.getContentType());
        return new StreamingRequestBody(mediaType, request.getInputStream());
    }

    public static MultipartBody.Part createStreamingPart(HttpServletRequest request) throws IOException {
        final MediaType mediaType = MediaType.parse(request.getContentType());
        return MultipartBody.Part.create(new StreamingRequestBody(mediaType, request.getInputStream()));
    }
}
