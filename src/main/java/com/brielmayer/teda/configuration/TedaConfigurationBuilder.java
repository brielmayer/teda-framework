package com.brielmayer.teda.configuration;

import javax.sql.DataSource;

import com.brielmayer.teda.handler.IExecutionHandler;
import com.brielmayer.teda.handler.ILoadHandler;
import com.brielmayer.teda.handler.ITestHandler;
import com.brielmayer.teda.handler.ITruncateHandler;
import com.brielmayer.teda.handler.impl.LoadHandler;
import com.brielmayer.teda.handler.impl.LogExecutionHandler;
import com.brielmayer.teda.handler.impl.TestHandler;
import com.brielmayer.teda.handler.impl.TruncateHandler;

public final class TedaConfigurationBuilder implements IDatabase, ITestDatabase, IBuild {

    private DataSource loadDatabase;
    private DataSource testDatabase;
    private ITruncateHandler truncateHandler = new TruncateHandler();
    private ILoadHandler loadHandler = new LoadHandler();
    private IExecutionHandler executionHandler = new LogExecutionHandler();
    private ITestHandler testHandler = new TestHandler();

    TedaConfigurationBuilder() {}

    @Override
    public IBuild withDatabase(final DataSource val) {
        this.loadDatabase = val;
        this.testDatabase = val;
        return this;
    }

    @Override
    public ITestDatabase withLoadDatabase(final DataSource val) {
        this.loadDatabase = val;
        return this;
    }

    @Override
    public IBuild withTestDatabase(final DataSource val) {
        this.testDatabase = val;
        return this;
    }

    @Override
    public IBuild withTruncateHandler(final ITruncateHandler val) {
        this.truncateHandler = val;
        return this;
    }

    @Override
    public IBuild withLoadHandler(final ILoadHandler val) {
        this.loadHandler = val;
        return this;
    }

    @Override
    public IBuild withExecutionHandler(final IExecutionHandler val) {
        this.executionHandler = val;
        return this;
    }

    @Override
    public IBuild withTestHandler(final ITestHandler val) {
        this.testHandler = val;
        return this;
    }

    DataSource getLoadDatabase() {
        return loadDatabase;
    }

    DataSource getTestDatabase() {
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

    @Override
    public TedaConfiguration build() {
        // The staged interfaces (IDatabase, ITestDatabase) guarantee at compile
        // time that at least one of withDatabase / withLoadDatabase+withTestDatabase
        // was called. Runtime check remains as a defence against the impl being
        // used directly, bypassing the interfaces.
        if (loadDatabase == null || testDatabase == null) {
            throw new IllegalStateException("TedaConfigurationBuilder: both load and test database must be set. "
                    + "Use withDatabase(ds) or withLoadDatabase(ds).withTestDatabase(ds).");
        }
        return new TedaConfiguration(this);
    }
}
