package com.brielmayer.teda.parser.ods;

import com.brielmayer.teda.model.Header;
import com.github.miachm.sods.Sheet;

import java.util.ArrayList;
import java.util.List;

public class OdsHeaderParser {

    public static List<Header> parseHeader(Sheet odsSheet, Coord coord) {
        final List<Header> headers = new ArrayList<>();
        final int maxCols = odsSheet.getMaxColumns();
        final int headerRow = coord.row + 1;

        for (int c = 1; coord.col + c < maxCols; c++) {
            final Object value = odsSheet.getRange(headerRow, coord.col + c).getValue();
            if (value == null) {
                // if empty column is reached, break
                break;
            }
            headers.add(Header.fromName(value.toString()));
        }
        return headers;
    }
}
