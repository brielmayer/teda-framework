package com.brielmayer.teda.parser.ods;

import java.util.Map;

import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.model.Table;

public class OdsSheetParser {

    public static Sheet parseSheet(com.github.miachm.sods.Sheet odsSheet) {
        Map<String, Table> tables = OdsTableParser.parseTable(odsSheet);
        return Sheet.builder().name(odsSheet.getName()).tables(tables).build();
    }
}
