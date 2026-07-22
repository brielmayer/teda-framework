package com.brielmayer.teda.executor;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.handler.IExecutionHandler;
import com.brielmayer.teda.handler.ILoadHandler;
import com.brielmayer.teda.handler.ITestHandler;
import com.brielmayer.teda.handler.ITruncateHandler;

public final class TedaExecutorBuilder {

    private BaseDatabase loadDatabase;
    private BaseDatabase testDatabase;
    private ITruncateHandler truncateHandler;
    private ILoadHandler loadHandler;
    private IExecutionHandler executionHandler;
    private ITestHandler testHandler;

    TedaExecutorBuilder() {}

    public TedaExecutorBuilder loadDatabase(final BaseDatabase loadDatabase) {
        this.loadDatabase = loadDatabase;
        return this;
    }

    public TedaExecutorBuilder testDatabase(final BaseDatabase testDatabase) {
        this.testDatabase = testDatabase;
        return this;
    }

    public TedaExecutorBuilder truncateHandler(final ITruncateHandler truncateHandler) {
        this.truncateHandler = truncateHandler;
        return this;
    }

    public TedaExecutorBuilder loadHandler(final ILoadHandler loadHandler) {
        this.loadHandler = loadHandler;
        return this;
    }

    public TedaExecutorBuilder executionHandler(final IExecutionHandler executionHandler) {
        this.executionHandler = executionHandler;
        return this;
    }

    public TedaExecutorBuilder testHandler(final ITestHandler testHandler) {
        this.testHandler = testHandler;
        return this;
    }

    BaseDatabase getLoadDatabase() {
        return loadDatabase;
    }

    BaseDatabase getTestDatabase() {
        return testDatabase;
    }

    ITruncateHandler getTruncateHandler() {
        return truncateHandler;
    }

    ILoadHandler getLoadHandler() {
        return loadHandler;
    }

    IExecutionHandler getExecutionHandler() {
        return executionHandler;
    }

    ITestHandler getTestHandler() {
        return testHandler;
    }

    public TedaExecutor build() {
        return new TedaExecutor(this);
    }
}
