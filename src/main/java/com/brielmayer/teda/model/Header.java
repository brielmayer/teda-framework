package com.brielmayer.teda.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Header {

    private static final String PRIMARY_KEY_PREFIX = "#";

    private final String name;
    private final boolean isPrimaryKey;

    public static Header fromName(String name) {
        if (name.startsWith(PRIMARY_KEY_PREFIX)) {
            return new Header(name.substring(1), true);
        } else {
            return new Header(name, false);
        }
    }
}
