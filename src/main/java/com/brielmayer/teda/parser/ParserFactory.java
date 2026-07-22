package com.brielmayer.teda.parser;

import java.util.ServiceLoader;

import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.DocumentType;

public final class ParserFactory {

    private ParserFactory() {}

    public static Parser getParser(final DocumentType documentType) {
        final ServiceLoader<ParserType> loader = ServiceLoader.load(ParserType.class);
        for (final ParserType parserType : loader) {
            if (parserType.handles(documentType)) {
                return parserType.createParser();
            }
        }
        throw TedaException.builder()
                .appendMessage("No parser found for document type \"%s\"", documentType)
                .build();
    }
}
