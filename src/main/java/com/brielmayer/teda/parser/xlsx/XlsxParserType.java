package com.brielmayer.teda.parser.xlsx;

import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.parser.Parser;
import com.brielmayer.teda.parser.ParserType;

public final class XlsxParserType implements ParserType {

    @Override
    public boolean handles(final DocumentType documentType) {
        return documentType == DocumentType.EXCEL;
    }

    @Override
    public Parser createParser() {
        return new XlsxDocumentParser();
    }
}
