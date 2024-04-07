package com.brielmayer.teda.parser.ods;

import com.brielmayer.teda.model.Header;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.util.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class OdsHeaderParser {

    public static List<Header> parseHeader(Sheet odsSheet, Tuple2<Integer, Integer> cellAddress) {
        final List<Header> headers = new ArrayList<>();

        for (byte c = 1; ; c++) {
            try {
                final Cell<SpreadSheet> cell = odsSheet.getCellAt(cellAddress.get0(), cellAddress.get1() + c);
                if (cell != null) {
                    final String headerName = cell.getTextValue();
                    final Header header = Header.fromName(headerName);
                    headers.add(header);
                    continue;
                }

                // if empty column is reached, break
                break;
            } catch (IndexOutOfBoundsException ignore) {
                // end is reached
                break;
            }
        }

        return headers;
    }
}
