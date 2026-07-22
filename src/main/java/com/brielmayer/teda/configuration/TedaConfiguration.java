package com.brielmayer.teda.configuration;

import javax.sql.DataSource;

import com.brielmayer.teda.handler.IExecutionHandler;
import com.brielmayer.teda.handler.ILoadHandler;
import com.brielmayer.teda.handler.ITestHandler;
import com.brielmayer.teda.handler.ITruncateHandler;

public class TedaConfiguration {

    private final DataSource loadDatabase;
    private final DataSource testDatabase;

    private final ITruncateHandler truncateHandler;
    private final ILoadHandler loadHandler;
    private final IExecutionHandler executionHandler;
    private final ITestHandler testHandler;

    TedaConfiguration(final TedaConfigurationBuilder builder) {
        this.loadDatabase = builder.getLoadDatabase();
        this.testDatabase = builder.getTestDatabase();
        this.truncateHandler = builder.getTruncateHandler();
        this.loadHandler = builder.getLoadHandler();
        this.executionHandler = builder.getExecutionHandler();
        this.testHandler = builder.getTestHandler();
    }

    public static IDatabase builder() {
        return new TedaConfigurationBuilder();
    }

    public DataSource getLoadDatabase() {
        return loadDatabase;
    }

    public DataSource getTestDatabase() {
        return testDatabase;
    }

    public ITruncateHandler getTruncateHandler() {
        return truncateHandler;
    }

    public ILoadHandler getLoadHandler() {
        return loadHandler;
    }

    public IExecutionHandler getExecutionHandler() {
        return executionHandler;
    }

    public ITestHandler getTestHandler() {
        return testHandler;
    }
}
