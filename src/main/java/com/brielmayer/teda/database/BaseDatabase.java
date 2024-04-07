package com.brielmayer.teda.database;

import com.brielmayer.teda.model.Header;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public abstract class BaseDatabase {

    private final DataSource dataSource;

    public int executeQuery(final String query) {
        try (final Statement statement = dataSource.getConnection().createStatement()) {
            return statement.executeUpdate(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e); // TODO better exception handling
        }
    }

    public int executeQuery(final String query, final String... params) {
        return executeQuery(String.format(query, params));
    }

    public List<Map<String, Object>> queryForList(final String query) {
        final List<Map<String, Object>> result = new ArrayList<>();
        try (final Statement statement = dataSource.getConnection().createStatement()) {
            final ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(ResultSetMapper.mapResultSet(resultSet));
            }
            return result;
        } catch (final SQLException e) {
            throw new RuntimeException(e); // TODO better exception handling
        }
    }

    protected void executeRow(final String query, final Map<String, Object> row) throws SQLException {
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                int parameterIndex = 1;
                for (final Map.Entry<String, Object> entry : row.entrySet()) {
                    preparedStatement.setObject(parameterIndex, entry.getValue());
                    parameterIndex++;
                }
                preparedStatement.executeUpdate();
            }
        }
    }

    public abstract void truncateTable(String tableName);

    public abstract void dropTable(String tableName);

    public abstract void insertRow(String tableName, Map<String, Object> row) throws SQLException;

    public abstract List<Map<String, Object>> select(String tableName, List<Header> headers);
}
