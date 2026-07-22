package com.brielmayer.teda.executor;

import java.util.List;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.handler.IExecutionHandler;
import com.brielmayer.teda.handler.ILoadHandler;
import com.brielmayer.teda.handler.ITestHandler;
import com.brielmayer.teda.handler.ITruncateHandler;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.Sheet;

public class TedaExecutor {

    private final BaseDatabase loadDatabase;
    private final BaseDatabase testDatabase;
    private final ITruncateHandler truncateHandler;
    private final ILoadHandler loadHandler;
    private final IExecutionHandler executionHandler;
    private final ITestHandler testHandler;
    private final CockpitReader cockpitReader = new CockpitReader();

    TedaExecutor(final TedaExecutorBuilder builder) {
        this.loadDatabase = builder.getLoadDatabase();
        this.testDatabase = builder.getTestDatabase();
        this.truncateHandler = builder.getTruncateHandler();
        this.loadHandler = builder.getLoadHandler();
        this.executionHandler = builder.getExecutionHandler();
        this.testHandler = builder.getTestHandler();
    }

    public static TedaExecutorBuilder builder() {
        return new TedaExecutorBuilder();
    }

    public void execute(final Document document) {
        final List<Command> commands = cockpitReader.read(document);
        for (final Command command : commands) {
            dispatch(command, document);
        }
    }

    private void dispatch(final Command command, final Document document) {
        final String value = command.getValue();
        switch (command.getAction()) {
            case TRUNCATE:
                truncateHandler.truncate(testDatabase, value);
                break;
            case LOAD:
                final Sheet loadSheet = document.getSheets().get(value);
                loadSheet.getTables().values().forEach(table -> loadHandler.load(loadDatabase, table));
                break;
            case EXECUTE:
                executionHandler.execute(value);
                break;
            case TEST:
                final Sheet testSheet = document.getSheets().get(value);
                testSheet.getTables().values().forEach(table -> testHandler.test(testDatabase, table));
                break;
        }
    }
}
