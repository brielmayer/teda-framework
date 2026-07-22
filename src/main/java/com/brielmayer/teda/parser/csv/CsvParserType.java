package com.brielmayer.teda.parser.csv;

import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.parser.Parser;
import com.brielmayer.teda.parser.ParserType;

public final class CsvParserType implements ParserType {

    @Override
    public boolean handles(final DocumentType documentType) {
        return documentType == DocumentType.CSV;
    }

    @Override
    public Parser createParser() {
        return new CsvDocumentParser();
    }
}
