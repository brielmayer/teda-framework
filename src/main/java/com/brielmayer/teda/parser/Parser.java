package com.brielmayer.teda.parser;

import java.io.InputStream;

import com.brielmayer.teda.model.Document;

@FunctionalInterface
public interface Parser {

    String TEDA = "#Teda";
    String TABLE = "#Table";

    String COCKPIT = "Cockpit";

    Document parse(InputStream inputStream);
}
