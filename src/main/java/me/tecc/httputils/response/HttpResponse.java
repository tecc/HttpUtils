package me.tecc.httputils.response;

import me.tecc.httputils.utils.*;

import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;

public interface HttpResponse extends HttpSerialisable {
    @NotNull HttpVersion getVersion();
    @NotNull HttpStatus getStatus();
    @NotNull HttpHeaders getHeaders();
    @NotNull ByteBuffer getBody();

    default void serialise(Writer writer) throws IOException {
        writer
                .append("HTTP/").append(getVersion().representation())
                .append(" ");
        getStatus().serialise(writer);
        writer.append("\r\n");
        getHeaders().serialise(writer);

        writer.append("\r\n");
        ByteBuffer body = getBody();
        if (body != null && body.limit() > 0) {
            HttpUtil.writeBuffer(body, writer);
        }
    }
}
