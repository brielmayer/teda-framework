package com.brielmayer.teda.parser.xlsx;

import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.model.Table;
import com.brielmayer.teda.parser.Parser;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class XlsxTableParser {

    public static Map<String, Table> parseTable(XSSFSheet xssfSheet) {
        Stream<CellAddress> tableStream = findCells(Parser.TABLE, xssfSheet).stream();
        Stream<CellAddress> tedaStream = findCells(Parser.TEDA, xssfSheet).stream();
        return Stream.concat(tableStream, tedaStream)
                .map(cellAddress -> parseTable(xssfSheet, cellAddress))
                .collect(HashMap::new, (map, table) -> map.put(table.getName(), table), HashMap::putAll);
    }

    private static Table parseTable(XSSFSheet xssfSheet, CellAddress cellAddress) {
        final String tableName = parseTableName(xssfSheet, cellAddress);
        final List<Header> headers = XlsxHeaderParser.parseHeader(xssfSheet, cellAddress);
        final List<Map<String, Object>> data = XlsxDataParser.parseData(xssfSheet, cellAddress);

        return Table.builder()
                .name(tableName)
                .headers(headers)
                .data(data)
                .build();
    }

    private static String parseTableName(XSSFSheet xssfSheet, CellAddress cellAddress) {
        return xssfSheet.getRow(cellAddress.getRow())
                .getCell(cellAddress.getColumn() + 1)
                .getStringCellValue();
    }

    private static List<CellAddress> findCells(final String needle, final XSSFSheet haystack) {
        final List<CellAddress> cellAddresses = new ArrayList<>();

        // only search first 100 rows
        for (int r = 0; r < 100; r++) {
            final XSSFRow row = haystack.getRow(r);
            if (row != null) {
                // only search first 100 columns
                for (int c = 0; c < 100; c++) {
                    final XSSFCell cell = row.getCell(c);

                    // skip empty cells
                    if (cell == null) {
                        continue;
                    }

                    // skip non-string cells
                    if (cell.getCellType() != CellType.STRING) {
                        continue;
                    }

                    // check if cell contains needle
                    if (cell.getStringCellValue().equals(needle)) {
                        cellAddresses.add(cell.getAddress());
                    }
                }
            }
        }

        return cellAddresses;
    }
}
