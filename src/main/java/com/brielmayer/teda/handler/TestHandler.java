package com.brielmayer.teda.handler;

import com.brielmayer.teda.comparator.ObjectComparator;
import com.brielmayer.teda.comparator.SortComparator;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.model.Table;
import com.brielmayer.teda.parser.TypeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TestHandler {

    private static final Logger log  = LoggerFactory.getLogger(TestHandler.class);

    public static void test(final BaseDatabase database, final Table expectedTable) {

        final Table actualTable = Table.builder()
                .name(expectedTable.getName())
                .headers(expectedTable.getHeaders())
                .data(database.select(expectedTable.getName(), expectedTable.getHeaders()))
                .build();

        log.info("Test table: {}", expectedTable.getName());

        // sort data
        final List<Header> primaryKeys = expectedTable.getHeaders().stream().filter(Header::isPrimaryKey).collect(Collectors.toList());
        expectedTable.getData().sort(new SortComparator(primaryKeys));
        actualTable.getData().sort(new SortComparator(primaryKeys));

        // compare data
        compare(expectedTable, actualTable);
    }

    private static void compare(final Table excelBean, final Table actualBean) {
        // check number of rows
        if (excelBean.getData().size() != actualBean.getData().size()) {
            throw TedaException.builder()
                    .appendMessage("Failed to compare data for bean %s", excelBean.getName())
                    .appendMessage("Number of rows are not equal")
                    .appendMessage("Expected number of rows: %d", excelBean.getData().size())
                    .appendMessage("Actual number of rows: %d", actualBean.getData().size())
                    .appendMessage()
                    .appendMessage("Actual:")
                    .appendMessage("%s", listToString(actualBean.getData()))
                    .appendMessage("Expected:")
                    .appendMessage("%s", listToString(excelBean.getData()))
                    .build();
        }

        // compare line by line
        for (int rowCount = 0; rowCount < excelBean.getData().size(); rowCount++) {

            final Map<String, Object> expectedRow = excelBean.getData().get(rowCount);
            final Map<String, Object> actualRow = actualBean.getData().get(rowCount);

            for (final Map.Entry<String, Object> entry : expectedRow.entrySet()) {
                final String key = entry.getKey();

                final Object actualValue = TypeParser.parse(actualRow.get(key));
                final Object expectedValue = TypeParser.parse(expectedRow.get(key));

                if (!ObjectComparator.compare(actualValue, expectedValue)) {
                    throw TedaException.builder()
                            .appendMessage("Error comparing Table %s in row %d", excelBean.getName(), rowCount + 1)
                            .appendMessage("Column %s: Expected (%s) \"%s\" != Actual (%s) \"%s\"", key, expectedValue.getClass().getSimpleName(), expectedValue, actualValue.getClass().getSimpleName(), actualValue)
                            .appendMessage("Expected Row:  %s", expectedRow.toString())
                            .appendMessage("Actual Row:    %s", actualRow.toString())
                            .build();
                }
            }
        }
    }

    // only used in case of an exception
    private static String listToString(final List<Map<String, Object>> data) {
        final StringBuilder retVal = new StringBuilder();
        for (final Map<String, Object> row : data) {
            retVal.append(String.format("%s\n", row.toString()));
        }
        return retVal.toString();
    }

}
