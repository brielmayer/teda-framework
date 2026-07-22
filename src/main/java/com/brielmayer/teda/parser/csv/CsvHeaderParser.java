package com.brielmayer.teda.parser.csv;

import java.util.ArrayList;
import java.util.List;

import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.parser.Coord;

public class CsvHeaderParser {

    public static List<Header> parseHeader(final List<List<String>> grid, final Coord coord) {
        final List<Header> headers = new ArrayList<>();
        final int headerRow = coord.row + 1;

        // CSV layout: header cells sit directly under the marker column and
        // extend to the right. No offset, no leading empty cells.
        for (int c = 0; ; c++) {
            final String value = CsvTableParser.getCell(grid, headerRow, coord.col + c);
            if (value == null || value.isEmpty()) {
                break;
            }
            headers.add(Header.fromName(value));
        }
        return headers;
    }
}
