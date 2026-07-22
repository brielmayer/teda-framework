package com.brielmayer.teda.parser.xlsx;

import java.util.ArrayList;
import java.util.List;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.Row;

import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.parser.Coord;

public class XlsxHeaderParser {

    public static List<Header> parseHeader(List<Row> rows, Coord coord) {
        final List<Header> headers = new ArrayList<>();
        final int headerRow = coord.row + 1;

        for (int c = 1; ; c++) {
            final Cell cell = XlsxTableParser.getCell(rows, headerRow, coord.col + c);
            if (cell == null) {
                // if empty column is reached, break
                break;
            }
            headers.add(Header.fromName(cell.asString()));
        }
        return headers;
    }
}
