package me.tecc.httputils.response;

import me.tecc.httputils.utils.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import java.util.function.Function;

public class HttpResponseBuilder implements HttpResponse {
    private @NotNull HttpVersion m_version = HttpVersion.V1_1;
    private @NotNull HttpStatus m_status = HttpStatus.OK;
    private @NotNull HttpHeaders m_headers = new HttpHeaders();
    private @NotNull ByteBuffer m_body = HttpUtil.EMPTY_BUFFER;

    public HttpResponseBuilder() {
    }

    public HttpResponseBuilder(HttpResponse request) {
        this.m_version = request.getVersion();
        this.m_status = new HttpStatus(request.getStatus());
        this.m_headers = new HttpHeaders(this.m_headers);

        // Copy body
        ByteBuffer body = request.getBody();
        if (body != null && body.limit() > 0) {
            this.m_body = HttpUtil.copyBuffer(body);
        } else {
            this.m_body = HttpUtil.EMPTY_BUFFER;
        }
    }

    @Contract("_ -> this")
    public HttpResponseBuilder version(@NotNull HttpVersion version) {
        if (version == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        this.m_version = version;
        return this;
    }

    @Override
    public @NotNull HttpVersion getVersion() {
        return this.m_version;
    }

    public HttpResponseBuilder status(@NotNull HttpStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        this.m_status = new HttpStatus(status);
        return this;
    }
    @Override
    public @NotNull HttpStatus getStatus() {
        return this.m_status;
    }

    @Contract("_ -> this")
    public HttpResponseBuilder processHeaders(Consumer<@NotNull HttpHeaders> processor) {
        processor.accept(this.m_headers);
        return this;
    }

    @Contract("_ -> this")
    public HttpResponseBuilder processHeaders(Function<@NotNull HttpHeaders, HttpHeaders> processor) {
        HttpHeaders processed = processor.apply(this.m_headers);
        if (processed != null) {
            this.m_headers = processed;
        }
        return this;
    }

    @Contract("_ -> this")
    public HttpResponseBuilder headers(HttpHeaders headers) {
        if (headers == null) {
            this.m_headers.clear();
        } else {
            this.m_headers = headers;
        }
        return this;
    }

    @Override
    public @NotNull HttpHeaders getHeaders() {
        return this.m_headers;
    }
    @Contract("_ -> this")
    public HttpResponseBuilder body(byte @Nullable [] body) {
        if (body != null) {
            this.m_body = ByteBuffer.wrap(body);
        } else {
            this.m_body = HttpUtil.EMPTY_BUFFER;
        }
        return this;
    }

    @Override
    public @NotNull ByteBuffer getBody() {
        return this.m_body;
    }
}
