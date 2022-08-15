package me.tecc.httputils.utils;

import me.tecc.httputils.request.HttpRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HttpUtil {
    public static HttpRequest makeImmutable(@NotNull HttpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request may not be null");
        }

        HttpMethod method = request.getMethod();
        String path = request.getPath();
        HttpVersion version = request.getVersion();
        HttpHeaders headers = new HttpHeaders(request.getHeaders());

        byte[] sourceBody = request.getBody();
        byte[] body;
        if (sourceBody.length > 0) {
            body = new byte[sourceBody.length];
            System.arraycopy(sourceBody, 0, body, 0, sourceBody.length);
        } else {
            body = new byte[0];
        }

        return create(method, version, path, headers, body);
    }

    public static HttpRequest create(HttpMethod method, HttpVersion version, String path, HttpHeaders headers, byte[] body) {
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
            public byte @NotNull [] getBody() {
                return body;
            }
        };
    }
}
