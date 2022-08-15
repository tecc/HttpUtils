package me.tecc.httputils.request;

import me.tecc.httputils.utils.HttpHeaders;
import me.tecc.httputils.utils.HttpMethod;
import me.tecc.httputils.utils.HttpVersion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class HttpRequestBuilder implements HttpRequest {
    private @NotNull HttpVersion m_version = HttpVersion.V1_1;
    private @NotNull HttpMethod m_method = HttpMethod.GET;
    private @NotNull String m_path = "/";
    private @NotNull HttpHeaders m_headers = new HttpHeaders();
    private byte @NotNull [] m_body = new byte[0];

    public HttpRequestBuilder() {
    }

    public HttpRequestBuilder(HttpRequest request) {
        this.m_version = request.getVersion();
        this.m_method = request.getMethod();
        this.m_path = request.getPath();
        this.m_headers = new HttpHeaders(this.m_headers);

        // Copy body
        byte[] body = request.getBody();
        if (body != null && body.length > 0) {
            this.m_body = new byte[body.length];
            System.arraycopy(body, 0, this.m_body, 0, body.length);
        }

    }

    @Contract("_ -> this")
    public HttpRequestBuilder version(@NotNull HttpVersion version) {
        if (version == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }
        this.m_version = version;
        return this;
    }

    @Override
    public @NotNull HttpVersion getVersion() {
        return this.m_version;
    }

    @Contract("_ -> this")
    public HttpRequestBuilder processHeaders(Consumer<@NotNull HttpHeaders> processor) {
        processor.accept(this.m_headers);
        return this;
    }

    @Contract("_ -> this")
    public HttpRequestBuilder processHeaders(Function<@NotNull HttpHeaders, HttpHeaders> processor) {
        HttpHeaders processed = processor.apply(this.m_headers);
        if (processed != null) {
            this.m_headers = processed;
        }
        return this;
    }

    @Contract("_ -> this")
    public HttpRequestBuilder headers(HttpHeaders headers) {
        this.m_headers = headers;
        return this;
    }

    @Override
    public @NotNull HttpHeaders getHeaders() {
        return this.m_headers;
    }

    @Contract("_ -> this")
    public HttpRequestBuilder method(HttpMethod method) {
        this.m_method = method;
        return this;
    }

    @Override
    public @NotNull HttpMethod getMethod() {
        return this.m_method;
    }

    @Contract("_ -> this")
    public HttpRequestBuilder path(String path) {
        if (path == null) path = "/";
        this.m_path = path;
        return this;
    }

    @Override
    public @NotNull String getPath() {
        return this.m_path;
    }

    @Contract("_ -> this")
    public HttpRequestBuilder body(byte @Nullable [] body) {
        if (body != null) {
            this.m_body = body;
        } else {
            this.m_body = new byte[0];
        }
        return this;
    }

    @Override
    public byte @NotNull [] getBody() {
        return this.m_body;
    }
}
