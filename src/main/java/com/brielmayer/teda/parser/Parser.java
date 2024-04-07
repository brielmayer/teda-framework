package com.brielmayer.teda.parser;

import com.brielmayer.teda.model.Document;

import java.io.InputStream;

@FunctionalInterface
public interface Parser {

    String TEDA = "#Teda";
    String TABLE = "#Table";

    String COCKPIT = "Cockpit";

    Document parse(InputStream inputStream);
}
