package me.tecc.httputils.utils;

import me.tecc.httputils.request.HttpRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public class HttpUtil {

    public static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);

    public static ByteBuffer copyBuffer(@NotNull ByteBuffer buffer) {
        byte[] buf = new byte[buffer.limit()];
        buffer.position(0);
        buffer.get(buf, 0, buffer.limit());
        return ByteBuffer.wrap(buf);
    }

    public static HttpRequest makeImmutable(@NotNull HttpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request may not be null");
        }

        HttpMethod method = request.getMethod();
        String path = request.getPath();
        HttpVersion version = request.getVersion();
        HttpHeaders headers = new HttpHeaders(request.getHeaders());

        ByteBuffer sourceBody = request.getBody();

        return create(method, version, path, headers, copyBuffer(sourceBody));
    }

    public static HttpRequest create(HttpMethod method, HttpVersion version, String path, HttpHeaders headers, byte[] body) {
        return create(method, version, path, headers, ByteBuffer.wrap(body));
    }

    public static HttpRequest create(HttpMethod method, HttpVersion version, String path, HttpHeaders headers, ByteBuffer body) {
        return new HttpRequest() {
            @Override
            public @NotNull HttpMethod getMethod() {
                return method;
            }

            @Override
            public @NotNull String getPath() {
                return path;
            }

            @Override
            public @NotNull HttpVersion getVersion() {
                return version;
            }

            @Override
            public @NotNull HttpHeaders getHeaders() {
                return headers;
            }

            @Override
            public @NotNull ByteBuffer getBody() {
                return body;
            }
        };
    }
}
