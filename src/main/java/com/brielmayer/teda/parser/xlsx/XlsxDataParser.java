package com.brielmayer.teda.parser.xlsx;

import com.brielmayer.teda.model.Header;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XlsxDataParser {

    public static List<Map<String, Object>> parseData(XSSFSheet xssfSheet, CellAddress cellAddress) {
        final List<Map<String, Object>> data = new ArrayList<>();

        final List<Header> headers = XlsxHeaderParser.parseHeader(xssfSheet, cellAddress);
        for (int r = 2; ; r++) {
            final XSSFRow xssfRow = xssfSheet.getRow(cellAddress.getRow() + r);
            if (xssfRow == null) {
                // end of table reached
                break;
            }

            final Map<String, Object> row = new LinkedHashMap<>();
            for (byte c = 0; c < headers.size(); c++) {
                final XSSFCell cell = xssfRow.getCell(cellAddress.getColumn() + c + 1);
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

    private static Object getCellValue(final Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // if cell is a date
                    return cell.getDateCellValue();
                } else if (isMathematicalInteger(cell.getNumericCellValue())) {
                    // if cell is a int / long
                    return (long) cell.getNumericCellValue();
                } else {
                    // else return double
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            // BLANK or ERROR
            default:
                return "";
        }
    }

    private static boolean isMathematicalInteger(final double x) {
        return !Double.isNaN(x) && !Double.isInfinite(x) && x == Math.rint(x);
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
