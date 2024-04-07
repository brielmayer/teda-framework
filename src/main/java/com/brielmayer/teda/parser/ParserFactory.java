package com.brielmayer.teda.parser;

import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.parser.ods.OdsDocumentParser;
import com.brielmayer.teda.parser.xlsx.XlsxDocumentParser;

public class ParserFactory {

    public static Parser getParser(DocumentType documentType) {
        switch (documentType) {
            case EXCEL:
                return new XlsxDocumentParser();
            case OPEN_DOCUMENT_SPREADSHEET:
                return new OdsDocumentParser();
            default:
                throw new IllegalArgumentException("Unsupported file type");
        }
    }
}
