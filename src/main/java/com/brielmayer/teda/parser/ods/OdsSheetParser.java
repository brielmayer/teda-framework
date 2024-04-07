package com.brielmayer.teda.parser.ods;

import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.model.Table;

import java.util.Map;

public class OdsSheetParser {

    public static Sheet parseSheet(org.jopendocument.dom.spreadsheet.Sheet odsSheet) {
        Map<String, Table> tables = OdsTableParser.parseTable(odsSheet);
        return Sheet.builder()
                .name(odsSheet.getName())
                .tables(tables)
                .build();
    }
}
