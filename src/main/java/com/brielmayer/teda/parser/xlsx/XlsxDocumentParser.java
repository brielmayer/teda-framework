package com.brielmayer.teda.parser.xlsx;

import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.parser.Parser;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XlsxDocumentParser implements Parser {

    @Override
    public Document parse(InputStream inputStream) {
        Map<String, Sheet> sheets = new HashMap<>();
        try (ReadableWorkbook workbook = new ReadableWorkbook(inputStream)) {
            for (org.dhatim.fastexcel.reader.Sheet xlsxSheet : workbook.getSheets().collect(Collectors.toList())) {
                final String name = xlsxSheet.getName();
                try (Stream<Row> rowStream = xlsxSheet.openStream()) {
                    final List<Row> rows = rowStream.collect(Collectors.toList());
                    sheets.put(name, XlsxSheetParser.parseSheet(name, rows));
                }
            }
        } catch (final IOException e) {
            throw TedaException.builder()
                    .appendMessage("Unable to read InputStream")
                    .cause(e)
                    .build();
        }
        return new Document(sheets);
    }
}
