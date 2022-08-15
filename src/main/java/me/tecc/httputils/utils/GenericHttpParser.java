package me.tecc.httputils.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public abstract class GenericHttpParser {
    protected BufferedReader reader;
    protected InputStream is;

    public GenericHttpParser(InputStream is) {
        this.reader = new BufferedReader(new InputStreamReader(is));
        this.is = is;

    }

    public HttpMethod readMethod() throws IOException, HttpParseException {
        String read = readUntilDelimiter(' ');
        if (read == null) {
            throw new HttpParseException("Method name was not terminated properly");
        }
        HttpMethod found = HttpMethod.valueOfSafe(read);
        if (found == null) {
            throw new HttpParseException("No such HTTP method: " + read);
        }
        return found;
    }

    public HttpVersion readVersion(boolean prefixedWithHttp) throws IOException, HttpParseException {
        String read = readUntilNewline();
        if (read == null) {
            throw new HttpParseException("HTTP version was not terminated properly");
        }
        if (prefixedWithHttp) {
            if (!read.startsWith("HTTP/")) throw new HttpParseException("Expected HTTP/ prefix for version");
            read = read.substring(5);
        }
        HttpVersion version = HttpVersion.getByRepresentation(read);
        if (version == null) {
            throw new HttpParseException("Unsupported or invalid HTTP version: " + read);
        }
        return version;
    }

    public String readPath() throws IOException, HttpParseException {
        String read = readUntilDelimiter(' ');
        if (read == null) {
            throw new HttpParseException("Path was not terminated properly");
        }
        if (read.isEmpty()) {
            throw new HttpParseException("Path is empty");
        }
        return read;
    }

    protected HttpHeaders readHeaders() throws IOException, HttpParseException {
        HttpHeaders headers = new HttpHeaders();
        while (readHeaderTo(headers)) { /* does nothing */ }
        return headers;
    }

    // returns whether it could read a header
    protected boolean readHeaderTo(@NotNull HttpHeaders headers) throws IOException, HttpParseException {
        String line = readUntilNewline();
        if (line == null) {
            throw new HttpParseException("No line");
        }
        if (line.isEmpty()) {
            return false;
        }
        StringBuilder name = new StringBuilder();
        StringBuilder value = new StringBuilder();
        boolean inValue = false;
        for (char c : line.toCharArray()) {
            if (inValue) {
                value.append(c);
                continue;
            }

            if (c == ':') {
                inValue = true;
                continue;
            }
            name.append(c);
        }
        if (!inValue) {
            throw new HttpParseException("Malformed header line");
        }

        headers.put(name.toString().trim(), value.toString().trim());
        return true;
    }

    protected String readUntilDelimiter(char delimiter) throws IOException {
        StringBuilder builder = new StringBuilder();
        int ci;
        while ((ci = reader.read()) != -1) {
            char c = (char) ci;
            if (c == delimiter) {
                return builder.toString();
            }
            builder.append(c);
        }
        return null;
    }
    protected String readUntilNewline() throws IOException {
        StringBuilder builder = new StringBuilder();
        int ci;
        boolean lastWasCR = false;
        while ((ci = reader.read()) != -1) {
            char c = (char) ci;
            switch (c) {
                case '\r':
                    lastWasCR = true;
                    break;
                case '\n':
                    if (lastWasCR) {
                        return builder.toString().trim();
                    }
                default:
                    if (lastWasCR) {
                        builder.append('\r');
                        lastWasCR = false;
                    }
                    builder.append(c);

                    break;
            }
        }
        return null;
    }

    public byte[] readBody(int size) throws IOException, HttpParseException {
        if (size < 1) {
            return new byte[0];
        }
        byte[] bytes = new byte[size];
        int actuallyRead = is.read(bytes, 0, size);
        if (actuallyRead != size) {
            throw new HttpParseException("Unexpected body length (expected: " +  size + ", got: " + actuallyRead + ")");
        }
        return bytes;
    }
}
