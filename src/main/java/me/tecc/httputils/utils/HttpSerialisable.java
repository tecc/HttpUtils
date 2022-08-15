package me.tecc.httputils.utils;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public interface HttpSerialisable {
    /**
     * Serialise this to a writer.
     *
     * @param writer The writer to serialise to
     * @throws IOException In the case of an I/O error
     */
    @Contract(pure = true)
    void serialise(Writer writer) throws IOException;
    default String serialise() {
        StringWriter writer = new StringWriter();
        try {
            serialise(writer);
        } catch (IOException e) {
            throw new RuntimeException("Error whilst serialising HTTP object", e);
        }
        return writer.toString();
    }
}
