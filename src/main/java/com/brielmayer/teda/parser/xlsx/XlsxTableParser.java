package com.brielmayer.teda.parser.xlsx;

import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.model.Table;
import com.brielmayer.teda.parser.Coord;
import com.brielmayer.teda.parser.Parser;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.CellType;
import org.dhatim.fastexcel.reader.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class XlsxTableParser {

    public static Map<String, Table> parseTable(List<Row> rows) {
        Stream<Coord> tableStream = findCells(Parser.TABLE, rows).stream();
        Stream<Coord> tedaStream = findCells(Parser.TEDA, rows).stream();
        return Stream.concat(tableStream, tedaStream)
                .map(coord -> parseTable(rows, coord))
                .collect(HashMap::new, (map, table) -> map.put(table.getName(), table), HashMap::putAll);
    }

    private static Table parseTable(List<Row> rows, Coord coord) {
        final String tableName = parseTableName(rows, coord);
        final List<Header> headers = XlsxHeaderParser.parseHeader(rows, coord);
        final List<Map<String, Object>> data = XlsxDataParser.parseData(rows, coord);

        return Table.builder()
                .name(tableName)
                .headers(headers)
                .data(data)
                .build();
    }

    private static String parseTableName(List<Row> rows, Coord coord) {
        final Cell cell = getCell(rows, coord.row, coord.col + 1);
        return cell == null ? "" : cell.asString();
    }

    private static List<Coord> findCells(final String needle, final List<Row> rows) {
        final List<Coord> coords = new ArrayList<>();
        // only search first 100 rows/columns
        final int maxRows = Math.min(100, rows.size());

        for (int r = 0; r < maxRows; r++) {
            final Row row = rows.get(r);
            if (row == null) {
                continue;
            }
            final int maxCols = Math.min(100, row.getCellCount());
            for (int c = 0; c < maxCols; c++) {
                final Cell cell = row.getCell(c);
                if (cell == null || cell.getType() != CellType.STRING) {
                    continue;
                }
                if (needle.equals(cell.asString())) {
                    coords.add(new Coord(r, c));
                }
            }
        }
        return coords;
    }

    static Cell getCell(List<Row> rows, int rowIdx, int colIdx) {
        if (rowIdx < 0 || rowIdx >= rows.size()) {
            return null;
        }
        final Row row = rows.get(rowIdx);
        if (row == null || colIdx < 0 || colIdx >= row.getCellCount()) {
            return null;
        }
        return row.getCell(colIdx);
    }
}
