package com.brielmayer.teda.parser.xlsx;

import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.parser.Parser;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XlsxDocumentParser implements Parser {

    @Override
    public Document parse(InputStream inputStream) {
        final XSSFWorkbook xssfWorkbook;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (final IOException e) {
            throw TedaException.builder()
                    .appendMessage("Unable to read InputStream")
                    .cause(e)
                    .build();
        }

        Map<String, Sheet> sheets = new HashMap<>();
        for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
            final XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(i);
            final String sheetName = xssfSheet.getSheetName();
            sheets.put(sheetName, XlsxSheetParser.parseSheet(xssfSheet));
        }
        return new Document(sheets);
    }
}
