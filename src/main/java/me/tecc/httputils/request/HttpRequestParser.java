package me.tecc.httputils.request;

import me.tecc.httputils.utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class HttpRequestParser extends GenericHttpParser {
    public HttpRequestParser(InputStream is) {
        super(is);
    }

    public HttpRequest parse() throws IOException, HttpParseException {
        HttpMethod method = readMethod();
        String path = readPath();
        HttpVersion version = readVersion(true);
        HttpHeaders headers = readHeaders();


        int contentLength = 0;
        if (headers.has(HttpHeaders.CONTENT_LENGTH)) {
            String contentLengthStr = headers.get(HttpHeaders.CONTENT_LENGTH);
            try {
                contentLength = Integer.parseInt(contentLengthStr);
            } catch (Exception e) {
                throw new HttpParseException("Content length header is invalid", e);
            }
        }
        byte[] body = readBody(contentLength);

        return HttpUtil.create(method, version, path, headers, body);
    }
}
