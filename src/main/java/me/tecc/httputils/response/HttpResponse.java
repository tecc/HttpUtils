package me.tecc.httputils.response;

import me.tecc.httputils.utils.*;

import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;

public interface HttpResponse extends HttpSerialisable {
    HttpVersion getVersion();
    HttpStatus getStatus();

    HttpHeaders getHeaders();

    ByteBuffer getBuffer();

    default void serialise(Writer writer) throws IOException {
        writer
                .append("HTTP/").append(getVersion().representation())
                .append(" ");
        getStatus().serialise(writer);

    }
}
