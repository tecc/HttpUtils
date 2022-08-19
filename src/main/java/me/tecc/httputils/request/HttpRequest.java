package me.tecc.httputils.request;

import me.tecc.httputils.utils.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;

/**
 * Interface for representing an HTTP request.
 * Comes with a default serialiser.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_requests">MDN Web Docs: HTTP Requests</a>
 */
public interface HttpRequest extends HttpSerialisable {
    /**
     * Gets the HTTP method of this request.
     * @return the HTTP method of this request
     * @see HttpMethod
     */
    @NotNull HttpMethod getMethod();

    /**
     * Gets the path of this request. May be a URL.
     * The path will include query parameters.
     *
     * @return The path of this request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_requests">MDN Web Docs: HTTP Requests</a>
     */
    @NotNull String getPath();

    /**
     * Gets the HTTP version of this request.
     * <p>
     * Note that the HTTP version may affect how the request is serialised by default.
     *
     * @return The HTTP version of this request.
     * @see HttpVersion
     */
    @NotNull HttpVersion getVersion();

    /**
     * Gets the HTTP headers of this request.
     * <p>
     * Note that the headers may affect how the request is serialised by default,
     * and that some headers will be overridden by the default serialiser.
     *
     * @return The headers of this request.
     * @see HttpHeaders
     */
    @NotNull HttpHeaders getHeaders();

    /**
     * Gets the body of this request.
     *
     * @return The body of this request
     */
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

        HttpHeaders headers = new HttpHeaders(getHeaders()); // Copy it for safety, as this serialiser may modify the headers

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
