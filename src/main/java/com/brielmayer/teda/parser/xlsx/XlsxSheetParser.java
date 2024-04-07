package com.brielmayer.teda.parser.xlsx;

import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.model.Table;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Map;

public class XlsxSheetParser {

    public static Sheet parseSheet(XSSFSheet xssfSheet) {
        Map<String, Table> tables = XlsxTableParser.parseTable(xssfSheet);
        return Sheet.builder()
                .name(xssfSheet.getSheetName())
                .tables(tables)
                .build();
    }
}
