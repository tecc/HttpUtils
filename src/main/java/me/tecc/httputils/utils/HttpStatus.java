package me.tecc.httputils.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class HttpStatus implements HttpSerialisable {
    private int m_code;
    private String m_message;
    private static final Map<Integer, HttpStatus> BUILTINS = new HashMap<>();

    public static final HttpStatus OK = new HttpStatus(200, "OK");

    public HttpStatus(int code, String message) {
        this(code, message, false);
    }
    private HttpStatus(int code, String message, boolean builtin) {
        this.m_code = code;
        this.m_message = message;
        if (builtin) {
            BUILTINS.put(code, this);
        }
    }

    public HttpStatus(HttpStatus status) {
        this(status.m_code, status.m_message);
    }


    public int getCode() {
        return this.m_code;
    }
    public String getMessage() {
        return this.m_message;
    }

    @Override
    public void serialise(Writer writer) throws IOException {
        writer.append(String.valueOf(this.getCode())).append(" ").append(this.getMessage());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            return this.getCode() == (Integer) obj;
        }
        if (obj instanceof HttpStatus) {
            return this.getCode() == ((HttpStatus) obj).getCode();
        }
        return false;
    }
}
