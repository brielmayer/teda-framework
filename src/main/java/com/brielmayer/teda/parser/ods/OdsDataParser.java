package com.brielmayer.teda.parser.ods;

import com.brielmayer.teda.model.Header;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.util.Tuple2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OdsDataParser {

    public static List<Map<String, Object>> parseData(Sheet odsSheet, Tuple2<Integer, Integer> cellAddress) {
        final List<Map<String, Object>> data = new ArrayList<>();

        final List<Header> headers = OdsHeaderParser.parseHeader(odsSheet, cellAddress);
        for (int r = 2; ; r++) {
            final Map<String, Object> row = new LinkedHashMap<>();
            for (byte c = 0; c < headers.size(); c++) {
                final Cell<SpreadSheet> cell = odsSheet.getCellAt(r, cellAddress.get1() + c + 1);
                if (cell == null) {
                    row.put(headers.get(c).getName(), "");
                } else {
                    row.put(headers.get(c).getName(), getCellValue(cell));
                }
            }

            if (isEmptyRow(row)) {
                // end of table reached
                break;
            } else {
                data.add(row);
            }
        }

        return data;
    }

    private static Object getCellValue(final Cell<SpreadSheet> cell) {
        if (cell.getValueType() == null) {
            return "";
        }
        switch (cell.getValueType()) {
            case STRING:
                return cell.getTextValue();
            case DATE:
            case TIME:
            case CURRENCY:
            case PERCENTAGE:
            case BOOLEAN:
            case FLOAT:
                return cell.getValue();
            default:
                return "";
        }
    }

    private static boolean isEmptyRow(final Map<String, Object> row) {
        for (final Map.Entry<String, Object> entry : row.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().toString().equals("")) {
                return false;
            }
        }
        return true;
    }
}
