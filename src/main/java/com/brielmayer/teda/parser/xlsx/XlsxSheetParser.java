package com.brielmayer.teda.parser.xlsx;

import java.util.List;
import java.util.Map;

import org.dhatim.fastexcel.reader.Row;

import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.model.Table;

public class XlsxSheetParser {

    public static Sheet parseSheet(String name, List<Row> rows) {
        Map<String, Table> tables = XlsxTableParser.parseTable(rows);
        return Sheet.builder().name(name).tables(tables).build();
    }
}
