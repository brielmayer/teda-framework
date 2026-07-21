package com.brielmayer.teda.config;

import com.brielmayer.teda.handler.IExecutionHandler;
import com.brielmayer.teda.handler.ILoadHandler;
import com.brielmayer.teda.handler.ITestHandler;
import com.brielmayer.teda.handler.ITruncateHandler;
import com.brielmayer.teda.handler.impl.LoadHandler;
import com.brielmayer.teda.handler.impl.LogExecutionHandler;
import com.brielmayer.teda.handler.impl.TestHandler;
import com.brielmayer.teda.handler.impl.TruncateHandler;
import lombok.Getter;

import javax.sql.DataSource;

@Getter
public class TedaConfiguration {

    private final DataSource loadDatabase;
    private final DataSource testDatabase;

    private final ITruncateHandler truncateHandler;
    private final ILoadHandler loadHandler;
    private final IExecutionHandler executionHandler;
    private final ITestHandler testHandler;

    private TedaConfiguration(Builder builder) {
        loadDatabase = builder.loadDatabase;
        testDatabase = builder.testDatabase;
        truncateHandler = builder.truncateHandler;
        loadHandler = builder.loadHandler;
        executionHandler = builder.executionHandler;
        testHandler = builder.testHandler;
    }

    public static IDatabase builder() {
        return new Builder();
    }

    public interface IBuild {
        IBuild withTruncateHandler(ITruncateHandler val);

        IBuild withLoadHandler(ILoadHandler val);

        IBuild withExecutionHandler(IExecutionHandler val);

        IBuild withTestHandler(ITestHandler val);

        TedaConfiguration build();
    }

    public interface IDatabase {
        IBuild withDatabase(DataSource val);
        ITestDatabase withLoadDatabase(DataSource val);
    }

    public interface ITestDatabase {
        IBuild withTestDatabase(DataSource val);
    }

    public static final class Builder implements IDatabase, ITestDatabase, IBuild {
        private ITruncateHandler truncateHandler = new TruncateHandler();
        private ILoadHandler loadHandler = new LoadHandler();
        private IExecutionHandler executionHandler = new LogExecutionHandler();
        private ITestHandler testHandler = new TestHandler();
        private DataSource testDatabase;
        private DataSource loadDatabase;

        private Builder() {
        }

        @Override
        public IBuild withDatabase(DataSource val) {
            testDatabase = val;
            loadDatabase = val;
            return this;
        }

        @Override
        public IBuild withTestDatabase(DataSource val) {
            testDatabase = val;
            return this;
        }

        @Override
        public ITestDatabase withLoadDatabase(DataSource val) {
            loadDatabase = val;
            return this;
        }

        @Override
        public IBuild withTruncateHandler(ITruncateHandler val) {
            truncateHandler = val;
            return this;
        }

        @Override
        public IBuild withLoadHandler(ILoadHandler val) {
            loadHandler = val;
            return this;
        }

        @Override
        public IBuild withExecutionHandler(IExecutionHandler val) {
            executionHandler = val;
            return this;
        }

        @Override
        public IBuild withTestHandler(ITestHandler val) {
            testHandler = val;
            return this;
        }

        public TedaConfiguration build() {
            return new TedaConfiguration(this);
        }
    }
}
