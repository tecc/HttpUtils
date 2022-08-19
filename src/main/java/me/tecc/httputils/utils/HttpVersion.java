package me.tecc.httputils.utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public enum HttpVersion {
    V1_0("1.0"),
    V1_1("1.1"),
    V2("2");

    private final String m_representation;

    private static final Map<String, HttpVersion> versionByRepresentation = new HashMap<>();
    static {
        for (HttpVersion version : values()) {
            versionByRepresentation.put(version.representation(), version);
        }
    }

    public static HttpVersion getByRepresentation(String version) {
        return versionByRepresentation.get(version);
    }

    HttpVersion(@NotNull String representation) {
        if (representation == null) {
            throw new IllegalArgumentException("Representation may not be null");
        }
        this.m_representation = representation;
    }

    public String representation() {
        return this.m_representation;
    }


    @Override
    public String toString() {
        return this.m_representation;
    }
}
