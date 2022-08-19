package me.tecc.httputils.request;

import me.tecc.httputils.utils.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;

/**
 * Interface for representing an HTTP request.
 * Comes with a default serialiser.
 */
public interface HttpRequest extends HttpSerialisable {
    @NotNull HttpMethod getMethod();

    @NotNull String getPath();

    @NotNull HttpVersion getVersion();

    @NotNull HttpHeaders getHeaders();

    @NotNull ByteBuffer getBody();

    /**
     * Serialises this request as per HTTP specifications.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_requests">MDN Web Docs: HTTP Requests</a>
     *
     * @param writer The writer to serialise this request to
     * @throws IOException In the case of an I/O error
     */
    default void serialise(Writer writer) throws IOException {
        HttpMethod method = getMethod();
        // Write start line
        writer
                .append(method.name())
                .append(" ").append(getPath())
                .append(" HTTP/").append(getVersion().toString());

        HttpHeaders headers = new HttpHeaders(getHeaders()); // Copy it for safety

        boolean hasTransferEncoding = headers.has("Transfer-Encoding");
        ByteBuffer body = getBody();
        if (!hasTransferEncoding) {
            if (body == null || body.limit() < 1) {
                // Per https://httpwg.org/specs/rfc7230.html#header.content-length,
                // if the method does not anticipate a body, we should not send a Content-Length header
                if (method.requestShouldHaveBody()) {
                    headers.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(0));
                }
            } else {
                headers.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.limit()));
            }
        }

        writer.append("\r\n");
        headers.serialise(writer);

        writer.append("\r\n"); // The empty line is necessary, according to https://computersciencewiki.org/index.php/HTTP_or_HTTP/2
        if (body != null && body.limit() > 0) {
            HttpUtil.writeBuffer(body, writer);
        }
    }

}
