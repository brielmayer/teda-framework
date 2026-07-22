package com.brielmayer.teda.parser;

import com.brielmayer.teda.model.DocumentType;

/**
 * SPI for spreadsheet parsers. Register implementations via
 * {@code META-INF/services/com.brielmayer.teda.parser.ParserType} to add
 * support for a new {@link DocumentType} without modifying the framework.
 */
public interface ParserType {

    boolean handles(DocumentType documentType);

    Parser createParser();
}
