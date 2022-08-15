package me.tecc.httputils.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders implements HttpSerialisable {
    public static final String CONTENT_LENGTH = "Content-Length";
    private final Map<String, String> m_headers;

    public HttpHeaders() {
        this(new HashMap<>());
    }

    public HttpHeaders(Map<String, String> headers) {
        this.m_headers = headers;
    }

    public HttpHeaders(HttpHeaders other) {
        this.m_headers = new HashMap<>(other.m_headers);
    }

    public int count() {
        return this.m_headers.size();
    }

    public Collection<String> names() {
        return this.m_headers.keySet();
    }

    public boolean has(String name) {
        return this.m_headers.containsKey(name);
    }

    public String get(String name) {
        return this.m_headers.get(name);
    }

    public void put(String name, String value) {
        this.m_headers.put(name, value);
    }

    /**
     * Serialise these headers to a writer.
     * <p>
     * This method presumes it is on its own line,
     * and will write newlines at the end of each header (including the last header).
     *
     * @param writer The writer to serialise to
     * @throws IOException In the case of an I/O error.
     */
    @Override
    public void serialise(Writer writer) throws IOException {
        for (String key : names()) {
            String value = get(key);
            writer
                    .append(key)
                    .append(": ").append(value)
                    .append("\r\n");
        }
    }
}
