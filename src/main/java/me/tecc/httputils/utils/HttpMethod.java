package me.tecc.httputils.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP methods enumerator.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods">MDN Web Docs: HTTP request methods</a>
 */
public enum HttpMethod {
    GET(false, true),
    HEAD(false, false),
    POST(true, true),
    PUT(true, true),
    DELETE(true, true),
    CONNECT(false, true),
    OPTIONS(false, true),
    TRACE(false, false),
    PATCH(true, true);

    private final boolean m_requestShouldHaveBody, m_responseShouldHaveBody;

    /*private static final Map<String, HttpMethod> m_index = new HashMap<>();
    static {
        for (HttpMethod method : HttpMethod.values()) {
            m_index.put(method.name(), method);
        }
    }*/

    /*public static HttpMethod getByName(String name) {
        return m_index.get(name);
    }*/

    HttpMethod(boolean requestShouldHaveBody, boolean responseShouldHaveBody) {
        this.m_requestShouldHaveBody = requestShouldHaveBody;
        this.m_responseShouldHaveBody = responseShouldHaveBody;
    }

    public static HttpMethod valueOfSafe(String s) {
        try {
            return valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Whether the request can be expected to have a body.
     *
     * @return true if it is expected; false otherwise
     */
    public boolean requestShouldHaveBody() {
        return this.m_requestShouldHaveBody;
    }

    /**
     * Whether the response can be expected to have a body.
     *
     * @return true if it is expected; false otherwise
     */
    public boolean responseShouldHaveBody() {
        return this.m_responseShouldHaveBody;
    }
}
