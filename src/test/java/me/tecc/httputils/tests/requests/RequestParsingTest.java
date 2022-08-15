package me.tecc.httputils.tests.requests;

import me.tecc.httputils.request.HttpRequest;
import me.tecc.httputils.request.HttpRequestParser;
import me.tecc.httputils.utils.HttpParseException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import static java.nio.charset.StandardCharsets.US_ASCII;

public class RequestParsingTest {
    @Test
    public void noBody() throws IOException, HttpParseException {
        HttpRequestParser parser = new HttpRequestParser(new ByteArrayInputStream("GET /example/path HTTP/1.1\r\n\r\n".getBytes(US_ASCII)));
        HttpRequest request = parser.parse();
    }
}
