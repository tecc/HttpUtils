package me.tecc.httputils.tests.requests;

import me.tecc.httputils.request.HttpRequest;
import me.tecc.httputils.request.HttpRequestParser;
import me.tecc.httputils.utils.HttpMethod;
import me.tecc.httputils.utils.HttpParseException;
import me.tecc.httputils.utils.HttpVersion;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestParsingTest {
    @Test
    public void noBody() throws IOException, HttpParseException {
        HttpRequestParser parser = new HttpRequestParser(new ByteArrayInputStream("GET /example/path HTTP/1.1\r\n\r\n".getBytes(US_ASCII)));
        HttpRequest request = parser.parse();

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals(HttpVersion.V1_1, request.getVersion());
        assertEquals("/example/path", request.getPath());
        assertEquals(0, request.getHeaders().count());
        assertEquals(0, request.getBody().limit());
    }
}
