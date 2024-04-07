package com.brielmayer.teda;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.handler.ExecutionHandler;
import com.brielmayer.teda.handler.LoadHandler;
import com.brielmayer.teda.handler.TestHandler;
import com.brielmayer.teda.handler.TruncateHandler;
import com.brielmayer.teda.model.Action;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.model.Table;
import com.brielmayer.teda.parser.Parser;

import java.util.Map;

public class TedaExecutor {

    private final BaseDatabase loadDatabase;
    private final BaseDatabase testDatabase;

    private final ExecutionHandler executionHandler;

    public TedaExecutor(BaseDatabase loadDatabase, BaseDatabase testDatabase, ExecutionHandler executionHandler) {
        this.loadDatabase = loadDatabase;
        this.testDatabase = testDatabase;
        this.executionHandler = executionHandler;
    }

    public void execute(final Document document) {
        // cockpit is always the first table in the first sheet
        Table teda = document.getSheets()
                .get(Parser.COCKPIT)
                .getTables()
                .values()
                .iterator()
                .next();

        for (final Map<String, Object> row : teda.getData()) {
            for (final Map.Entry<String, Object> entry : row.entrySet()) {

                // cockpit must only contain strings
                final String header = entry.getKey();
                final String value = (String) entry.getValue();

                // if cell is empty, no action will be performed
                if (value == null || value.isEmpty()) {
                    continue;
                }

                switch (Action.valueOf(header)) {
                    case TRUNCATE:
                        TruncateHandler.truncate(testDatabase, value);
                        break;
                    case LOAD:
                        final Sheet loadSheet = document.getSheets().get(value);
                        loadSheet.getTables()
                                .values()
                                .forEach(table -> LoadHandler.load(loadDatabase, table));
                        break;
                    case EXECUTE:
                        executionHandler.execute(value);
                        break;
                    case TEST:
                        final Sheet testSheet = document.getSheets().get(value);
                        testSheet.getTables()
                                .values()
                                .forEach(table -> TestHandler.test(testDatabase, table));;
                        break;
                }
            }
        }
    }
}
