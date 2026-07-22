package com.brielmayer.teda.database;

import com.brielmayer.teda.model.Header;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class BaseDatabase {

    // maps a ResultSet to a list of case-insensitive row maps (DbUtils' default
    // BasicRowProcessor lower-cases column keys). DbUtils manages the
    // connection/statement/resultset lifecycle around this handler.
    private static final ResultSetHandler<List<Map<String, Object>>> MAP_LIST_HANDLER = new MapListHandler();

    private final DataSource dataSource;
    private final QueryRunner queryRunner;

    protected BaseDatabase(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.queryRunner = new QueryRunner(dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public int executeQuery(final String query) {
        try {
            return queryRunner.update(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e); // TODO better exception handling
        }
    }

    public List<Map<String, Object>> queryForList(final String query) {
        try {
            return queryRunner.query(query, MAP_LIST_HANDLER);
        } catch (final SQLException e) {
            throw new RuntimeException(e); // TODO better exception handling
        }
    }

    protected void executeRow(final String query, final Map<String, Object> row) throws SQLException {
        queryRunner.update(query, row.values().toArray());
    }

    public abstract void truncateTable(String tableName);

    public abstract void dropTable(String tableName);

    public abstract void insertRow(String tableName, Map<String, Object> row) throws SQLException;

    public abstract List<Map<String, Object>> select(String tableName, List<Header> headers);
}
