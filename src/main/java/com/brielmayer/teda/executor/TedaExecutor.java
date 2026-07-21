package com.brielmayer.teda.executor;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.handler.IExecutionHandler;
import com.brielmayer.teda.handler.ILoadHandler;
import com.brielmayer.teda.handler.ITestHandler;
import com.brielmayer.teda.handler.ITruncateHandler;
import com.brielmayer.teda.model.Action;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.model.Table;
import com.brielmayer.teda.parser.Parser;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;

@RequiredArgsConstructor
@Builder
public class TedaExecutor {

    private final BaseDatabase loadDatabase;
    private final BaseDatabase testDatabase;

    private final ITruncateHandler truncateHandler;
    private final ILoadHandler loadHandler;
    private final IExecutionHandler executionHandler;
    private final ITestHandler testHandler;

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

                final Action action;
                try {
                    action = Action.valueOf(header);
                } catch (final IllegalArgumentException e) {
                    throw TedaException.builder()
                            .appendMessage("Unknown cockpit action \"%s\"", header)
                            .appendMessage("Valid actions are: %s", Arrays.toString(Action.values()))
                            .cause(e)
                            .build();
                }

                switch (action) {
                    case TRUNCATE:
                        truncateHandler.truncate(testDatabase, value);
                        break;
                    case LOAD:
                        final Sheet loadSheet = document.getSheets().get(value);
                        loadSheet.getTables()
                                .values()
                                .forEach(table -> loadHandler.load(loadDatabase, table));
                        break;
                    case EXECUTE:
                        executionHandler.execute(value);
                        break;
                    case TEST:
                        final Sheet testSheet = document.getSheets().get(value);
                        testSheet.getTables()
                                .values()
                                .forEach(table -> testHandler.test(testDatabase, table));;
                        break;
                }
            }
        }
    }
}
