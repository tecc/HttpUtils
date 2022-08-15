package me.tecc.httputils.tests.requests;

import me.tecc.httputils.request.HttpRequestBuilder;
import me.tecc.httputils.utils.HttpMethod;
import me.tecc.httputils.utils.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestSerialisationTest {
    @Test
    public void noBody() {
        HttpRequestBuilder builder = new HttpRequestBuilder()
                .method(HttpMethod.GET)
                .version(HttpVersion.V1_1)
                .path("/example/path");

        String serialised = builder.serialise();
        assertEquals(String.join("",
                "GET /example/path HTTP/1.1\r\n\r\n"
        ), serialised);
    }
}
