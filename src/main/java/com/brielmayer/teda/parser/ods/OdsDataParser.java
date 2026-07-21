package com.brielmayer.teda.parser.ods;

import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.parser.Coord;
import com.github.miachm.sods.Sheet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OdsDataParser {

    public static List<Map<String, Object>> parseData(Sheet odsSheet, Coord coord) {
        final List<Map<String, Object>> data = new ArrayList<>();
        final List<Header> headers = OdsHeaderParser.parseHeader(odsSheet, coord);
        final int maxRows = odsSheet.getMaxRows();

        for (int r = coord.row + 2; r < maxRows; r++) {
            final Map<String, Object> row = new LinkedHashMap<>();
            for (int c = 0; c < headers.size(); c++) {
                final Object value = odsSheet.getRange(r, coord.col + c + 1).getValue();
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
