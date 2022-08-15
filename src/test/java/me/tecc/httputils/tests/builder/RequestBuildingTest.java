package me.tecc.httputils.tests.builder;

import me.tecc.httputils.request.HttpRequestBuilder;
import me.tecc.httputils.utils.HttpMethod;
import me.tecc.httputils.utils.HttpVersion;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RequestBuildingTest {
    @Test
    public void test() {
        HttpRequestBuilder builder = new HttpRequestBuilder()
                .method(HttpMethod.GET)
                .version(HttpVersion.V1_1)
                .path("/example/path");

        assertEquals(HttpMethod.GET, builder.getMethod());
        assertEquals(HttpVersion.V1_1, builder.getVersion());
        assertEquals("/example/path", builder.getPath());
        assertNotNull(builder.getHeaders());
        assertEquals(0, builder.getHeaders().count());
        assertNotNull(builder.getBody());
        assertEquals(0, builder.getBody().length);
    }
}
