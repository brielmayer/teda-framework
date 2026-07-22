package com.brielmayer.teda.parser.ods;

import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.parser.Parser;
import com.brielmayer.teda.parser.ParserType;

public final class OdsParserType implements ParserType {

    @Override
    public boolean handles(final DocumentType documentType) {
        return documentType == DocumentType.OPEN_DOCUMENT_SPREADSHEET;
    }

    @Override
    public Parser createParser() {
        return new OdsDocumentParser();
    }
}
