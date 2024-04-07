package com.brielmayer.teda.parser.ods;

import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.model.Table;
import com.brielmayer.teda.parser.Parser;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.util.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OdsTableParser {

    public static Map<String, Table> parseTable(Sheet odsSheet) {
        Stream<Tuple2<Integer, Integer>> tableStream = findCells(Parser.TABLE, odsSheet).stream();
        Stream<Tuple2<Integer, Integer>> tedaStream = findCells(Parser.TEDA, odsSheet).stream();
        return Stream.concat(tableStream, tedaStream)
                .map(cellAddress -> parseTable(odsSheet, cellAddress))
                .collect(HashMap::new, (map, table) -> map.put(table.getName(), table), HashMap::putAll);
    }

    private static Table parseTable(Sheet odsSheet, Tuple2<Integer, Integer> cellAddress) {
        final String tableName = parseTableName(odsSheet, cellAddress);
        final List<Header> headers = OdsHeaderParser.parseHeader(odsSheet, cellAddress);
        final List<Map<String, Object>> data = OdsDataParser.parseData(odsSheet, cellAddress);

        return Table.builder()
                .name(tableName)
                .headers(headers)
                .data(data)
                .build();
    }

    private static String parseTableName(Sheet odsSheet, Tuple2<Integer, Integer> cellAddress) {
        return odsSheet.getCellAt(cellAddress.get0(), cellAddress.get1() + 1)
                .getTextValue();
    }

    private static List<Tuple2<Integer, Integer>> findCells(final String needle, final Sheet haystack) {

        final List<Tuple2<Integer, Integer>> cellAddresses = new ArrayList<>();

        // only search first 100 rows
        for (int r = 0; r < 100; r++) {
            // only search first 100 columns
            for (int c = 0; c < 100; c++) {
                try {
                    final Cell<SpreadSheet> cell = haystack.getCellAt(r, c);

                    // skip empty cells
                    if (cell == null) {
                        continue;
                    }

                    // skip non-string cells
                    if (cell.getValueType() != ODValueType.STRING) {
                        continue;
                    }

                    // check if cell contains needle
                    if (cell.getTextValue().equals(needle)) {
                        cellAddresses.add(new Tuple2<>(r, c));
                    }

                } catch (IndexOutOfBoundsException ignore) {
                    // end is reached
                    break;
                }
            }
        }

        return cellAddresses;
    }
}
