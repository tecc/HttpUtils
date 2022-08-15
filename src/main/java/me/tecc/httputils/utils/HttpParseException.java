package me.tecc.httputils.utils;

public class HttpParseException extends Exception {
    public HttpParseException() {
        super();
    }

    public HttpParseException(String message) {
        super(message);
    }

    public HttpParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpParseException(Throwable cause) {
        super(cause);
    }

    protected HttpParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
