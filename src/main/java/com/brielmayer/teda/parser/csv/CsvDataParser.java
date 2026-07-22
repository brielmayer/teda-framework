package com.brielmayer.teda.parser.csv;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.parser.Coord;

public class CsvDataParser {

    public static List<Map<String, Object>> parseData(final List<List<String>> grid, final Coord coord) {
        final List<Map<String, Object>> data = new ArrayList<>();
        final List<Header> headers = CsvHeaderParser.parseHeader(grid, coord);

        for (int r = coord.row + 2; r < grid.size(); r++) {
            final Map<String, Object> row = new LinkedHashMap<>();
            for (int c = 0; c < headers.size(); c++) {
                // CSV layout: data cells align with the header row above,
                // both starting at the marker column.
                final String value = CsvTableParser.getCell(grid, r, coord.col + c);
                // CSV cells are always strings; TypeParser detects the intended type
                // downstream (via SpreadsheetStringDetector).
                row.put(headers.get(c).getName(), value == null ? "" : value);
            }

            if (isEmptyRow(row)) {
                // end of table reached
                break;
            }
            data.add(row);
        }
        return data;
    }

    private static boolean isEmptyRow(final Map<String, Object> row) {
        for (final Map.Entry<String, Object> entry : row.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
