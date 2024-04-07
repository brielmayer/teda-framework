package com.brielmayer.teda.parser.xlsx;

import com.brielmayer.teda.model.Header;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;

public class XlsxHeaderParser {

    public static List<Header> parseHeader(XSSFSheet xssfSheet, CellAddress cellAddress) {
        final List<Header> headers = new ArrayList<>();

        final XSSFRow columnRow = xssfSheet.getRow(cellAddress.getRow() + 1);
        for (byte c = 1; ; c++) {
            final XSSFCell cell = columnRow.getCell(cellAddress.getColumn() + c);
            if (cell != null) {
                final String headerName = cell.getStringCellValue();
                final Header header = Header.fromName(headerName);
                headers.add(header);
                continue;
            }

            // if empty column is reached, break
            break;
        }

        return headers;
    }
}
